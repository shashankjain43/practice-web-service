package com.snapdeal.ims.enums;


public enum UserStatus {
	GUEST("guest"),
	REGISTERED("registered"),
	UNVERIFIED("unverified"),
	TEMP("temp");
	
	private String value;
	
	private UserStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
