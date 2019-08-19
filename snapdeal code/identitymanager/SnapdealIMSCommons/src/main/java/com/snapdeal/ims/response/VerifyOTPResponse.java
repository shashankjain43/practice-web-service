package com.snapdeal.ims.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class VerifyOTPResponse extends AbstractResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean otpVerified ;
}
