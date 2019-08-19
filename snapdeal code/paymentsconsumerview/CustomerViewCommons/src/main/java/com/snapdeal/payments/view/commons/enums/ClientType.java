package com.snapdeal.payments.view.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClientType {
	USER_FACING("USER_FACING"),NON_USER_FACING("NON_USER_FACING");

	private String clientType;

	ClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getValue() {
		return clientType;
	}
	
	@JsonValue
	public String getClientType() {
		return this.clientType;
	}
	@JsonCreator
	public static ClientType forValue(String value) {
		if (null != value) {
			for (ClientType clientType : values()) {
				if (clientType.getClientType().equals(value)) {
					return clientType;
				}
			}
		}
		return null;
	}
}
