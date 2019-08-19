package com.snapdeal.opspanel.userpanel.request;

import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdRequest;

import lombok.Data;

@Data
public class SearchTxnByIdFlowRequest {

	private GetTransactionByIdRequest request;

}
