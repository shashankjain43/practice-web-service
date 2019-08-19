package com.snapdeal.payments.view.merchant.commons.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

public @Data class TransactionPaybleDto implements Serializable {

   private static final long serialVersionUID = 1L;

   private String fcTxnId;
   private String fcTxnType;

   private BigDecimal merchantFee;
   private BigDecimal serviceTax;
   private BigDecimal swachBharatCess;
   private BigDecimal netDeduction;
   private BigDecimal txnAmount;
   private BigDecimal amountPayable;
   private String btsTxnRef;
   private Date tsmTimeStamp;
   private String payableMetaData ;

}
