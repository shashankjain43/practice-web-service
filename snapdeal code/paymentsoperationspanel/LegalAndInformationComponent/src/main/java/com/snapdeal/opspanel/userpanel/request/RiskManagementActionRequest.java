package com.snapdeal.opspanel.userpanel.request;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class RiskManagementActionRequest {

   private String transactionIdType;
   private String transactionId;
   private List<String> actions;
   private BigDecimal fraudAmount;
   private String reason;
}
