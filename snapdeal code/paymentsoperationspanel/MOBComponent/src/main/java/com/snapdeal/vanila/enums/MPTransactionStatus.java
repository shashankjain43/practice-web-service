package com.snapdeal.vanila.enums;

public enum MPTransactionStatus {

	PENDING("PENDING"), 
	SUCCESS("SUCCESS"), 
	FAILED("FAILED"), 
	SETTLED("SETTLED"),
	TO_BE_SETTLLED("TO_BE_SETTLLED"),
	ROLLBACK("ROLLBACK");
	
	private final String txnStatusValue;

	private MPTransactionStatus(String txnStatusValue) {
		this.txnStatusValue = txnStatusValue;
	}

	public String getTxnStatusValue() {
		return txnStatusValue;
	}
}