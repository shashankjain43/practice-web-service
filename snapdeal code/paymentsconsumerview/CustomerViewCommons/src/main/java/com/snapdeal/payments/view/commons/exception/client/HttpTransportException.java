package com.snapdeal.payments.view.commons.exception.client;

import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;



public class HttpTransportException extends PaymentsViewServiceException {

	private static final long serialVersionUID = 1L;

	public HttpTransportException(String message, String code) {
		super(code, message);
	}
}
