package com.snapdeal.opspanel.userpanel.request;

import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdRequest;

import lombok.Data;

@Data
public class SearchTxnByIdempotencyIdFlowRequest {
	
private GetTransactionByIdempotencyIdRequest request;

}
