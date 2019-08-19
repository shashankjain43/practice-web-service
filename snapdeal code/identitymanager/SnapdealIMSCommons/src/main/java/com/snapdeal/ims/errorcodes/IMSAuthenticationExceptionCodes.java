package com.snapdeal.ims.errorcodes;

public enum IMSAuthenticationExceptionCodes {

	USER_DOES_NOT_EXIST("ER-2101","User does not exist"), 
	INVALID_GLOBAL_TOKEN("ER-2102", "Global Token is invalid"), 
	GLOBAL_TOKEN_EXPIRED("ER-2103", "Global Token has expired"), 
	INVALID_TOKEN("ER-2104", "Token is invalid"), 
	TOKEN_EXPIRED("ER-2105", "Token has expired"),	
	INVALID_REFRESH_TOKEN("ER-2106", "Refresh Token is invalid"), 
	REFRESH_TOKEN_EXPIRED("ER-2107", "Refresh Token has expired"), 
	INVALID_ACCESS_TOKEN("ER-2108", "Access Token is invalid"), 
	ACCESS_TOKEN_EXPIRED("ER-2109", "Access Token has expired"),
	INVALID_AUTH_CODE("ER-2110", "Auth Code is invalid");
	
	private String errCode;
	private String errMsg;

	private IMSAuthenticationExceptionCodes(String errCode, String errMsg) {
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
