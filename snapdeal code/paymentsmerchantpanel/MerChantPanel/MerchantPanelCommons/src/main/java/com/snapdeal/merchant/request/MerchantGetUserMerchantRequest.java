package com.snapdeal.merchant.request;

import lombok.Data;

@Data
public class MerchantGetUserMerchantRequest extends AbstractMerchantRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6923564476895454778L;
	
	private String userId;
}
