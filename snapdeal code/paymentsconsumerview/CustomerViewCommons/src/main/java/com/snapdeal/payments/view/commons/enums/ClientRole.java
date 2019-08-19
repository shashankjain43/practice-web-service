package com.snapdeal.payments.view.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClientRole {

	ADMIN("ADMIN"),
	USER("USER");

	private String clientRole;

	ClientRole(String clientRole) {
		this.clientRole = clientRole;
	}

	public String getValue() {
		return clientRole;
	}
	
	@JsonValue
	public String getClientRole() {
		return this.clientRole;
	}
	@JsonCreator
	public static ClientRole forValue(String value) {
		if (null != value) {
			for (ClientRole clientRole : values()) {
				if (clientRole.getClientRole().equals(value)) {
					return clientRole;
				}
			}
		}
		return null;
	}
}
