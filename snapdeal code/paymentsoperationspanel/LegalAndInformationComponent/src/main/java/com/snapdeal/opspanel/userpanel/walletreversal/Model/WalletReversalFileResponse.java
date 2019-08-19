package com.snapdeal.opspanel.userpanel.walletreversal.Model;

import lombok.Data;

@Data
public class WalletReversalFileResponse {

	private String status;
	private String transactionId;
	private String txnTimeStamp;
	private String error;
	
}
