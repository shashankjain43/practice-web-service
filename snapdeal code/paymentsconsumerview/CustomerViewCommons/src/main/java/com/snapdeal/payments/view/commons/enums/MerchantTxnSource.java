package com.snapdeal.payments.view.commons.enums;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MerchantTxnSource {

   AGGREGATOR("AGGREGATOR"),
   OPS("OPS"), 
   DISBURSEMENT_ENGINE("DISBURSEMENT_ENGINE"), 
   PAYABLES("PAYABLES"),
   P2M("P2M"),
   ESCROW("ESCROW");
   @Getter
   private final String merchantTxnSource;

   MerchantTxnSource(String merchantTxnSource) {
      this.merchantTxnSource = merchantTxnSource;
   }

   @JsonValue
   public String getMerchantTxnSource() {
      return this.merchantTxnSource;
   }

   @JsonCreator
   public static MerchantTxnSource forValue(String value) {
      if (null != value) {
         for (MerchantTxnSource eachType : values()) {
            if (eachType.getMerchantTxnSource().equals(value)) {
               return eachType;
            }
         }
      }
      return null;
   }
}
