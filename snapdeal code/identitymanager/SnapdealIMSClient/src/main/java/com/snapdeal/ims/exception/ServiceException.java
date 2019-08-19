package com.snapdeal.ims.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends Exception {

   private String errCode;
   private String errMsg;
   protected String startTime;
   protected String endTime;

   private static final long serialVersionUID = 1L;

   public ServiceException(String errMsg, String errCode, String startTime, String endTime) {
      super(errMsg);
      this.errCode = errCode;
      this.errMsg = errMsg;
      this.startTime = startTime;
      this.endTime = endTime;
   }
   
   public ServiceException(String errMsg, String errCode) {
      super(errMsg);
      this.errCode = errCode;
      this.errMsg = errMsg;
   }
      
}
