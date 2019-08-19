package com.snapdeal.ims.otp.types;

/**
 * 
 * @author shagun
 *
 */
public enum OTPStatus {

	ACTIVE("ACTIVE"), 
	VERIFIED("VERIFIED"),
	NOT_ACTIVE("NOT_ACTIVE"),
	DELETED("DELETED");
	
	private final String otpStatus;
	
	OTPStatus(String otpStatus) {
		this.otpStatus=otpStatus;
	}
	public String getOTPStatus(){
		return this.otpStatus;
	}


}
