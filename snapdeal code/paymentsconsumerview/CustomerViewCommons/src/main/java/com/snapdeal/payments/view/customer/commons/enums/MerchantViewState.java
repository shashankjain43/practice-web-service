package com.snapdeal.payments.view.customer.commons.enums;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MerchantViewState {
   PENDING("PENDING"), SUCCESS("SUCCESS"), FAILED("FAILED"), ROLLED_BACK("ROLLBACK"), NOT_TO_REACH_HERE(
            "NOT_TO_REACH_HERE"), SETTLED("SETTLED"),REFUND("REFUND"),ON_HOLD("ON_HOLD");
   @Getter
   private final String name;

   MerchantViewState(String name) {
      this.name = name;
   }
   @JsonValue
   public String getMerchantViewState() {
      return this.name;
   }

   @JsonCreator
   public static MerchantViewState forValue(String value) {
      if (null != value) {
         for (MerchantViewState eachType : values()) {
            if (eachType.getMerchantViewState().equals(value)) {
               return eachType;
            }
         }
      }
      return null;
   }
}
