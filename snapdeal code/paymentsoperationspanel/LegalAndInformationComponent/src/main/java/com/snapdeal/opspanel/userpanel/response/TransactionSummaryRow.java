package com.snapdeal.opspanel.userpanel.response;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionSummaryRow {
	
	private Date date;
	private String transactionAmount;
	private String generalBalanceTxnAmount;
	private String giftVoucherTxnAmount;
	private String type;
	private String txnRef;
	private String postTxnBalance;

}
