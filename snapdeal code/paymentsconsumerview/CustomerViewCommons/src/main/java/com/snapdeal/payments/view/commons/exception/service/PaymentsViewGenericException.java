package com.snapdeal.payments.view.commons.exception.service;

import com.snapdeal.payments.view.commons.enums.ExceptionType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentsViewGenericException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   private String errCode;
   private String errMsg;
   private ExceptionType exceptionType ;

   public PaymentsViewGenericException(String errMsg) {
      super(errMsg);
      this.errMsg = errMsg;
   }
   public PaymentsViewGenericException(String errCode, String errMsg) {
	      super(errMsg);
	      this.errCode = errCode;
	      this.errMsg = errMsg;
	   }


   public PaymentsViewGenericException(String errCode, String errMsg,ExceptionType exceptionType) {
      super(errMsg);
      this.errCode = errCode;
      this.errMsg = errMsg;
      this.exceptionType = exceptionType ;
   }

   public PaymentsViewGenericException(String errCode, String message, Throwable cause) {
      super(message, cause);
      this.errCode = errCode;
      this.errMsg = message;
   }

   private Class<? extends PaymentsViewGenericException> exceptionCause;

   {
      this.setExceptionCause(this.getClass());
   }
}
