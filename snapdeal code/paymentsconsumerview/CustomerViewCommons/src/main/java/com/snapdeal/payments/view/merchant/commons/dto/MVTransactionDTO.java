package com.snapdeal.payments.view.merchant.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionStatus;
import com.snapdeal.payments.view.merchant.commons.enums.MVTransactionType;

@Data
public class MVTransactionDTO implements Serializable {

   private static final long serialVersionUID = -2588371871848211280L;

   private Date txnDate;
   private String fcTxnId;
   private String fcTxnType;
   private String instrumentType;
   private String merchantTxnId;
   private String orderId;
   private MVTransactionType txnType; // filter
   private MVTransactionStatus txnStatus; // filter
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

   private String txnRefId;
   private String txnRefType;
   private String merchantTag ;
   private String usrDisplayInfo;
   private String displayInfo ;

   private String settlementId;
   private Date settlementDate ;
   private String txnMetaData;
   private String payableMetaData;
   
   private BigDecimal krishiKalyanCess ;
   private String partnerId;
   private String dealerId;
   private String platformId ;
   private String neftId ;
   private String disbursementMetaData;

}
