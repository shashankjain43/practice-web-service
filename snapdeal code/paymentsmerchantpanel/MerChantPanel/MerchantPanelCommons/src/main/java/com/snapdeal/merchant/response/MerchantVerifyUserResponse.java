package com.snapdeal.merchant.response;

import lombok.Data;

@Data
public class MerchantVerifyUserResponse extends AbstractResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8544496922128506421L;
	
	private boolean userPresent;
	
}
