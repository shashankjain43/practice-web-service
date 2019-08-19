package com.snapdeal.ims.common.constant;

public enum ValidResponseEnum {

	OK(200), CREATED(201);

	private int value;

	private ValidResponseEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
