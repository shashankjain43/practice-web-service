package com.snapdeal.payments.view.entity;

import java.io.Serializable;

import lombok.Data;

public @Data class TransactionDetailChildEntity  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String fcTxnID ;
	private String childFcTxnId ;
	private String txnType ;
	private String childTxnType ;
}
