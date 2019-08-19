package com.snapdeal.ims.otp.internal.request;

import lombok.Data;

public @Data class UpdateResendAttemptsRequest {
	private String otpId;
	private String moobileNumber;
	private String emailId;
	private String token;
	private int resendAttempts;
	private String clientId;
}