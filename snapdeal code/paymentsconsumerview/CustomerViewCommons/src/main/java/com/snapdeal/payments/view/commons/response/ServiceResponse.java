package com.snapdeal.payments.view.commons.response;

import java.util.Date;

import lombok.Data;

import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;

public @Data class ServiceResponse<T> {
   private T response;
   private PaymentsViewGenericException exception;
   private Date serverTimeStamp;

   public void setResponse(T response) {
      this.response = response;
      this.serverTimeStamp = new Date();
   }
}
