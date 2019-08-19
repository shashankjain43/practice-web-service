package com.snapdeal.opspanel.userpanel.p2preversal.model;

import lombok.Data;

@Data
public class P2PReversalResponse {

	private String status;
	private String reverseTxnId;
	private String tsmTransactionState;
	private String sourceRunningBalance;
	private String error;
	private String remarks;

}
