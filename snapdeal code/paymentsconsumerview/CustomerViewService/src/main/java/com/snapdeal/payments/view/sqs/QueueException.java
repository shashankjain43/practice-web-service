package com.snapdeal.payments.view.sqs;

import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;

public class QueueException extends PaymentsViewGenericException {

   private static final long serialVersionUID = 3159264538892418722L;
   
   public QueueException(String e){
      super(e);
   }
   
   public QueueException(Exception e){
      super();
   }

}
