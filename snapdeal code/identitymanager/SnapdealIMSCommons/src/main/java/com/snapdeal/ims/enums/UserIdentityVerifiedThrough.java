package com.snapdeal.ims.enums;

public enum UserIdentityVerifiedThrough {
   
   EMAIL_PWD_VERIFIED, 
   FACEBOOK_VERIFIED, 
   GOOGLE_VERIFIED, 
   MOBILE_PWD_VERIFIED, 
   MOBILE_OTP_VERIFIED,
   TOKEN_VERIFIED;

   public static UserIdentityVerifiedThrough forName(String value) {
      if (value != null) {
         for (UserIdentityVerifiedThrough eachSrc : values()) {
            if (eachSrc.name().equalsIgnoreCase(value.trim())) {
               return eachSrc;
            }
         }
      }
      return null;
   }
}

