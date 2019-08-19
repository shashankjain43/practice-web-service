package com.snapdeal.ims.exception;

public class HttpTransportException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public HttpTransportException(String message, String code) {
		super(message, code);
	}
}
