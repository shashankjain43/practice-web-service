package com.snapdeal.ims.otp.internal.request;

import com.snapdeal.ims.enums.OTPPurpose;

import lombok.Data;

public @Data class GetFrozenAccount {
	private String userId;
	private OTPPurpose otpType;
}