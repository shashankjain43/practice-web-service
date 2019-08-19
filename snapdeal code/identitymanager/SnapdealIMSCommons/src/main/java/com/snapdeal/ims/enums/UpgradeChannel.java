package com.snapdeal.ims.enums;

import lombok.Getter;

public enum UpgradeChannel {

   MOBILE_WAP("MOBILE_WAP"),

   //MOBILE("MOB_WAP"), 
   WEB("WEB"), 
   CRM("CRM"), 
   OMS("OMS"), 
   OMS_BUYFLOW("OMS_BUYFLOW"), 
   BRAND_STORE("BRAND_STORE"), 
   SELECT("SELECT"), 
   IOS_APP("IOS_APP"),
   WIN_APP("WIN_APP"),
   ANDROID_APP("ANDROID_APP"),
   OTHERS("OTHERS");


   @Getter
   private String name;

   private UpgradeChannel(String name) {
      this.name = name;
   }

   public static UpgradeChannel forName(String value) {
      if (value != null) {
         for (UpgradeChannel eachSrc : values()) {
            if (eachSrc.name().equalsIgnoreCase(value.trim())) {
               return eachSrc;
            }
         }
      }
      return null;
   }
}