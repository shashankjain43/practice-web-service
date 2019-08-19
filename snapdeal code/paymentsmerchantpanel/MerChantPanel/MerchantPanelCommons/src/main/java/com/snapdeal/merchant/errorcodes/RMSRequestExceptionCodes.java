package com.snapdeal.merchant.errorcodes;

public enum RMSRequestExceptionCodes {

   CLIENT_INTERNAL(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
            ErrorConstants.GENERIC_INTERNAL_SERVER_MSG), INTERNAL_ERROR(
                     ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
                     ErrorConstants.GENERIC_INTERNAL_SERVER_MSG), SERVER_INTERNAL(
                              ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
                              ErrorConstants.GENERIC_INTERNAL_SERVER_MSG), No_SUCH_USER(
                                       ErrorConstants.USER_DOES_NOT_EXISTS_CODE,
                                       ErrorConstants.USER_DOES_NOT_EXISTS_MSG), DEFAULT_VALIDATION(
                                                ErrorConstants.USERID_AND_PASSWORD_WRONG_CODE,
                                                ErrorConstants.USERID_AND_PASSWORD_WRONG_MSG), USER_ALREADY_EXIST(
                                                         ErrorConstants.USER_ALREADY_EXISTS_CODE,
                                                         ErrorConstants.USER_ALREADY_EXISTS_MSG), ROLE_NAME_ALREADY_EXIST(
                                                                  ErrorConstants.ROLE_NAME_ALREADY_EXIST_CODE,
                                                                  ErrorConstants.ROLE_NAME_ALREADY_EXIST_MSG), UNAUTHRIZED_USER(
                                                                           ErrorConstants.SESSION_INVALID_CODE,
                                                                           ErrorConstants.SESSION_INVALID_MSG);

   private final String errCode;
   private final String errMsg;

   private RMSRequestExceptionCodes(String errCode, String errMsg) {
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