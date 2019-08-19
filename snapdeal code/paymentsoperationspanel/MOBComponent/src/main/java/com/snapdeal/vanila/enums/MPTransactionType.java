package com.snapdeal.vanila.enums;

public enum MPTransactionType {

	DEBIT("DEBIT"),
	REFUND("REFUND"),
	PAYMENT("PAYMENT");
	
	private final String txnTypeValue;

	private MPTransactionType(String txnTypeValue) {
		this.txnTypeValue = txnTypeValue;
	}

	public String getTxnTypeValue() {
		return txnTypeValue;
	}

}
