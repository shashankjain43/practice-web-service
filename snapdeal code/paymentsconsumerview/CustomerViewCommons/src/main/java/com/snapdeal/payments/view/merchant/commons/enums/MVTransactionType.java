package com.snapdeal.payments.view.merchant.commons.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MVTransactionType {

   DEBIT("DEBIT"), REFUND("REFUND"), PAYMENT("PAYMENT");

   private final String txnTypeValue;

   private MVTransactionType(String txnTypeValue) {
      this.txnTypeValue = txnTypeValue;
   }
   @JsonValue
   public String getTxnTypeValue() {
      return this.txnTypeValue;
   }

   @JsonCreator
   public static MVTransactionType forValue(String value) {
      if (null != value) {
         for (MVTransactionType txnType : values()) {
            if (txnType.getTxnTypeValue().equals(value)) {
               return txnType;
            }
         }
      }
      return null;
   }

}
