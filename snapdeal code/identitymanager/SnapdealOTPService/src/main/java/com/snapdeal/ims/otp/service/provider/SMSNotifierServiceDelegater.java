package com.snapdeal.ims.otp.service.provider;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.fcNotifier.client.impl.NotifierServiceClient;
import com.snapdeal.fcNotifier.exception.HttpTransportException;
import com.snapdeal.fcNotifier.reponse.SMSResponse;
import com.snapdeal.fcNotifier.request.SMSNotifierRequest;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.notifier.service.Notifier;
import com.snapdeal.notifier.sms.request.SMSRequest;
import com.snapdeal.sms.client.model.SmsSenderResponse;

@Component
@Slf4j
public class SMSNotifierServiceDelegater {

	@Autowired
	Notifier notifier;

	@Autowired
	OTPUtility otpUtility;

	@Autowired
	private NotifierServiceClient fcNotifier;

	public void sendSms(UserOTPEntity otpInfo,Merchant merchant) {
		try {
			boolean isFCNotifierEnabled = otpUtility.isFcNotifierEnabled();
			log.info("SendOTPbyEnum is : " + otpInfo.getSendOTPBy());
			if (SendOTPByEnum.forValue(otpInfo.getSendOTPBy()) == SendOTPByEnum.FREECHARGE) {
				if (isFCNotifierEnabled) {
					sendSmsByFCNotifer(otpInfo,merchant);
				} else {
					log.info("fc notifier is disabled ,sending sms through notifier.");
					sendSmsByNotifier(otpInfo,merchant);
				}
			} else {
				sendSmsByNotifier(otpInfo,merchant);
			}
		} catch (Exception e) {
			
			log.error("Sending sms failed exception for mobile  : " + otpInfo.getMobileNumber() + " ::  " + e);
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}
	}

	private SmsSenderResponse sendSmsByNotifier(UserOTPEntity otpInfo,Merchant merchant)
			throws Exception {
		SMSRequest smsRequest = new SMSRequest();
		smsRequest.setMobileNumber(otpInfo.getMobileNumber());
		smsRequest.setOtpType(otpInfo.getOtpType());
		smsRequest.setSMSChannelId(otpUtility.getChannelId());
		smsRequest.setSMSChannelName(otpUtility.getChannelName());
		smsRequest.setTemplateV0(otpUtility.getTemplateName());
		Map<String, String> templateFiller = new HashMap<String, String>();
		templateFiller.put(CommonConstants.VERIFICATION_CODE, otpInfo.getOtp());
		templateFiller.put(OtpConstants.MERCHANT,StringUtils.capitalize(merchant.toString().toLowerCase()));
		templateFiller.put(OtpConstants.EXPIRY_TIME,otpUtility.getExpiryDurationInMins().toString());
		smsRequest.setTemplateFiller(templateFiller);
		String templateBody = getTemplateBody(otpInfo) ;
		if(StringUtils.isBlank(templateBody)){
			templateBody = otpUtility.getTemplateBody() ;
		}
		
		smsRequest.setTemplateBody(templateBody);
		setMobileNumberAsTestMobileIfApplicable(smsRequest);

		SmsSenderResponse smsResponse = notifier.sendSMS(smsRequest);
		log.debug("smsResponse :" + smsResponse);
		return smsResponse;
	}

	private void setMobileNumberAsTestMobileIfApplicable(SMSRequest smsRequest) {
		if (otpUtility.isSendSMSOnTestMobile()) {
			log.debug("Sending sms to test mobile number instead of : "
					+ smsRequest.getMobileNumber());
			smsRequest.setMobileNumber(otpUtility.getTestMobileNumber());
		}
	}
	
	private void setMobileNumberAsTestMobileIfApplicable(SMSNotifierRequest smsRequest) {
		if (otpUtility.isSendSMSOnTestMobile()) {
			log.debug("Sending sms to test mobile number instead of : "
					+ smsRequest.getMobileNumber());
			smsRequest.setMobileNumber(otpUtility.getTestMobileNumber());
		}
	}

