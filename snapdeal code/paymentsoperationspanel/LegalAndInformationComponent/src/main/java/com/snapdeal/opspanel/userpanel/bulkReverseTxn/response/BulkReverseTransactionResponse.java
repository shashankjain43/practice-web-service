package com.snapdeal.opspanel.userpanel.bulkReverseTxn.response;

import lombok.Data;

import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionResponse;

@Data
public class BulkReverseTransactionResponse {
	
	private String status;
	
	private ReverseTransactionResponse reverseTransactionResponse;

}
