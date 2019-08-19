package com.snapdeal.payments.view.commons.exception;

import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;

public class PaymentsViewServiceException extends PaymentsViewGenericException{

	private static final long serialVersionUID = -1663092887536384506L;

	public PaymentsViewServiceException(String errCode, String errMsg) {
		super(errCode,errMsg);
	}
	public PaymentsViewServiceException(String errCode, String errMsg,ExceptionType type) {
		super(errCode,errMsg,type);
	}
}
