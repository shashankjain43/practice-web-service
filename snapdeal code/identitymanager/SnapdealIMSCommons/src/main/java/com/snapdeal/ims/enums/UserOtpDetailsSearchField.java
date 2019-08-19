package com.snapdeal.ims.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum UserOtpDetailsSearchField {
	USER_ID("USER_ID"),
	OTP_ID("OTP_ID"),
	MOBILE_NUMBER("MOBILE_NUMBER"),
	EMAIL_ID("EMAIL_ID");
	private final String otpSearchField;

	UserOtpDetailsSearchField(String otpSearchField) {
		this.otpSearchField = otpSearchField;
	}
	@org.codehaus.jackson.annotate.JsonValue
	public String getOTPSearchField() {
		return this.otpSearchField;
	}

	@JsonCreator
	public static UserOtpDetailsSearchField forValue(String value) {
		if (null != value) {
			for (UserOtpDetailsSearchField eachPurpose : values()) {
				if (eachPurpose.getOTPSearchField().equals(value)) {
					return eachPurpose;
				}
			}
		}
		return null;
	}
}
