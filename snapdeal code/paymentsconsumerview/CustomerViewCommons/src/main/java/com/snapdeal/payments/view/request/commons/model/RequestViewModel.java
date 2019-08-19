package com.snapdeal.payments.view.request.commons.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RequestViewModel {
	
	private String TxnId;
	private String TxnType;
	private String txnState;
	private String txnDisplayState;
	private String previousActionTaken;
	private String actionCommands;
	private String userId;
	private String userType;
	private String userDisplayInfo;
	private String otherPartyId;
	private String otherPartyDisplayInfo;
	private BigDecimal txnAmount;
	

}
