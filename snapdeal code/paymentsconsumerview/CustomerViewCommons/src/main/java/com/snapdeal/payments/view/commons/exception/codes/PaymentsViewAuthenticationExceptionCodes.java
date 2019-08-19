package com.snapdeal.payments.view.commons.exception.codes;

public enum PaymentsViewAuthenticationExceptionCodes {

	USER_DOES_NOT_EXIST("ER-2101","User does not exist") ;
	
	private String errCode;
	private String errMsg;

	private PaymentsViewAuthenticationExceptionCodes(String errCode, String errMsg) {
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
