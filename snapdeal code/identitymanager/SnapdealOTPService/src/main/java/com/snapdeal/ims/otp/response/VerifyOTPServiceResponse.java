package com.snapdeal.ims.otp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class VerifyOTPServiceResponse {
	private String status;
	private String message;
	private String userId;
	private String mobileNumber;
}
