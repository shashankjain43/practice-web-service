package com.snapdeal.merchant.errorcodes;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class MOBtoMPErrorCodeMapping {

   private static Map<String, MOBRequestExceptionCodes> errorCodeMap = new HashMap<String, MOBRequestExceptionCodes>();

   @PostConstruct
   private void loadErrorMap() {

      for (MOBtoMPErrorCodeEnum error : MOBtoMPErrorCodeEnum.values()) {
         errorCodeMap.put(error.mobErrorCode, error.merchantErrorEnum);
      }
   }

   public MOBRequestExceptionCodes getMPForMOBErrorCode(String mobCode) {
      return errorCodeMap.get(mobCode);
   }

   enum MOBtoMPErrorCodeEnum {
      ER_1101("ER-1101", MOBRequestExceptionCodes.SESSION_INVALID), ER_1102("ER-1102",
               MOBRequestExceptionCodes.USER_PERMISSION_INVALID), ER_1103("ER-1103",
                        MOBRequestExceptionCodes.USER_DOES_NOT_ASSOCIATED_WITH_MERCHANT), ER_5129(
                                 "ER-5129",
                                 MOBRequestExceptionCodes.USER_ALREADY_EXISTS), ER_5101("ER_5101",
                                          MOBRequestExceptionCodes.SESSION_INVALID), ER_4154(
                                                   "ER-4154",
                                                   MOBRequestExceptionCodes.MERCHANTID_IS_BLANK);

      private String mobErrorCode;

      private MOBRequestExceptionCodes merchantErrorEnum;

      MOBtoMPErrorCodeEnum(String mobErrorCode, MOBRequestExceptionCodes merchantErrorEnum) {
         this.mobErrorCode = mobErrorCode;
         this.merchantErrorEnum = merchantErrorEnum;
      }

   }
}