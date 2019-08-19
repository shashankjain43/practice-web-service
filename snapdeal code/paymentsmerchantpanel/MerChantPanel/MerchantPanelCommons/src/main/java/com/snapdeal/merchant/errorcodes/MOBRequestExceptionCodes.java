package com.snapdeal.merchant.errorcodes;

public enum MOBRequestExceptionCodes {

   USER_DOES_NOT_ASSOCIATED_WITH_MERCHANT(
            ErrorConstants.USER_DOES_NOT_ASSOCIATED_WITH_MERCHANT_CODE,
            ErrorConstants.USER_DOES_NOT_ASSOCIATED_WITH_MERCHANT_MSG), SESSION_INVALID(
                     ErrorConstants.SESSION_INVALID_CODE,
                     ErrorConstants.SESSION_INVALID_MSG), REQUEST_MERCHANT_INVALID(
                              ErrorConstants.MERCHANT_ID_IN_REQUEST_INVALID_CODE,
                              ErrorConstants.MERCHANT_ID_IN_REQUEST_INVALID_MSG), USER_PERMISSION_INVALID(
                                       ErrorConstants.USER_PERMISSION_INVALID_CODE,
                                       ErrorConstants.USER_PERMISSION_INVALID_MSG), USER_ALREADY_EXISTS(
                                                ErrorConstants.USER_ALREADY_EXISTS_CODE,
                                                ErrorConstants.USER_ALREADY_EXISTS_MSG), MERCHANTID_IS_BLANK(
                                                         ErrorConstants.MERCHANTID_IS_BLANK_CODE,
                                                         ErrorConstants.MERCHANTID_IS_BLANK_MSG);

   private final String errCode;
   private final String errMsg;

   private MOBRequestExceptionCodes(String errCode, String errMsg) {
      this.errCode = errCode;
      this.errMsg = errMsg;
   }

   public String getErrCode() {
      return this.errCode;
   }

   public String getErrMsg() {
      return this.errMsg;
   }

}