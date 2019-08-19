package com.snapdeal.payments.view.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

public @Data class TransactionDetailsForDisbursment implements Serializable {

   private static final long serialVersionUID = 1L;
   private String txnId;
   private String settlementId;
   private Date settlementDate;
   private Date tsmTimeStamp;
   private String disbursementEngineMetadata;

}