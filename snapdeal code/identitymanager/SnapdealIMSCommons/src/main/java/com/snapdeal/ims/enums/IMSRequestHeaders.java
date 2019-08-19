package com.snapdeal.ims.enums;
/**
 * This enum defines the various request header supported.
 */
public enum IMSRequestHeaders {

	CONTENT_TYPE("content-type"),
	ACCEPT("accept"),
	CLIENT_ID("clientId"), 
	CLIENT_IP_ADDRESS("clientIpAddress"), 
	USER_AGENT("user-Agent"), 
	USER_MACHINE_IDENTIFIER("userMachineIdentifier"), 
	CLIENT_SDK_VERSION("client-version"),
	TIMESTAMP("timestamp"),
	REQUEST_URI("Request-URI"), 
	HASH("hash"),
	USER_ID("userId"),
	EMAIL_ID("emailID"),
	MOBILE_NUMBER("mobileNumber"),
	HTTPMETHOD("httpmethod"),
	SERVER_IP_ADDRESS("serverIpAddress"),
   START_TIME("startTime"),
   APP_REQUEST_ID("apprequestid");


	private String description;

	private IMSRequestHeaders(String description) {
		this.description = description;
	}

	public String toString() {
		return description;
	}

}
