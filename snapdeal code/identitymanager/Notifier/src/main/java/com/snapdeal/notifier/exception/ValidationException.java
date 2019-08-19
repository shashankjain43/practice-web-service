package com.snapdeal.notifier.exception;

public class ValidationException extends Exception {

   private static final long serialVersionUID = 1L;

   public ValidationException(String message) {
      super(message);
   }

   public ValidationException(String message, Throwable throwable) {
      super(message, throwable);
   }
}
