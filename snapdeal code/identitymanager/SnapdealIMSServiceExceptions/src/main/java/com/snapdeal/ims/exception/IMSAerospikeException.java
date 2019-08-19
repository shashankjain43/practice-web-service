package com.snapdeal.ims.exception;

public class IMSAerospikeException extends IMSGenericException{

   private static final long serialVersionUID = 3480539223261710510L;

   public IMSAerospikeException(String errCode, String errMsg) {
      super(errCode, errMsg);
   }
}
