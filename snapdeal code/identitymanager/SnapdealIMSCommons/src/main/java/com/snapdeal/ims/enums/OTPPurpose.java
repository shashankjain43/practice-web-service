package com.snapdeal.ims.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OTPPurpose {

	LOGIN("LOGIN"), 
	FORGOT_PASSWORD("FORGOT_PASSWORD"),
	MOBILE_VERIFICATION("MOBILE_VERIFICATION"), 
	USER_SIGNUP("USER_SIGNUP"),
	UPDATE_MOBILE("UPDATE_MOBILE"),
	UPGRADE_USER("UPGRADE_USER"),
	LINK_ACCOUNT("LINK_ACCOUNT"),
	ONECHECK_SOCIAL_SIGNUP("ONECHECK_SOCIAL_SIGNUP"),
	MONEY_OUT("MONEY_OUT"),
	WALLET_PAY("WALLET_PAY"),
	WALLET_LOAD("WALLET_LOAD"),
	WALLET_ENQUIRY("WALLET_ENQUIRY"),
	LOGIN_WITH_EMAIL_OTP("LOGIN_WITH_EMAIL_OTP"),
	LOGIN_WITH_MOBILE_OTP("LOGIN_WITH_MOBILE_OTP"),
	SIGNUP_WITH_OTP("SIGNUP_WITH_OTP");
	
	private final String otpPurpose;

	OTPPurpose(String otpPurpose) {
		this.otpPurpose = otpPurpose;
	}
	@org.codehaus.jackson.annotate.JsonValue
	public String getOTPPurpose() {
		return this.otpPurpose;
	}

	@JsonCreator
	public static OTPPurpose forValue(String value) {
		if (null != value) {
			for (OTPPurpose eachPurpose : values()) {
				if (eachPurpose.getOTPPurpose().equals(value)) {
					return eachPurpose;
				}
			}
		}
		return null;
	}
}
