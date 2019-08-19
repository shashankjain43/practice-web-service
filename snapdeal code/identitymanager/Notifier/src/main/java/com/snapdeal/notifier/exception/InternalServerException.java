package com.snapdeal.notifier.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternalServerException extends Exception {

	private static final long serialVersionUID = 1L;
	private String errCode;
	private String errMsg;

	public InternalServerException(String message) {
		super(message);
	}

	public InternalServerException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public InternalServerException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
}
