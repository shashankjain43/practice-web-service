package com.snapdeal.opspanel.userpanel.request;

import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceRequest;

import lombok.Data;

@Data
public class SearchTxnByReferenceFlowRequest {

	GetTransactionsByReferenceRequest transactionsByReferenceRequest;

}
