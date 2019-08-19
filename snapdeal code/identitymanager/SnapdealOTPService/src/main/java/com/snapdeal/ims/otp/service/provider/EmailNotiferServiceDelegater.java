package com.snapdeal.ims.otp.service.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.snapdeal.fcNotifier.client.impl.NotifierServiceClient;
import com.snapdeal.fcNotifier.exception.HttpTransportException;
import com.snapdeal.fcNotifier.request.EmailNotifierRequest;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.notifier.email.reponse.EmailResponse;
import com.snapdeal.notifier.email.request.EmailMessage;
import com.snapdeal.notifier.exception.ValidationException;
import com.snapdeal.notifier.service.Notifier;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Component
@Slf4j
public class EmailNotiferServiceDelegater {

	@Autowired
	Notifier notifier;

	@Autowired
	OTPUtility otpUtility;

	@Autowired
	private NotifierServiceClient fcNotifier;

	@Logged
	@Timed
	@Marked
	public void sendEmail(UserOTPEntity otpInfo,Merchant merchant,String name) {
		try {
			boolean isFCNotifierEnabled = otpUtility.isFcNotifierEnabled();
			log.info("SendOTPbyEnum is : " + otpInfo.getSendOTPBy());
			EmailMessage emailMessage = getEmailMessage(otpInfo,merchant,name);
			if (SendOTPByEnum.forValue(otpInfo.getSendOTPBy()) == SendOTPByEnum.FREECHARGE
					|| SendOTPByEnum.forValue(otpInfo.getSendOTPBy()) == SendOTPByEnum.V0FREECHARGE) {
				if (isFCNotifierEnabled) {
					EmailNotifierRequest fcNotifierEmailRequest = new EmailNotifierRequest();
					fcNotifierEmailRequest.setBlocking(true);
					fcNotifierEmailRequest
							.setEmailMessage(getFcNotifierEmailMessage(emailMessage));
					com.snapdeal.fcNotifier.reponse.EmailResponse response = null;
					int retryCount = Integer.parseInt(Configuration.getGlobalProperty(ConfigurationConstants.EMAIL_SMS_RETRIES));
					while (retryCount > 0) {
						try{
							response = fcNotifier.sendEmail(fcNotifierEmailRequest);
							retryCount=0;
						} catch (HttpTransportException e){
							retryCount--;
							log.info("Sending email failed for email_id " + fcNotifierEmailRequest.getEmailMessage().getTo() +" : retry left - "+retryCount);
						}
					}
					
					log.info("EmailResponse is " + response);
				} else {
					log.info("fc notifier is disabled ,sending email through notifier.");
					EmailResponse response = notifier.sendEmail(emailMessage,
							true);
					log.info("EmailResponse is " + response);
				}
			} else {
				EmailResponse response = notifier.sendEmail(emailMessage, true);
				log.info("EmailResponse is " + response);
			}
		} catch (Exception e) {
			log.error("Sending email failed exception for email :"
					+ otpInfo.getEmail() + " " + e);
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}
	}

	private EmailMessage getEmailMessage(UserOTPEntity otpInfo,Merchant merchant,String name) {
		EmailMessage emailMessage = new EmailMessage();
		if(name == null){
			name="";
		}
		Map<String, String> tags = new HashMap<String, String>();
		tags.put(OtpConstants.VERIFICATION_CODE, otpInfo.getOtp());
		tags.put(OtpConstants.MERCHANT,StringUtils.capitalize(merchant.toString().toLowerCase()));
		tags.put(OtpConstants.MOBILE_NUMBER,otpInfo.getMobileNumber());
		tags.put(OtpConstants.CUSTOMER_TAG,name);

		List<String> toList = new ArrayList<String>();
		toList.add(otpInfo.getEmail().toString());
		emailMessage.addRecepients(toList);

		String fromEmailId = getFromEmailId(SendOTPByEnum.forValue(otpInfo.getSendOTPBy()));
		emailMessage.setFrom(fromEmailId);

		String templateKey = getTemplateKey(otpInfo);
		emailMessage.setTemplateKey(templateKey);

		String emailSubject = getEmailSubject(otpInfo);
		emailMessage.setSubject(emailSubject);

		String replyEmailId = getReplyEmailId(SendOTPByEnum.forValue(otpInfo.getSendOTPBy()));
		emailMessage.setReplyTo(replyEmailId);
		
		emailMessage.setTags(tags);
		String taskId = "OTP_MAIL-" + otpInfo.getUserId() + "-"
				+ UUID.randomUUID().getLeastSignificantBits();
		emailMessage.setTaskId(taskId);
		setEmailIDAsTestEmailIDIfApplicable(emailMessage);
		log.debug("Email message: " + emailMessage);
		return emailMessage;
	}

