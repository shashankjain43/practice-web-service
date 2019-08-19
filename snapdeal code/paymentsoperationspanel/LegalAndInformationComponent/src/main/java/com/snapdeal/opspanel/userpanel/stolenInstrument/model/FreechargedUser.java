package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class FreechargedUser extends StolenCardTxnModel{
	
	private String name;
	
	private String email;
	
	private String mobile;
	
	private String userId;

}
