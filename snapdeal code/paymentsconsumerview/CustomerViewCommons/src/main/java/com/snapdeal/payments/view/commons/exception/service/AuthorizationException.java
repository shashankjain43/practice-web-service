package com.snapdeal.payments.view.commons.exception.service;

import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewDefaultExceptionCodes;

public class AuthorizationException extends PaymentsViewGenericException {

	private static final long serialVersionUID = 2098153784319255395L;

	public AuthorizationException(String errCode,String errMsg) {
		super(errCode,errMsg);
	}

	public AuthorizationException(Throwable cause) {
		super(PaymentsViewDefaultExceptionCodes.AUTHORIZATION.errCode(),
				PaymentsViewDefaultExceptionCodes.AUTHORIZATION.errMsg(), cause);
	}

	public AuthorizationException() {
		super(PaymentsViewDefaultExceptionCodes.AUTHORIZATION.errCode(), 
				PaymentsViewDefaultExceptionCodes.AUTHORIZATION.errMsg());
	}

	public AuthorizationException(String errCode, String message, 
				Throwable cause) {
		super(errCode,message, cause);
	}
}
