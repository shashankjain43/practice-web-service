package com.snapdeal.payments.view.commons.exception.codes;

public enum PaymentsViewAuthorizationExceptionCodes {

	IP_ADDRESS_NOT_PROVIDED("ER-1101","Client IP Address not provided to Server"), 
	UNAUTHORIZED_CLIENT("ER-1102", "Client not authorized"), 
	UNAUTHORIZED_CLIENT_IP_ADDRESS("ER-1103", "Client IP Address not authorized"), 
	CLIENT_SECRET_NOT_PROVIDED("ER-1104", "Client Secret not provided"), 
	INVALID_REQUEST("ER-1105", "Invalid Request"),
	CLIENT_IP_ADDRESS_NOT_PROVIDED("ER-1106", "Client IP Address not provided"),
	CLIENT_ID_NOT_PROVIDED("ER-1107", "Client ID not provided"),
    CLIENT_ID_NOT_SUPPORTED("ER-1108", "Client ID not supported"),
    REQUEST_TIMEOUT("ER-1109", "Request timeout"),
    CLIENT_NAME_IS_BLANK("ER-1110","Client name is blank"),
    CLIENT_NAME_MAX_LENGTH("ER-1111","Client name excceeds max length"),
    CLIENT_STATUS_IS_NULL("ER-1112","Client status is null");
	
	private String errCode;
	private String errMsg;

	private PaymentsViewAuthorizationExceptionCodes(String errCode, String errMsg) {
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
