package com.snapdeal.merchant.errorcodes;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ErrorCodeMessageLoader {

   private static Map<String, RequestExceptionCodes> reqErrorMap = new HashMap<String, RequestExceptionCodes>();

   @PostConstruct
   private void loadErrorMap() {
      // reqErrorMap = new HashMap<String, RequestExceptionCodes>();
      for (RequestExceptionCodes error : RequestExceptionCodes.values()) {
         reqErrorMap.put(error.getErrCode(), error);
      }
   }

   public RequestExceptionCodes getExceptionCodeFromErrorCode(String errCode) {
      return reqErrorMap.get(errCode);
   }

}
