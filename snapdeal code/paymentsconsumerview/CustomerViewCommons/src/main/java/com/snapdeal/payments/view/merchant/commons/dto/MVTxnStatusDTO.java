package com.snapdeal.payments.view.merchant.commons.dto;

import java.util.Date;

import lombok.Data;

@Data
public class MVTxnStatusDTO {

	private String txnId;
	private String txnType ;
	private String instrumentType;
	private String txnState;
	private String txnRef;
	private String txnRefType;
	private Date txnDate ;
}
