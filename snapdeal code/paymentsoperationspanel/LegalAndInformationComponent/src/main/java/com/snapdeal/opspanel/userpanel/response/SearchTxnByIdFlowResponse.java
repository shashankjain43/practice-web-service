package com.snapdeal.opspanel.userpanel.response;

import org.json.JSONObject;

import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdResponse;

import lombok.Data;

@Data
public class SearchTxnByIdFlowResponse {

	private GetTransactionByIdResponse transactionByIdResponse;
	private String addCashJson;
}
