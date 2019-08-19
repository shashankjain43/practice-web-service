package com.snapdeal.payments.view.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClientPlatform {
	WEB("WEB"), WAP("WAP"), APP("APP");

	private String clientPlatform;

	ClientPlatform(String clientPlatform) {
		this.clientPlatform = clientPlatform;
	}

	public String getValue() {
		return clientPlatform;
	}
	
	@JsonValue
	public String getClientPlatform() {
		return this.clientPlatform;
	}
	@JsonCreator
	public static ClientPlatform forValue(String value) {
		if (null != value) {
			for (ClientPlatform clientPlatform : values()) {
				if (clientPlatform.getClientPlatform().equals(value)) {
					return clientPlatform;
				}
			}
		}
		return null;
	}
}
