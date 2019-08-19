package com.snapdeal.opspanel.userpanel.exception;

import com.snapdeal.opspanel.AbstractComponentabstract.Exception.GenericException;

public class InfoPanelException extends GenericException {
	private static final long serialVersionUID = 2L;
	private final String errCode;
	private final String errMessage;
	
	public InfoPanelException(String string, String errMessage) {
		super("");
		this.errCode = string;
		this.errMessage = errMessage;
	}

	public InfoPanelException(String errCode, String errMessage, String source) {
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
