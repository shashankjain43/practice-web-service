package com.snapdeal.merchant.errorcodes;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RMStoMPErrorCodeMapping {

   private static Map<String, RMSRequestExceptionCodes> errorCodeMap = new HashMap<String, RMSRequestExceptionCodes>();

   @PostConstruct
   private void loadErrorMap() {

      for (RMStoMPErrorCodeEnum error : RMStoMPErrorCodeEnum.values()) {
         if (error.useMPCode == true) {
            errorCodeMap.put(error.rmsErrorCode, error.merchantErrorEnum);
         }
      }
   }

   public RMSRequestExceptionCodes getRMSForMOBErrorCode(String rmsCode) {
      return errorCodeMap.get(rmsCode);
   }

   enum RMStoMPErrorCodeEnum {
      ER_501("501", RMSRequestExceptionCodes.CLIENT_INTERNAL, true), ER_502("502",
               RMSRequestExceptionCodes.INTERNAL_ERROR,
               true), ER_503("503", RMSRequestExceptionCodes.SERVER_INTERNAL, true), ER_504("504",
                        RMSRequestExceptionCodes.No_SUCH_USER, false), ER_505("505",
                                 RMSRequestExceptionCodes.DEFAULT_VALIDATION, false), ER_506("506",
                                          RMSRequestExceptionCodes.USER_ALREADY_EXIST,
                                          false), ER_507("507",
                                                   RMSRequestExceptionCodes.ROLE_NAME_ALREADY_EXIST,
                                                   true), ER_508("508",
                                                            RMSRequestExceptionCodes.UNAUTHRIZED_USER,
                                                            false);

      private String rmsErrorCode;

      private RMSRequestExceptionCodes merchantErrorEnum;

      private boolean useMPCode;

      RMStoMPErrorCodeEnum(String rmsErrorCode, RMSRequestExceptionCodes merchantErrorEnum,
               boolean useMPCode) {
         this.rmsErrorCode = rmsErrorCode;
         this.merchantErrorEnum = merchantErrorEnum;
         this.useMPCode = useMPCode;
      }

   }

}