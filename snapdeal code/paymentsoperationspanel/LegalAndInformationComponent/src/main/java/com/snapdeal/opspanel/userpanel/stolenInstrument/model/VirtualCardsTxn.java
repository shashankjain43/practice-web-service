package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class VirtualCardsTxn extends StolenCardTxnModel{
	
	private String merchantName;
	
	private String merchantId;
	
	private String traceNumber;
	
	private String merchantCategoryCode;
	
	private String cardHashId;
	
}
