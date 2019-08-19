package com.snapdeal.payments.view.merchant.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum MVTransactionStatus {

	PENDING("PENDING"), 
	SUCCESS("SUCCESS"), 
	FAILED("FAILED"),
	TO_BE_SETTLLED("TO_BE_SETTLLED"),
	SETTLED("SETTLED"),
	ROLLBACK("ROLLBACK"),
	REFUND("REFUND"),
	ON_HOLD("ON_HOLD");

	private final String txnStatusValue;
	
	private MVTransactionStatus(String txnStatusValue) {
		this.txnStatusValue = txnStatusValue;
	}
	@JsonValue
	public String getTxnStatusValue() {
		return this.txnStatusValue;
	}
	
	
	@JsonCreator
	public static MVTransactionStatus forValue(String value) {
		if (null != value) {
			for (MVTransactionStatus txnStatus : values()) {
				if (txnStatus.getTxnStatusValue().equals(value)) {
					return txnStatus;
				}
			}
		}
		return null;
	}

	
}
