package com.snapdeal.payments.view.customer.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MerchantTxnType {

	CREDIT("CREDIT"),
	DEBIT("DEBIT"),
	REFUND("REFUND"),
	PAYMENT("PAYMENT"),
	LOAD_CASH("LOAD_CASH"),
	LOAD_CASH_REVERSE("LOAD_CASH_REVERSE"),
	LOAD_CASH_REFUND("LOAD_CASH_REFUND");
	
	private final String merchantTxnType;

	MerchantTxnType(String merchantTxnType) {
		this.merchantTxnType = merchantTxnType;
	}
	@JsonValue
	public String getMerchantTxnType() {
		return this.merchantTxnType;
	}
	
	@JsonCreator
	public static MerchantTxnType forValue(String value) {
		if (null != value) {
			for (MerchantTxnType eachType : values()) {
				if (eachType.getMerchantTxnType().equals(value)) {
					return eachType;
				}
			}
		}
		return null;
	}
}
