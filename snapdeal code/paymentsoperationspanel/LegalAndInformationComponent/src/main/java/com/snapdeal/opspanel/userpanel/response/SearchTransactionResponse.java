package com.snapdeal.opspanel.userpanel.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class SearchTransactionResponse {

   private String emailId;
   private String userId;
   private String walletTransactionId;
   private String fcLoadingTransactionId;
   private String fcPaymentTransactionId;
   private String fcWithdrawlTransactionId;
   private String sdPaymentOrderId;
   private String transactionStartDate;
   private String transactionEndDate;
   private String walletTransactionType; //TODO ask for enums
   private String merchantId;

   private BigDecimal transactionAmount;
   private BigDecimal cumulativeBalance;
   private String walletAccountStatus; //TODO ask for enums
   private String imsAccountStatus;  //TODO ask for enums
   private String migrationStatus;  //TODO ask for enums
   private String errorCode;

}
