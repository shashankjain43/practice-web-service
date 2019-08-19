package com.snapdeal.opspanel.batch.model;

import lombok.Data;

@Data
public  class BulkRefundInputCSVModel {
	String id;
	String amount;
	String comments;
	String platformId;
	String feeReversalCode;
}
