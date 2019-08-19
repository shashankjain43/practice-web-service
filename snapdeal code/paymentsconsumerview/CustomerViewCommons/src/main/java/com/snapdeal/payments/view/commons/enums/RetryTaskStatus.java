package com.snapdeal.payments.view.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RetryTaskStatus {

	SUBMITTED("SUBMITTED"), 
	BLOCKED("BLOCKED"), 
	PENDING("PENDING"),
	SUCCESS("SUCCESS");

	private final String name;

	RetryTaskStatus(String transactionStatus) {
		this.name = transactionStatus;
	}

	@JsonValue
	public String getName() {
		return this.name;
	}

	@JsonCreator
	public static RetryTaskStatus forValue(String value) {
		if (null != value) {
			for (RetryTaskStatus eachStatus : values()) {
				if (eachStatus.getName().equals(value)) {
					return eachStatus;
				}
			}
		}
		return null;
	}
}
