package com.snapdeal.opspanel.userpanel.response;

import java.util.List;

import lombok.Data;

@Data
public class SearchTxnResponse {

	private List<TransactionDetails> transactionDetailsList;
	private String lastEvaluatedKey;
	
}
