package com.snapdeal.merchant.dto;

import lombok.Data;

@Data
public class MPSearch {

   private String transactionId;
   private String merchantTxnId;
   private String settlementId;
   private String customerId;
   private String orderId;
   private String productId;
   private String terminalId;
   private String storeId;

}
