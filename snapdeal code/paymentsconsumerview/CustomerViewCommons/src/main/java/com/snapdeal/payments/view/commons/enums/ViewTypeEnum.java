package com.snapdeal.payments.view.commons.enums;

import lombok.Getter;

public enum ViewTypeEnum {
   MERCHANTVIEW("merchant_view"),
   REQUESTVIEW("request_view");
   @Getter
   public String name;

   private ViewTypeEnum(String view) {
      name = view;
   }

   public String getMerchantName() {
      return name;
   }

   public static ViewTypeEnum forName(String value) {
      if (value != null) {
         for (ViewTypeEnum eachSrc : values()) {
            if (eachSrc.name().equalsIgnoreCase(value.trim())) {
               return eachSrc;
            }
         }
      }
      return null;
   }

}
