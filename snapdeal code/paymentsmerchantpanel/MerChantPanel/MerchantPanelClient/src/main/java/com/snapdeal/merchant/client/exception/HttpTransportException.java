package com.snapdeal.merchant.client.exception;

public class HttpTransportException extends Exception {

   private int status;
   private static final long serialVersionUID = 1L;

   public HttpTransportException(String message) {
      super(message);
   }

   public HttpTransportException(String message, int code) {
      super(message);
      this.setStatus(code);
   }

   public HttpTransportException(String message, Throwable cause) {
      super(message, cause);
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }
}
