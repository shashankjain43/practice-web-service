package com.snapdeal.payments.view.commons.exception.service;

import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewDefaultExceptionCodes;

public class ValidationException extends PaymentsViewGenericException{

	private static final long serialVersionUID = 1L;

	public ValidationException(String errCode,String errMsg) {
		super(errCode,errMsg);
	}

	public ValidationException() {
		this(PaymentsViewDefaultExceptionCodes.VALIDATION.errCode(),
			 PaymentsViewDefaultExceptionCodes.VALIDATION.errMsg());
	}
}
