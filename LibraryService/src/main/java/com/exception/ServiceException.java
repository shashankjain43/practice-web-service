package com.exception;

import lombok.Getter;
import lombok.Setter;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	@Setter
	@Getter
	private String errCode;

	@Setter
	@Getter
	private String errMsg;

	public ServiceException(String errMsg) {
		super(errMsg);
		this.errMsg = errMsg;
	}

	public ServiceException(String errCode, String errMsg) {
		super(errMsg);
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public ServiceException(String errCode, String message, Throwable cause) {
		super(message, cause);
		this.errCode = errCode;
		this.errMsg = message;
	}

}
