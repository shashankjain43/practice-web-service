package com.snapdeal.ims.enums;

public enum Reason {

	FRAUD("FRAUD"),
	USER_INITIATED("USER_INITIATED"),
	TAKE_OVER("TAKEOVER"),
	OTHERS("OTHERS");
	
	private final String reason;
	
	Reason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return this.reason;
	}
}
