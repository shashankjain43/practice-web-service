package com.snapdeal.ims.enums;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum Gender {

	MALE("m"), FEMALE("f"), OTHERS("o");

	private String value;

	private Gender(String value) {
		this.value = value;
	}

	@org.codehaus.jackson.annotate.JsonValue
	public final String getValue() {
		return this.value;
	}
	@JsonCreator
	public static Gender forValue(String value) {
		if (null != value) {
			for (Gender eachGender : values()) {
				if (eachGender.getValue().equals(value)) {
					return eachGender;
				}
			}
		}
		return null;
	}	
}