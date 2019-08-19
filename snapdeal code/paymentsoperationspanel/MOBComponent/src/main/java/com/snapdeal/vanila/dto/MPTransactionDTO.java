package com.snapdeal.vanila.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.snapdeal.vanila.enums.MPTransactionStatus;
import com.snapdeal.vanila.enums.MPTransactionType;

import lombok.Data;

@Data
public class MPTransactionDTO {

	private String txnDate;
	// private Date refundDate;
	private String fcTxnId;
	private String merchantTxnId;
	private String orderId;
	private String txnRefId;
	private MPTransactionType txnType; // filter
	private MPTransactionStatus txnStatus; // filter
	// private MVRefundStatus refundStatus;
	// private MVRefundType refundType;
	private BigDecimal merchantFee;
	private BigDecimal serviceTax;
	private BigDecimal swachBharatCess;
	private BigDecimal totalTxnAmount;
	private BigDecimal netDeduction;
	private BigDecimal payableAmount;

	// Choosable fields
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
	// private String settlementStatus; //filter

	private String email;
	private String mobile;
	
	//New Fields Added After Merchant Hierarchy
	
	private BigDecimal krishiKalyanCess;
	private String partnerId;
	private String dealerId;
	private String platformId;

}
