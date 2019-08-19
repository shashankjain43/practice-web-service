package com.snapdeal.opspanel.userpanel.p2preversal.model;

import com.snapdeal.payments.p2pengine.model.enums.TSMTransactionState;

import lombok.Data;

@Data
public class P2PFullReversalResponse {

	    private String transactionId;
	    private java.util.Date transactionTimeStamp;
	    private TSMTransactionState transactionStatus;
	    private String status;
	    private String error;
	
}
