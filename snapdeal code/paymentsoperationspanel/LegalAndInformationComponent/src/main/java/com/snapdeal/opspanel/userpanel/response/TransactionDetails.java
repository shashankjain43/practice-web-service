package com.snapdeal.opspanel.userpanel.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.snapdeal.payments.sdmoney.service.model.Balance;

import lombok.Data;

@Data
public class TransactionDetails {

   private String emailId;
   private String userId;
   private String walletTransactionId;
   private String transactionReference;

   @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm a z", timezone="Asia/Kolkata")
   private Date transactionDate;

   private String walletTransactionType;
   private String instrumentType;
   private String merchantName;

   private Balance transactionAmount;
   private Balance cumulativeBalance;
   private String walletAccountStatus;
   private String imsAccountStatus;
   private String migrationStatus;
   private String transactionStatus;

   //Card hash
   private String cardHash;
   private String reason;

   //Bank Details
   private String shortName;
   private String accountNumber;
   private String bankName;
   private String ifsc;
   
   private String metaData;
}
