package com.snapdeal.vanila.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.snapdeal.opspanel.AbstractComponentabstract.Exception.GenericException;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MerchantException extends GenericException {


	private static final long serialVersionUID = 1L;

	private final String errCode;
	private final String errMessage;
	
	public MerchantException(String errMessage) {
		super("MerchantException");
		this.errCode = null;
		this.errMessage = errMessage;
	}

	public MerchantException(String errorCode, String errMessage) {
		super("MerchantException");
		this.errCode = errorCode;
		this.errMessage = errMessage;
	}
	
	public MerchantException(String errorCode, String errMessage, String source) {
		super(source);
		this.errCode = errorCode;
		this.errMessage = errMessage;
	}

}
