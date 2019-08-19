package com.snapdeal.opspanel.promotion.exception;

import com.snapdeal.opspanel.AbstractComponentabstract.Exception.GenericException;

public class WalletServiceException extends GenericException {
	private static final long serialVersionUID = 2L;
	private final String errCode;
	private final String errMessage;
	
	public WalletServiceException(String string, String errMessage) {
		super("");
		this.errCode = string;
		this.errMessage = errMessage;
	}

	public WalletServiceException(String string, String errMessage, String source) {
		super(source);
		this.errCode = string;
		this.errMessage = errMessage;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getErrMessage() {
		return errMessage;
	}
	
}
