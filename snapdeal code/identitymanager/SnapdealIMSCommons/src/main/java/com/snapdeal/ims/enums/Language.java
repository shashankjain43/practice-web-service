package com.snapdeal.ims.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Language {

	ENGLISH("ENGLISH"), HINDI("HINDI");

	private final String language;

	private Language(String language) {
		this.language = language;
	}

	public String getOTPPurpose() {
		return this.language;
	}

	@org.codehaus.jackson.annotate.JsonValue
	public final String getValue() {
		return this.language;
	}

	@JsonCreator
	public static Language forValue(String language) {
		if (null != language) {
			for (Language eachLanguage : values()) {
				if (eachLanguage.getValue().equals(language)) {
					return eachLanguage;
				}
			}
		}
		return null;
	}

}