	private String getEmailSubject(UserOTPEntity otpInfo) {
		
		OTPPurpose otpPurpose = OTPPurpose.valueOf(otpInfo.getOtpType());
		SendOTPByEnum sendOtpBy = SendOTPByEnum.forValue(otpInfo.getSendOTPBy()) ;
		switch(sendOtpBy){
		case FREECHARGE :
		case V0FREECHARGE :
			switch (otpPurpose) {
				case FORGOT_PASSWORD:
					return otpUtility.getEmailSubjectFORGOT_PASSWORD();
				case LINK_ACCOUNT:
					return otpUtility.getEmailSubjectLINK_ACCOUNT();
				case MONEY_OUT:
					return otpUtility.getEmailSubjectMONEY_OUT();
				default:
					return otpUtility.getEmailSubject();
			}
		case SNAPDEAL :
			switch (otpPurpose) {
				case FORGOT_PASSWORD:
					return otpUtility.getEmailSubjectFORGOT_PASSWORDSnapdeal();
				case LINK_ACCOUNT:
					return otpUtility.getEmailSubjectLINK_ACCOUNTSnapdeal();
				case MONEY_OUT:
					return otpUtility.getEmailSubjectMONEY_OUTSnapdeal();
				default:
					return otpUtility.getEmailSubject();
		}
			default :
				return otpUtility.getEmailSubject();  
			
		
		}
	}

	private String getFromEmailId(SendOTPByEnum sendOTPBy) {
		switch (sendOTPBy) {
		case FREECHARGE:
		case V0FREECHARGE:
			return otpUtility.getFromEmailIdFreeCharge();
		case SNAPDEAL:
		default:
			return otpUtility.getFromEmailId();
		}
	}
	
	private String getReplyEmailId(SendOTPByEnum sendOTPBy) {
		switch (sendOTPBy) {
		case FREECHARGE:
		case V0FREECHARGE:
			return otpUtility.getFreechargeReplyEmailId();
		case SNAPDEAL:
		default:
			return otpUtility.getSnapdealReplyEmailId();
		}
	}

	private com.snapdeal.fcNotifier.request.EmailMessage getFcNotifierEmailMessage(
			EmailMessage emailMessage) throws ValidationException {
		if (emailMessage == null) {
			return null;
		}
		com.snapdeal.fcNotifier.request.EmailMessage fcNotifierEmailMessage = new com.snapdeal.fcNotifier.request.EmailMessage();
		fcNotifierEmailMessage.setCc(emailMessage.getCc());
		fcNotifierEmailMessage.setFrom(emailMessage.getFrom());
		fcNotifierEmailMessage.setReplyTo(emailMessage.getReplyTo());
		fcNotifierEmailMessage.setRequestId(emailMessage.getRequestId());
		fcNotifierEmailMessage.setSubject(emailMessage.getSubject());
		fcNotifierEmailMessage.setTags(emailMessage.getTags());
		fcNotifierEmailMessage.setTaskId(emailMessage.getTaskId());
		fcNotifierEmailMessage.setTemplateKey(emailMessage.getTemplateKey());
		fcNotifierEmailMessage.setTo(emailMessage.getTo());

		return fcNotifierEmailMessage;
	}

	private void setEmailIDAsTestEmailIDIfApplicable(EmailMessage emailMessage) {
		if (otpUtility.isSendEmailOnTestEmailId()) {
			log.debug("Sending email to test email instead of : "
					+ emailMessage.getTo());
			emailMessage.setTo(Arrays.asList(otpUtility.getTestEmailId()));
		}
	}

	private String getTemplateKey(UserOTPEntity otpInfo) {
		SendOTPByEnum sendOTPBy= SendOTPByEnum.forValue(otpInfo.getSendOTPBy()) ;
		switch (sendOTPBy) {
		case FREECHARGE:
			return otpInfo.getSendOTPBy().toString()
					+ "."+otpInfo.getOtpType().toLowerCase().replace("_",".")+"."+
						OtpConstants.TEMPLATE_KEY;
		case SNAPDEAL:
			return otpInfo.getSendOTPBy().toString() +
			"."+otpInfo.getOtpType().toLowerCase().replace("_",".")+"."+
					OtpConstants.TEMPLATE_KEY;
		case V0FREECHARGE:
			return otpInfo.getSendOTPBy().toString() +
					"."+otpInfo.getOtpType().toLowerCase().replace("_",".")+"."+
							OtpConstants.TEMPLATE_KEY;
		}
		return "global."+OtpConstants.TEMPLATE_KEY;
	}
}
