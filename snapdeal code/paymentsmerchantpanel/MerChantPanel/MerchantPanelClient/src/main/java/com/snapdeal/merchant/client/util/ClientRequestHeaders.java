package com.snapdeal.merchant.client.util;
/**
 * This enum defines the various request header supported.
 */
public enum ClientRequestHeaders {

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
	token("token"),
	merchantId("merchantId");


	private String description;

	private ClientRequestHeaders(String description) {
		this.description = description;
	}

	public String toString() {
		return description;
	}

}
