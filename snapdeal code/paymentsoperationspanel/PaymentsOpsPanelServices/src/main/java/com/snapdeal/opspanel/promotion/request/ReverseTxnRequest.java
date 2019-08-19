package com.snapdeal.opspanel.promotion.request;

import lombok.Data;

@Data
public class ReverseTxnRequest {
	
	
	private String prevTransactionId;
	private String reason;
	private String transactionReference;
	
	
}
