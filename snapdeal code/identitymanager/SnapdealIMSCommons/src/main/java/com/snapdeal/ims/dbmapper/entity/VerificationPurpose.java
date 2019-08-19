package com.snapdeal.ims.dbmapper.entity;

public enum VerificationPurpose {
	VERIFY_NEW_USER("N"),
	VERIFY_GUEST_USER("G"),
	FORGOT_PASSWORD("P"),
	PARKING_INTO_WALLET("W");
	private String value;

	VerificationPurpose(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static VerificationPurpose forName(String value) {
		VerificationPurpose purpose = null;
		for (VerificationPurpose eachPurpose : values()) {
			if (eachPurpose.value.equals(value)) {
				purpose = eachPurpose;
				break;
			}
		}
		return purpose;
	}
}
