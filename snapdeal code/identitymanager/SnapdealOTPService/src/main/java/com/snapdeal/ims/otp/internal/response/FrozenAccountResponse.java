package com.snapdeal.ims.otp.internal.response;

import lombok.Data;

public @Data class FrozenAccountResponse {
	boolean status;
	String requestType;
	long remainingMinutes ;
}
