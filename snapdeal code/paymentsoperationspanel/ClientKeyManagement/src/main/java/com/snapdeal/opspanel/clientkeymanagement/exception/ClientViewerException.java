package com.snapdeal.opspanel.clientkeymanagement.exception;

import com.snapdeal.opspanel.AbstractComponentabstract.Exception.GenericException;

public class ClientViewerException extends GenericException {
	private static final long serialVersionUID = 2L;
	private final String errCode;
	private final String errMessage;

	public ClientViewerException(String errCode, String errMessage) {
		super("");
		this.errCode = errCode;
		this.errMessage = errMessage;
	}

	public ClientViewerException(String errCode, String errMessage, String source) {
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
