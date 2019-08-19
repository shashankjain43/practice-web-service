package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class WithdrawToBankTxn extends StolenCardTxnModel{
	
	private String accountNumber;
	
	private String bankName;
	
	private String ifsc;
	
	
	
	
	

}
