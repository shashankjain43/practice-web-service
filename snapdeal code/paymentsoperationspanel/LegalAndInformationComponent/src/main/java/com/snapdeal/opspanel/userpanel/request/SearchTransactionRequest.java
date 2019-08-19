package com.snapdeal.opspanel.userpanel.request;

import java.util.Date;

import lombok.Data;

@Data
public class SearchTransactionRequest {

   private String emailId;
   private String userId;
   private String walletTransactionId;
   private String fcLoadingTransactionId;
   private String fcPaymentTransactionId;
   private String fcWithdrawlTransactionId;
   private String sdPaymentOrderId;
   private Date transactionStartDate;
   private Date transactionEndDate;
   private String walletTransactionType;
   private String instrumentType;
   private String merchantName;
   private int pageNumber;
   private String lastEvaluatedKey;
}
