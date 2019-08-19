package com.snapdeal.ims.otp.internal.request;

import lombok.Data;

import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.otp.types.OTPStatus;

public @Data class UpdateOTPStatusRequest {
	private String otpId;
	private OTPPurpose otpType;
	private OTPStatus otpStatusCurrent;
	private OTPStatus otpStatusExpected;
}