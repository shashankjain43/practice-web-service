package com.snapdeal.ims.common.constant;

public enum InvalidResponseEnum {

	BAD_REQUEST(400), AUTHENTICATION_FAILURE(401), INTERNAL_SERVER_ERROR(500);

	private int value;

	private InvalidResponseEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
