package com.snapdeal.opspanel.promotion.enums;

public enum UploadType {
	IMS_ID("IMS_ID"),
	EMAIL_ID("EMAIL_ID"),
	MOBILE_ID("MOBILE_ID");

	private String type;

	private UploadType(String type) {
		this.type=type;
	}

}
