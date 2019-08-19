package com.snapdeal.ims.otp.internal.request;

import java.util.Date;

import lombok.Data;

import com.snapdeal.ims.enums.OTPPurpose;

public @Data class DropUserFromFreezeRequest {
	private String userId;
	private OTPPurpose otpType;
	private String isDeleted="true";
	private Date currentTime;
}
