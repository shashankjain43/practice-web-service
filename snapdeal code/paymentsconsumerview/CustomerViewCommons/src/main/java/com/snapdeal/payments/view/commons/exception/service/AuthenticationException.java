package com.snapdeal.payments.view.commons.exception.service;

public class AuthenticationException extends PaymentsViewGenericException {

	private static final long serialVersionUID = 1L;

	public AuthenticationException(String errCode, String errMsg) {
		super(errCode, errMsg);
	}

}
