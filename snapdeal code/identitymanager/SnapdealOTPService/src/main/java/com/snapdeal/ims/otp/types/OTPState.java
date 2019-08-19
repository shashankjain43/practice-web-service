package com.snapdeal.ims.otp.types;

public enum OTPState {
	NOT_ACTIVE("NOT_ACTIVE"), 
	IN_THRESHOLD("IN_THRESHOLD"),
	IN_EXPIRY("IN_EXPIRY"),
	DOES_NOT_EXIST("DOES_NOT_EXIST"),
	RESEND_COUNT_BREACHED("RESEND_COUNT_BREACHED"),
	VERIFIED("VERIFIED"),
	DELETED("DELETED");
	
	private final String otpState;
	
	OTPState(String otpState) {
		this.otpState=otpState;
	}
	public String getOTPState(){
		return this.otpState;
	}

}
