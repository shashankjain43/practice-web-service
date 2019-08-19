package com.snapdeal.merchant.enums;

public enum MerchantEnvType {

	SANDBOX("SANDBOX"),PRODUCTION("PRODUCTION");
	
	private final String envTypeValue;

	private MerchantEnvType(String envTypeValue) {
		this.envTypeValue = envTypeValue;
	}

	public String getEnvTypeValue() {
		return envTypeValue;
	}
}
