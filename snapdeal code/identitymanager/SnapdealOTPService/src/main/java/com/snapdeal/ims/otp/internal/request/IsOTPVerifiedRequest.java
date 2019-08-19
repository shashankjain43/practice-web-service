package com.snapdeal.ims.otp.internal.request;

import lombok.Data;

public @Data class IsOTPVerifiedRequest {
	private String otpId;
	private String clientId;
}
