package com.snapdeal.merchant.exception;

public class HttpTransportException extends MerchantException {

   private static final long serialVersionUID = -5010990306824813850L;

   public HttpTransportException(String message, String code) {
      super(message, code);
   }

}
