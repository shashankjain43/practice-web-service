package com.snapdeal.payments.view.request.commons.enums;

import lombok.Getter;

public enum EscrowViewState {

	MONEY_REFUNDED("MONEY_REFUNDED","REFUND"),
	MONEY_RECIEVED("MONEY_SENT","MONEY_RECIEVED"),
	INITIALIZED("MONEY_SENT","ON_HOLD"),
	PENDING("PENDING","PENDING"),
	FAILED("FAILED","FAILED"),
	DECLINED("FAILED","DECLINED");
	

	
	@Getter
	private String sourceStatus,destStatus;
	private EscrowViewState(String source,String dest) {
		this.sourceStatus=source;
		this.destStatus=dest;
	}
	
	

}
