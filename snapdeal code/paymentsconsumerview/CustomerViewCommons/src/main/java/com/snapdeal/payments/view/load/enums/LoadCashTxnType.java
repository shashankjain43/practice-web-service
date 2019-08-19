package com.snapdeal.payments.view.load.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LoadCashTxnType {

	LOAD_CASH("LOAD_CASH"),
	LOAD_CASH_REVERSE("LOAD_CASH_REVERSE"),
	LOAD_CASH_REFUND("LOAD_CASH_REFUND");
	
	private final String loadViewTxnType;

	LoadCashTxnType(String loadViewTxnType) {
		this.loadViewTxnType = loadViewTxnType;
	}
	@JsonValue
	public String getLoadViewTxnType() {
		return this.loadViewTxnType;
	}
	
	@JsonCreator
	public static LoadCashTxnType forValue(String value) {
		if (null != value) {
			for (LoadCashTxnType eachType : values()) {
				if (eachType.getLoadViewTxnType().equals(value)) {
					return eachType;
				}
			}
		}
		return null;
	}
}
