package com.snapdeal.payments.view.commons.enums;

/**
 * This enum defines the various request header supported.
 */
public enum PaymentsViewRequestHeaders {

	CONTENT_TYPE("content-type"),
	ACCEPT("accept"),
	CLIENT_NAME("clientName"), 
	CLIENT_IP_ADDRESS("clientIpAddress"), 
	USER_AGENT("user-Agent"), 
	USER_MACHINE_IDENTIFIER("userMachineIdentifier"), 
	CLIENT_SDK_VERSION("client-version"),
	TIMESTAMP("timestamp"),
	REQUEST_URI("Request-URI"), 
	HASH("hash"),
	USER_ID("userId"),
	HTTPMETHOD("httpmethod"),
	SERVER_IP_ADDRESS("serverIpAddress"),
	APP_REQUEST_ID("apprequestid"),
	TOKEN("token"),
	DOMAINID("domainId"),
	ENV_TYPE("envType");
	
	private String description;

	private PaymentsViewRequestHeaders(String description) {
		this.description = description;
	}

	public String toString() {
		return description;
	}

}
