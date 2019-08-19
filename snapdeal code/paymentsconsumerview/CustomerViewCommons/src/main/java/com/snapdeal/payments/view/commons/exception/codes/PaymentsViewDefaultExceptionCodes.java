package com.snapdeal.payments.view.commons.exception.codes;

public enum PaymentsViewDefaultExceptionCodes {

	
	AUTHORIZATION("ER-1100", "User is not authorized for this action"),
	AUTHENTICATION("ER-2100", "Authentication Exception"), 
	INTERNAL_SERVER("ER-3100", "System Error! Please try after some time"),
	SERVICE_EXCEPTION("ER-5100","Service Exception"),
	VALIDATION("ER-6100","Validation Exception"),
	INTERNAL_CLIENT("ER-7100","Internal Client Exception") ;

	private String errCode;
	private String errMsg;

	private PaymentsViewDefaultExceptionCodes(String errCode, String errMsg) {
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
