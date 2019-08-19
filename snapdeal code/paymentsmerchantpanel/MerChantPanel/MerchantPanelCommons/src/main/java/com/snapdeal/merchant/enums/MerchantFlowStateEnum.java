package com.snapdeal.merchant.enums;

public enum MerchantFlowStateEnum {

	BASIC_INFO("Basic Details"), BANK_INFO("Bank Verification"), KYC("KYC Verification");

	private final String merchantStateValue;

	private MerchantFlowStateEnum(String merchantStateValue) {
		this.merchantStateValue = merchantStateValue;
	}

	public String getMerchantStateValue() {
		return merchantStateValue;
	}

	public static String enumOf(String merchantStateValue) {
		if (merchantStateValue != null) {
			for (MerchantFlowStateEnum eachStateValue : values()) {
				if (eachStateValue.getMerchantStateValue().equalsIgnoreCase(merchantStateValue)) {
					return eachStateValue.name();
				}
			}
		}
		return null;
	}
}