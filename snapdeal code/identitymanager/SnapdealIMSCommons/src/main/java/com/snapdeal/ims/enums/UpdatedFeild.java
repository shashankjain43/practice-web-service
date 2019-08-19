package com.snapdeal.ims.enums;
/*
 * Radhika
 * */
public enum UpdatedFeild {
	ENABLED_DISABLED("ENABLED_DISABLED"),
	IS_DELETED("IS_DELETED"),
	MOBILE_NO("MOBILE_NO"),
	PASSWORD("PASSWORD"),
	GENERAL_INFO("GENERAL_INFO");

	private String fieldName;

	private UpdatedFeild(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getfieldName() {
		return fieldName;
	}

	public static UpdatedFeild forName(String value) {
		if (value != null) {
			for (UpdatedFeild eachSrc : values()) {
				if (eachSrc.name().equalsIgnoreCase(value.trim())) {
					return eachSrc;
				}
			}
		}
		return null;
	}

}
