package com.snapdeal.merchant.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantException extends Exception {


	private static final long serialVersionUID = 1L;

	private final String errCode;
	private final Integer errCodeInt;
	private final String errMessage;
	
	public MerchantException(String errMessage) {
		super();
		this.errCode = null;
		this.errCodeInt = null;
		this.errMessage = errMessage;
	}

	public MerchantException(String errorCode, String errMessage) {
		
		super();
		this.errCodeInt = null;
		this.errCode = errorCode;
		this.errMessage = errMessage;
	}

	public MerchantException(Integer errorCodeInt, String errMessage) {
	
		super();
		this.errCode = null;
		this.errCodeInt = errorCodeInt;
		this.errMessage = errMessage;

	}

	/*public MerchantException(Integer errorCode, String string) {
		
		super();
		this.errCode = errorCode;
		this.errMessage = errMessage;
	}*/

}
