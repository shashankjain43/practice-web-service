package com.snapdeal.ims.otp.internal.request;

import lombok.Data;

import com.snapdeal.ims.otp.types.OTPStatus;

public @Data class UpdateInvalidAttemptsRequest {

	private String otpId;
	private OTPStatus otpStatus;
	private int invalidAttempts;
	private String clientId;
	private String reason;
}