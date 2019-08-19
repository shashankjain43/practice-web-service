package com.snapdeal.ims.exception;


public class AuthenticationException extends IMSGenericException {

	private static final long serialVersionUID = 1L;

	public AuthenticationException(String errCode,String errMsg) {
		super(errCode,errMsg);
	}
	
}
