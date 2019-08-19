package com.snapdeal.payments.view.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public @Data class RequestViewEntity {

	private String txnId ;
	private String fcTxnId;
	private String txnType;
	private String p2pTxnState;
	private String tsmTxnState;
	private String rvTxnState;
	private BigDecimal txnAmount;
	private String merchantTag;
	private PartyDetailsEntity srcPartyDetails ;
	private PartyDetailsEntity destPartyDetails ;
	private String metaData ;
	private Date txnDate;
	private Date tsmTimeStamp;
}
