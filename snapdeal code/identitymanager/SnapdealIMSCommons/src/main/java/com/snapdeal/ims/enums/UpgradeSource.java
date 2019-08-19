package com.snapdeal.ims.enums;

import lombok.Getter;

public enum UpgradeSource {

   SIGN_IN("SIGN_IN"), 
   SIGN_UP("SIGN_UP"), 
   ORDER_FLOW("ORDER_FLOW"), 
   THANK_YOU("THANK_YOU"),
   MY_ACCOUNT("MY_ACCOUNT"),
   RETURN_ORDER("RETURN_ORDER"),
   OTHERS("OTHERS"),
   ONECHECK_SOCIAL_SIGNUP("ONECHECK_SOCIAL_SIGNUP");

   @Getter
   private String name;

   private UpgradeSource(String name) {
      this.name = name;
   }

   public static UpgradeSource forName(String value) {
      if (value != null) {
         for (UpgradeSource eachSrc : values()) {
            if (eachSrc.name.equalsIgnoreCase(value.trim())) {
               return eachSrc;
            }
         }
      }
      return null;
   }
}
