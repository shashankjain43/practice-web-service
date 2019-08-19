package com.snapdeal.opspanel.batch.model;

import lombok.Data;

public @Data class BulkRefundOutputCSVModel {

	String id;
	String amount;
	String comments;
	String platformId;
	String feeReversalCode;
	String status;
	String message;
}
