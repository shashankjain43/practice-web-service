package com.snapdeal.ims.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum OTPRequestChannel {

	THROUGH_SMS("THROUGH_SMS"), 
	THROUGH_CALL("THROUGH_CALL");
	
	private final String otpRequestChannel;

	OTPRequestChannel(String otpRequestChannel) {
		this.otpRequestChannel = otpRequestChannel;
	}
	@org.codehaus.jackson.annotate.JsonValue
	public String getOTPRequestChannel() {
		return this.otpRequestChannel;
	}

	@JsonCreator
	public static OTPRequestChannel forValue(String value) {
		if (null != value) {
			for (OTPRequestChannel eachPurpose : values()) {
				if (eachPurpose.getOTPRequestChannel().equals(value)) {
					return eachPurpose;
				}
			}
		}
		return null;
	}

}


