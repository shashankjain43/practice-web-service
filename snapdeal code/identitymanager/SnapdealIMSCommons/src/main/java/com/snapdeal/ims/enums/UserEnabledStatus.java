package com.snapdeal.ims.enums;

public enum UserEnabledStatus {
	ENABLED("ENABLED"),
	DISABLED("DISABLED"),
	CLOSED("CLOSED");


	private String userEnabledStatus;

	private UserEnabledStatus(String userEnabledStatus) {
		this.userEnabledStatus = userEnabledStatus;
	}

	public String getCreateWalletStatus() {
		return userEnabledStatus;
	}
}
