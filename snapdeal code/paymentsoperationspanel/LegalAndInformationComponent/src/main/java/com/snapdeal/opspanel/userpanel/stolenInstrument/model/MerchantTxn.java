package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import java.math.BigDecimal;

import lombok.Data;
@Data
public class MerchantTxn extends StolenCardTxnModel{
	
	private String merchantId;
	
	private String merchantName;

}