	private SMSResponse sendSmsByFCNotifer(UserOTPEntity otpInfo,Merchant merchant)
			throws Exception {
		SMSNotifierRequest smsRequest = new SMSNotifierRequest();
		smsRequest.setMobileNumber(otpInfo.getMobileNumber());
		smsRequest.setOtpType(otpInfo.getOtpType());
		smsRequest.setSMSChannelId(otpUtility.getChannelId());
		smsRequest.setSMSChannelName(otpUtility.getChannelName());
		smsRequest.setTemplateV0(otpUtility.getTemplateName());
		Map<String, String> templateFiller = new HashMap<String, String>();
		templateFiller.put(CommonConstants.VERIFICATION_CODE, otpInfo.getOtp());
		templateFiller.put(OtpConstants.MERCHANT,StringUtils.capitalize(merchant.toString().toLowerCase()));
		templateFiller.put(OtpConstants.EXPIRY_TIME,otpUtility.getExpiryDurationInMins().toString());
		smsRequest.setTemplateFiller(templateFiller);
		String templateBody = getTemplateBody(otpInfo) ;
		
		smsRequest.setTemplateBody(templateBody);
		setMobileNumberAsTestMobileIfApplicable(smsRequest);
		SMSResponse smsResponse = null;
		int retryCount = Integer.parseInt(Configuration.getGlobalProperty(ConfigurationConstants.EMAIL_SMS_RETRIES));
		while (retryCount > 0) {
			try{
				smsResponse = fcNotifier.sendSMS(smsRequest);
				retryCount=0;
			} catch (HttpTransportException e){
				retryCount--;
				log.info("Sending sms failed for mobile number " + smsRequest.getMobileNumber() +" : retry left - "+retryCount);
			}
		}
		log.debug("smsResponse :" + smsResponse);
		return smsResponse;
	}
	
	private String getTemplateBody(UserOTPEntity otpInfo){
		OTPPurpose otpPurpose = OTPPurpose.valueOf(otpInfo.getOtpType()) ;
		SendOTPByEnum sendOTPBy = SendOTPByEnum.forValue(otpInfo.getSendOTPBy()) ;
		switch(sendOTPBy){
		case FREECHARGE :
			switch (otpPurpose) {
				case LOGIN :
					return otpUtility.getSmsTemplateLoginFreecharge();
				case FORGOT_PASSWORD:
					return otpUtility.getSmsTemplateForgotPasswordFreecharge() ;
				case MOBILE_VERIFICATION: 
					return otpUtility.getSmsTemplateMobileVerificationFreecharge() ;				
				case USER_SIGNUP:
					return otpUtility.getSmsTemplateUserSignupFreecharge();
				case UPDATE_MOBILE:
					return otpUtility.getSmsTemplateUpdateMobileFreecharge();
				case UPGRADE_USER:
					return otpUtility.getSmsTemplateUpgradeUserFreecharge();
				case LINK_ACCOUNT:
					return otpUtility.getSmsTemplateLinkAccountFreecharge();
				case MONEY_OUT:
					return otpUtility.getSmsTemplateMoneyOutFreecharge();
				case ONECHECK_SOCIAL_SIGNUP:
					return otpUtility.getSmsTemplateOneCheckFreecharge() ;
				case WALLET_PAY :
					return otpUtility.getSmsTemplateWalletPayFreeCharge() ;
				case WALLET_LOAD :
					return otpUtility.getSmsTemplateWalletLoadFreeCharge() ;
				case WALLET_ENQUIRY :
					return otpUtility.getSmsTemplateWalletEnquiryFreeCharge() ;
				default:
					return otpUtility.getEmailSubject();
			}	
		case SNAPDEAL :
			switch (otpPurpose) {
				case LOGIN :
					return otpUtility.getSmsTemplateLoginSnapdeal();
				case FORGOT_PASSWORD:
					return otpUtility.getSmsTemplateForgotPasswordSnapdeal() ;
				case MOBILE_VERIFICATION: 
					return otpUtility.getSmsTemplateMobileVerificationSnapdeal() ;				
				case USER_SIGNUP:
					return otpUtility.getSmsTemplateUserSignupSnapdeal();
				case UPDATE_MOBILE:
					return otpUtility.getSmsTemplateUpdateMobileSnapdeal();
				case UPGRADE_USER:
					return otpUtility.getSmsTemplateUpgradeUserSnapdeal();
				case LINK_ACCOUNT:
					return otpUtility.getSmsTemplateLinkAccountSnapdeal();
				case MONEY_OUT:
					return otpUtility.getSmsTemplateMoneyOutSnapdeal();
				case ONECHECK_SOCIAL_SIGNUP:
					return otpUtility.getSmsTemplateOneCheckSnapdeal() ;
				case WALLET_PAY :
					return otpUtility.getSmsTemplateWalletPaySnapdeal();
				case WALLET_LOAD :
					return otpUtility.getSmsTemplateWalletLoadSnapdeal() ;
				case WALLET_ENQUIRY :
					return otpUtility.getSmsTemplateWalletEnquirySnapdeal() ;
				default:
					return otpUtility.getEmailSubject();
			}	
			default :
				return otpUtility.getEmailSubject() ;
		}
	}
}