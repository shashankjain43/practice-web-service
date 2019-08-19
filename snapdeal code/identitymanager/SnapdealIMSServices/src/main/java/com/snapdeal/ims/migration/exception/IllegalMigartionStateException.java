package com.snapdeal.ims.migration.exception;

import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.IMSGenericException;

public class IllegalMigartionStateException extends IMSGenericException {

   private static final long serialVersionUID = 1L;

   public IllegalMigartionStateException(String errCode,String errMsg) {
      super(errCode,errMsg);
   }

   public IllegalMigartionStateException() {
      this(IMSDefaultExceptionCodes.MIGRATION_EXCEPTION.errCode(),
           IMSDefaultExceptionCodes.MIGRATION_EXCEPTION.errMsg());
   }
}
