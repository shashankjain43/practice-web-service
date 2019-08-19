package com.snapdeal.opspanel.userpanel.response;

import java.util.ArrayList;
import java.util.List;

import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;

import lombok.Data;

@Data
public class SearchTxnFlowResponse {

	private List<TransactionSummary> txnSummaryList= new ArrayList<TransactionSummary> ();
	private String userId; 
	private String lastEvaluatedKey;
}
