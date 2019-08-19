package com.snapdeal.ims.migration.exception;

import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.exception.IMSGenericException;

public class IMSMigrationHardDeclinedException extends IMSGenericException {

   private static final long serialVersionUID = 1L;

   public IMSMigrationHardDeclinedException(String errCode,String errMsg) {
      super(errCode,errMsg);
   }

   public IMSMigrationHardDeclinedException() {
      this(IMSMigrationExceptionCodes.OTHER_SIDE_SERVICE_DOWN.errCode(),
           IMSMigrationExceptionCodes.OTHER_SIDE_SERVICE_DOWN.errMsg());
   }
}
