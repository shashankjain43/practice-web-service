package com.snapdeal.ims.enums;

public enum LinkUserVerifiedThrough {

   LINK_ACCOUNT_VIA_PASSWORD, 
   LINK_ACCOUNT_VIA_MOBILE_OTP,
   LINK_ACCOUNT_VIA_EMAIL_OTP,
   LINK_ACCOUNT_VIA_SOCIAL,
   LINK_ACCOUNT_VIA_PARKING;

   public static LinkUserVerifiedThrough forName(String value) {
      if (value != null) {
         for (LinkUserVerifiedThrough eachSrc : values()) {
            if (eachSrc.name().equalsIgnoreCase(value.trim())) {
               return eachSrc;
            }
         }
      }
      return null;
   }
}