package com.snapdeal.ims.otp.constants;

public interface OtpConstants {

	public static final String REQUEST_TYPE_GENERATE = "Generate";
	public static final String REQUEST_TYPE_VERIFY = "Verify";
	public static final String FROZEN_REASON_INVALID_ATTEMPTS = "Invalid_Attempts";
	public static final String FROZEN_REASON_RESEND_ATTEMPTS = "Resend_Attempts";
	public static final String STATUS_SUCCESS = "Success";
	public static final String STATUS_FAILURE = "Failure";
	public static final String SHOULD_NOT_COME = "here you should not come!";
	public static final String OTP_STATE = "Current OTP state ";
	public static final String OTP_NOT_EXIST = "otp doesn't exist for current otpId";
	public static final String NOT_ACTIVE = "otp not in active state";
	public static final String OTP_EMAIL = "OTP Code";
	public static final String VERIFICATION_CODE = "verificationCode" ;
	public static final String MOBILE_NUMBER = "mobile" ;
	public static final String VALIDATION_ERROR = "error occured while validating the parmetrs";
	public static final String ACCOUNT_FROZEN="account is blocked for this mobile number and email id";
	public static final int STATUS=200;
	public static final String MERCHANT = "merchant" ;
	public static final String TEMPLATE_KEY = "verification.otp.email.template" ;
	public static final String CUSTOMER_TAG = "custName";
	public static final String EXPIRY_TIME = "expiryTime";
}
