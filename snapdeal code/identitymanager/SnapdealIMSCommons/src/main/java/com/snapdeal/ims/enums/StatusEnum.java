package com.snapdeal.ims.enums;

public enum StatusEnum {
	SUCCESS(1), FAILURE(0);

	private int value;

	private StatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
