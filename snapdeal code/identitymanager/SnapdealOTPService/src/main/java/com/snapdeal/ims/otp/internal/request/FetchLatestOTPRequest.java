package com.snapdeal.ims.otp.internal.request;

import lombok.Data;

public @Data class FetchLatestOTPRequest {

	private String clientId;
	private String token;
	private String otpId;
}