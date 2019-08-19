package com.snapdeal.payments.view.load.enums;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum LoadCashTxnStatus {
   PENDING("PENDING"), SUCCESS("SUCCESS"), FAILED("FAILED"), ROLLED_BACK("ROLLBACK"), NOT_TO_REACH_HERE(
            "NOT_TO_REACH_HERE");
   @Getter
   private final String name;

   LoadCashTxnStatus(String name) {
      this.name = name;
   }
   @JsonValue
   public String getLoadViewState() {
      return this.name;
   }

   @JsonCreator
   public static LoadCashTxnStatus forValue(String value) {
      if (null != value) {
         for (LoadCashTxnStatus eachType : values()) {
            if (eachType.getLoadViewState().equals(value)) {
               return eachType;
            }
         }
      }
      return null;
   }
}
