package com.snapdeal.opspanel.userpanel.response;

import lombok.Data;

@Data
public class FraudTransactionDetails {
	
	
	private String merchantName;
	
	private String transactionType;
	
	private String generalBalance;
	
	private String voucherBalance;
	
	private String runningBalance;
	
	private String transactionRefrence;
	
	private String bankAccountName;
	
	private String bankAccountNumber;
	
	private String bankIFSCCode;

}
