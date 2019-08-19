package com.snapdeal.merchant.enums;

/**
 * This enum defines the various request header supported.
 */
public enum MPanelRequestHeader {

   CONTENT_TYPE("content-type"),
   ACCEPT("accept"),
   CLIENT_IP_ADDRESS("clientIpAddress"),
   CLIENT_SDK_VERSION("client-version"),
   TIMESTAMP("timestamp"),
   REQUEST_URI("Request-URI"), 
   HASH("hash"),
   HTTPMETHOD("httpmethod"),
   SERVER_IP_ADDRESS("serverIpAddress"),
   TOKEN("token"),
   MERCHANTID("merchantId");
   
   private String description;

   private MPanelRequestHeader(String description) {
      this.description = description;
   }

   public String toString() {
      return description;
   }
}
