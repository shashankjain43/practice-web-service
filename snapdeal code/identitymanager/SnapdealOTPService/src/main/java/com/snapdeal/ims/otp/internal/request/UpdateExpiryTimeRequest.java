package com.snapdeal.ims.otp.internal.request;

import java.util.Date;

import lombok.Data;

import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.otp.types.OTPStatus;

public @Data class UpdateExpiryTimeRequest {
	private String otpId;
	private Date expiryTime;
	private String otp;
	private int resendAttempts;
	private String userId;
	private OTPPurpose otpType;
	private OTPStatus otpStatus;
	private String mobileNumber;
	private String emailId;
}