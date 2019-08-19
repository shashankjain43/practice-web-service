package com.snapdeal.ims.activity.exception;

public enum ActivityExceptionCodes {

	ActivityException("ER-5100", "Activity Exception");

	private String errCode;
	private String errMsg;

	private ActivityExceptionCodes(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String errCode() {
		return this.errCode;
	}

	public String errMsg() {
		return this.errMsg;
	}

}
