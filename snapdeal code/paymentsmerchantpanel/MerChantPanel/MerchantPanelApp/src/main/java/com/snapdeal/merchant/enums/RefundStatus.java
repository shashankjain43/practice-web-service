package com.snapdeal.merchant.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using=RefundStatusSerializer.class)
public enum RefundStatus {

	PARTIAL_SUCCESS("Partial Success"),
	FAILED("Failed"),
	SUCCESS("Success"),
	INDETERMINANT("Indeterminant"),
	INITIATED("Initiated");
	
	private final String status;
	
	private RefundStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return status;
	}
}
