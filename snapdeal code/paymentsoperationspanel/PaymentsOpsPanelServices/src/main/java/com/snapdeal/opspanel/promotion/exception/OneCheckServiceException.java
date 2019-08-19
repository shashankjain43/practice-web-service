package com.snapdeal.opspanel.promotion.exception;

import com.snapdeal.opspanel.AbstractComponentabstract.Exception.GenericException;

public class OneCheckServiceException extends GenericException {
	private static final long serialVersionUID = 2L;
	private final String errCode;
	private final String errMessage;
	
	public OneCheckServiceException(String errCode, String errMessage) {
		super("");
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	public OneCheckServiceException(String errCode, String errMessage, String source) {
		super(source);
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}
	
}
