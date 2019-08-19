package com.snapdeal.payments.view.sqs;

public class NotificationReaderException extends RuntimeException {

   private static final long serialVersionUID = 3159264538892418722L;
   
   public NotificationReaderException(String e){
      super(e);
   }
   
   public NotificationReaderException(Exception e){
      super(e);
   }

}
