package com.snapdeal.ims.errorcodes;

public enum IMSAuthorizationExceptionCodes {

	IP_ADDRESS_NOT_PROVIDED("ER-1101","Client IP Address not provided to Server"), 
	UNAUTHORIZED_CLIENT("ER-1102", "Client not authorized"), 
	UNAUTHORIZED_CLIENT_IP_ADDRESS("ER-1103", "Client IP Address not authorized"), 
	CLIENT_SECRET_NOT_PROVIDED("ER-1104", "Client Secret not provided"), 
	INVALID_REQUEST("ER-1105", "Invalid Request"),
	CLIENT_IP_ADDRESS_NOT_PROVIDED("ER-1106", "Client IP Address not provided"),
	CLIENT_ID_NOT_PROVIDED("ER-1107", "Client ID not provided"),
   CLIENT_ID_NOT_SUPPORTED("ER-1108", "Client ID not supported"),
   REQUEST_TIMEOUT("ER-1109", "Request timeout");
	
	private String errCode;
	private String errMsg;

	private IMSAuthorizationExceptionCodes(String errCode, String errMsg) {
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
