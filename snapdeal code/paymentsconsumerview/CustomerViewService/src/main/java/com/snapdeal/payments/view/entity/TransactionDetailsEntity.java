package com.snapdeal.payments.view.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

public @Data class TransactionDetailsEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String txnId ;
	private String fcTxnId ;
	private String merchantTxnType ;
	private String txnType ;
	private String merchantTxnId;
	private String settlementId ;
	private Date settlementDate ;
	private String txnRef ;
	private String txnRefType ;
	private BigDecimal merchantFee ;
	private BigDecimal txnAmount ;
	private BigDecimal serviceTax ;
	private BigDecimal swachBharatCess ;
	private BigDecimal amountPayable ;
	private BigDecimal netDeduction ;
	private String merchantId ;
	private String userId ;
	private String merchantName ;
	private String customerName ;
	private String btsTxnRef ;
	private String storeId ;
	private String storeName ;
	private String productId ;
	private String terminalId  ;
	private String platform ;
	private String osVersion ;
	private String custmerIP ;
	private String location ;
	private String shippingCity ;
	private String source ;
	private Date txnDate ;
	private Date createdOn ;
	private Date updatedOn;
	private Date tsmTimeStamp ;
	private String merchantTag ;
	private String displayInfo ;
	private String txnMetaData;
	private String payableMetaData ;
	private String disbursementEngineMetadata;
}