package com.snapdeal.ims.otp.dao.mapper;

public enum OTPInfoDaoMapper {

	SAVE_OTP("otp.saveOTP"), 
	GET_LATEST_OTP("otp.getLatestOTP"),
	SAVE_OTP_WITH_ENCRYPTION("saveOTPWithEncryption"),
	INCREMENT_INVALID_ATTEMPTS("otp.incrementInvalidAttempts"),
	INCREMENT_RESEND_ATTEMPTS("otp.incrementResendAttempts"),
	UPDATE_CURRENT_OTP_STATUS("otp.updateCurrentOtpStatus"),
	UPDATE_EXPIRY_TIME("otp.updateExpiryTime"),
	FETCH_OTP_FOR_VALIDATION("otp.fetchOtpForValidation"), 
	GET_OTP_ID("otp.getOtpId"),
	CHECK_IF_OTP_SEND("otp.checkIfOtpPresent"),
	GET_OTP_FROM_ID("otp.isOtpPresent"),
	GET_VERIFIED_OTP("otp.getActiveOtp");
	
	private String description;

	private OTPInfoDaoMapper(String desc) {
		this.description = desc;
	}

	@Override
	public String toString() {
		return description;
	}

}
