package com.snapdeal.ims.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ConfigureUserBasedOn {

	EMAIL("EMAIL"),
	MOBILE("MOBILE"),
	TOKEN("TOKEN"),
	USER_ID("USER_ID") ;
	
	private String value;

	private ConfigureUserBasedOn(String value) {
		this.value = value;
	}

	@org.codehaus.jackson.annotate.JsonValue
	public final String getValue() {
		return this.value;
	}
	@JsonCreator
	public static ConfigureUserBasedOn forValue(String value) {
		if (null != value) {
			for (ConfigureUserBasedOn eachGender : values()) {
				if (eachGender.getValue().equals(value)) {
					return eachGender;
				}
			}
		}
		return null;
	}	
}
