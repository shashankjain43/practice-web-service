package com.snapdeal.merchant.errorcodes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorCodeMappingUtil {

   @Autowired
   MOBtoMPErrorCodeMapping mobtoMPErrorCodeMapping;

   @Autowired
   RMStoMPErrorCodeMapping rmstoMPErrorCodeMapping;

   public MOBRequestExceptionCodes mobErrorCodeMapping(String mobCode) {

      return mobtoMPErrorCodeMapping.getMPForMOBErrorCode(mobCode);
   }

   public RMSRequestExceptionCodes rmsErrorCodeMapping(String rmsCode) {

      return rmstoMPErrorCodeMapping.getRMSForMOBErrorCode(rmsCode);
   }
}
