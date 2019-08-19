package com.snapdeal.merchant.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import com.snapdeal.merchant.enums.MPTransactionStatus;
import com.snapdeal.merchant.enums.MPTransactionType;

@Data
public class MPTransactionDTO {

	private Date txnDate;
	private String fcTxnId;
	private String merchantTxnId;
	private String orderId;
	private MPTransactionType txnType;   
	private MPTransactionStatus txnStatus;
	
	//payable MetaData
	private BigDecimal merchantFee;
	private BigDecimal serviceTax;
	private BigDecimal swachBharatCess;
	private BigDecimal totalTxnAmount;
	private BigDecimal netDeduction;
	private BigDecimal payableAmount;
	private BigDecimal krishiKalyanCess;
	
	private String merchantId;
	private String merchantName;
	private String storeId;
	private String storeName;
	private String terminalId;
	private String custId;
	private String custName;
	private String custIP;
	private String productId;
	private String location;
	private String shippingCity;
	
	private String settlementId;
	
	private String txnRefId;
	private String txnRefType;
	
	private String mobile ;
	private String email;
	
	private String platformId;
	private String dealerId;
	private String patnerId ;
	

}