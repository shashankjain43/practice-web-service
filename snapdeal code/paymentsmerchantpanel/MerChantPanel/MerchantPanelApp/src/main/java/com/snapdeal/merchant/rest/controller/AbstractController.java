package com.snapdeal.merchant.rest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;

import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.errorcodes.ErrorCodeMessageLoader;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;
import com.snapdeal.merchant.exception.MerchantException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AbstractController {

   @Autowired
   protected ErrorCodeMessageLoader errorMessageMap;

   protected GenericMerchantResponse getResponse(Object response) {
      GenericMerchantResponse mpResponse = new GenericMerchantResponse();
      mpResponse.setError(null);
      mpResponse.setData(response);
      return mpResponse;
   }

   protected void verifyError(BindingResult result, HttpServletRequest request)
            throws MerchantException {
      if (result.hasErrors() && null != result.getAllErrors()) {
         RequestExceptionCodes code = this.errorMessageMap
                  .getExceptionCodeFromErrorCode(result.getAllErrors().get(0).getDefaultMessage());
         log.error("Invalid Request Error occured while {} of user", request.getRequestURI());
         throw new MerchantException(code.getErrCode(), code.getErrMsg());
      }
   }

}
