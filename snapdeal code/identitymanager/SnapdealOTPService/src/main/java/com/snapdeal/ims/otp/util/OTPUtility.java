package com.snapdeal.ims.otp.util;

import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.types.OTPState;

@Component
@Getter
@Setter
public class OTPUtility {

	@Value("${otp.expiryInMins}")
	private Integer expiryDurationInMins;

	@Value("${otp.reSendAttemptsLimit}")
	private Integer reSendAttemptsLimit;

	@Value("${sms.template.otp.channelId}")
	private Integer channelId;
	@Value("${sms.template.otp.templateName}")
	private String templateName;

	@Value("${smschannels.valuefirst.channelName}")
	private String channelName;

	@Value("${otp.invalidAttemptsLimit}")
	private Integer invalidAttemptsLimit;

	@Value(value = "${user.blockTimeInMins}")
	private Integer blockDurationInMins;

	@Value("${sms.apiurl.valuefirst}")
	private String apiUrl;

	@Value("${snapdeal.notifier.email.octaneTriggerId}")
	private String triggerId;

	@Value("${snapdeal.notifier.email.octaneClientName}")
	private String emailClientName;

	@Value("${snapdeal.notifier.email.octaneServiceUrl}")
	private String url;

	@Value("${snapdeal.email.client.client.name}")
	private String clientName;

	@Value("${snapdeal.email.client.textContent}")
	private String textContent;

	@Value("${snapdeal.email.client.verificationText}")
	private String verificationPin;
	@Value("${snapdeal.email.client.fromTo.emailId}")
	private String fromEmailId;
	private String fromEmailIdFreeCharge ;
	private String snapdealReplyEmailId;
	private String freechargeReplyEmailId;
	@Value("${verify.success.message}")
	private String successMessage;
	@Value("${verify.failure.message}")
	private String failureMessage;
	
	@Value("${snapdeal.sms.clientName}")
	private String smsClientName ;
	
	@Value("${snapdeal.sms.clientUrl}")
	private String smsClientUrl;
	
	private String emailSubject;
	private String emailSubjectFORGOT_PASSWORD;
	private String emailSubjectLINK_ACCOUNT;
	private String emailSubjectMONEY_OUT;
	private String emailSubjectFORGOT_PASSWORDSnapdeal;
	private String emailSubjectLINK_ACCOUNTSnapdeal;
	private String emailSubjectMONEY_OUTSnapdeal;

	
	private String smsTemplateLoginFreecharge ;
	private String smsTemplateForgotPasswordFreecharge ;
	private String smsTemplateMobileVerificationFreecharge ;
	private String smsTemplateUserSignupFreecharge ;
	private String smsTemplateUpdateMobileFreecharge ;
	private String smsTemplateUpgradeUserFreecharge ;
	private String smsTemplateLinkAccountFreecharge ;
	private String smsTemplateOneCheckFreecharge ;
	private String smsTemplateMoneyOutFreecharge ;
	
	private String smsTemplateWalletPayFreeCharge ;
	private String smsTemplateWalletLoadFreeCharge ;
	private String smsTemplateWalletEnquiryFreeCharge ;
	
	private String smsTemplateWalletPaySnapdeal ;
	private String smsTemplateWalletLoadSnapdeal ;
	private String smsTemplateWalletEnquirySnapdeal ;
	
	
	private String smsTemplateLoginSnapdeal ;
	private String smsTemplateForgotPasswordSnapdeal ;
	private String smsTemplateMobileVerificationSnapdeal ;
	private String smsTemplateUserSignupSnapdeal ;
	private String smsTemplateUpdateMobileSnapdeal ;
	private String smsTemplateUpgradeUserSnapdeal ;
	private String smsTemplateLinkAccountSnapdeal ;
	private String smsTemplateOneCheckSnapdeal ;
	private String smsTemplateMoneyOutSnapdeal ;
	
	private String templateBody;
	
	private boolean otpNumberFix;
	private boolean fcNotifierEnabled;
	private boolean otpEncryptionEnabled;
	
	private boolean sendEmailOnTestEmailId;
	private boolean sendSMSOnTestMobile ;
	private boolean sendCallOnTestMobile ;
	private String testEmailId ;
	private String testMobileNumber;


	public OTPState getOTPState(Optional<UserOTPEntity> otpOption) {
		return OTPStateCalculator.get(otpOption);
	}
}
