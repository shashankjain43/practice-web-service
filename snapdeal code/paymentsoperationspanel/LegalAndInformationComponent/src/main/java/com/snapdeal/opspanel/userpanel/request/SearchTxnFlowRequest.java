package com.snapdeal.opspanel.userpanel.request;

import com.snapdeal.payments.sdmoney.service.model.GetTransactionsRequest;

import lombok.Data;

@Data
public class SearchTxnFlowRequest {

	private String searchKey;
	private GetTransactionsRequest searchTxnRequest;
}
