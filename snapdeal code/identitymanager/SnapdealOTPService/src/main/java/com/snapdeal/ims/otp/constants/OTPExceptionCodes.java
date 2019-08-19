package com.snapdeal.ims.otp.constants;

public enum OTPExceptionCodes {
	//defined by abhishek
	INVALIT_PURPOSE("ER-2004", "invalid purpose"), 
	PARSING_ERROR("ER-3014","error occured while parsing XML doc"),
	ERROR_ON_LOADING_FILE("ER-3015","error loading mesasge template"),
	SMS_NOTIFICATION_ERROR("ER-3016","SMS notification erro"),
	WRONG_SMS_INFO("ER-3212","Wrong smsChannelInfo {}"),
	SHOULD_NOT_COME("ER-3423","here you  should not come"),
	INVALID_PARAMETERS("Er-3434","one of the necessay paremters in empty"),
	ERROR_ON_SEND("ER-3013","error occured while sending the mail"),
	ERROR_ON_CONNECTING_SNAPDEAL_EMAIL("ER-3012","error occured while connecting to snapodeal email client"),
	INVALID_OTP_ENTERED("ER-1904","Invalid One Time Password (OTP) entered.Please try again."), 
	INVALID_PURPOSE("ER-2004", "Invalid purpose"),
	INVALID_OTP_ID("ER-2102","Invalid OTP Id"),
	OTP_LIMIT_BREACHED("ER-2103","Oops ! You have reached OTP limit, please contact customer support."),
	OTP_ALREADY_USED("ER-2104", "Otp code already used"),
	INVALID_MOBILE_NUMBER("ER-1005", "Invalid mobile number, example - 9876543210"),
	INVALID_PARAMETER_REQUEST("ER-3032","one of the necessary parameters in request is empty"),
	INVALID_BOTH_PARAMETER("ER-3421","both mobile number and emailid can not be blank"),
	TOKEN_SHOUD_BE_THERE("ER-3454","token can not be blank"),
	OTPID_SHOULD_BE_THERE("ER-3432","otp id can not be blank"),
	OTP_SHOULD_BE_THERE("ER-2345","otp can not be blank");;
	
	
	private String errCode;
	private String errMsg;

	private OTPExceptionCodes(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String errCode() {
		return this.errCode;
	}

	public String errMsg() {
		return this.errMsg;
	}

}
