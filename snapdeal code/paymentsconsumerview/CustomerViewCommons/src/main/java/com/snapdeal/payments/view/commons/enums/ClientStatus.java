package com.snapdeal.payments.view.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClientStatus {
	ACTIVE("ACTIVE"),INACTIVE("INACTIVE");

	private String clientStatus;

	ClientStatus(String clientStatus) {
		this.clientStatus = clientStatus;
	}

	public String getValue() {
		return clientStatus;
	}
	@JsonValue
	public String getClientStatus() {
		return this.clientStatus;
	}
	@JsonCreator
	public static ClientStatus forValue(String value) {
		if (null != value) {
			for (ClientStatus eachStatus : values()) {
				if (eachStatus.getClientStatus().equals(value)) {
					return eachStatus;
				}
			}
		}
		return null;
	}
}
