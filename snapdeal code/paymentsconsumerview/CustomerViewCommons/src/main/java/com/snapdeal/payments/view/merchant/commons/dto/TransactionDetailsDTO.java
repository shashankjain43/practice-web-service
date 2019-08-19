package com.snapdeal.payments.view.merchant.commons.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

public @Data class TransactionDetailsDTO implements Serializable{
		
		private static final long serialVersionUID = 1L;
		private String txnId ;
		private String fcTxnId ;
		private String merchantTxnType ;
		private String txnType ;
		private String transactionState;
		private Date updatedTime ;
		private String parentTxnId ;
		private String parentTxnType ;
}