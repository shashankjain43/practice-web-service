package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class StolenCardTxnModel {
	
	private BigDecimal amount;
	
	private Date creationDate;

}
