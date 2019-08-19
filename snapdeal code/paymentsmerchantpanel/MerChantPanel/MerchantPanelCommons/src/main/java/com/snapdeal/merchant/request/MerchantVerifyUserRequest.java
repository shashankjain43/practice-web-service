package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class MerchantVerifyUserRequest extends AbstractMerchantRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3516947225244233970L;
	
	private String loginName;
	
}
