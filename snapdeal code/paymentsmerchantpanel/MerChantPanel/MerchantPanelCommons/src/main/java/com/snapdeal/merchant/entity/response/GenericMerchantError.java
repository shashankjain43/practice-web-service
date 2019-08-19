package com.snapdeal.merchant.entity.response;

import lombok.Getter;

@Getter
public class GenericMerchantError{

	private final String errorCode;
	private final String errorMessage;
	
	public GenericMerchantError(String errCode, String errMsg){
		this.errorCode = errCode;
		this.errorMessage = errMsg;
	}
	
	public GenericMerchantError(String errMsg){
		this.errorCode = null;
		this.errorMessage = errMsg;
	}
	
}
