package com.snapdeal.merchant.errorcodes;

//Error codes and error messages for invalid request parameters

public enum RequestExceptionCodes {

   GENERIC_INTERNAL_SERVER(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
            ErrorConstants.GENERIC_INTERNAL_SERVER_MSG), SESSION_INVALID(
                     ErrorConstants.SESSION_INVALID_CODE, ErrorConstants.SESSION_INVALID_MSG),

   INTERNAL_CLIENT(ErrorConstants.INTERNAL_CLIENT_CODE, ErrorConstants.INTERNAL_CLIENT_MSG),

   TOKEN_IS_BLANK(ErrorConstants.TOKEN_IS_BLANK_CODE, ErrorConstants.TOKEN_IS_BLANK_MSG),

   USER_DOES_NOT_EXISTS(ErrorConstants.USER_DOES_NOT_EXISTS_CODE,
            ErrorConstants.USER_DOES_NOT_EXISTS_MSG), USER_PERMISSION_INVALID(
                     ErrorConstants.USER_PERMISSION_INVALID_CODE,
                     ErrorConstants.USER_PERMISSION_INVALID_MSG),
   
   USERNAME_FORMAT_INCORRECT(ErrorConstants.USERID_FORMAT_INCORRECT_CODE,
            ErrorConstants.USERID_FORMAT_INCORRECT_MSG), USERNAME_IS_BLANK(
                     ErrorConstants.USERID_IS_BLANK_CODE,
                     ErrorConstants.USERID_IS_BLANK_MSG), LOGIN_NAME_IS_BLANK(
                              ErrorConstants.LOGIN_NAME_IS_BLANK_CODE,
                              ErrorConstants.LOGIN_NAME_IS_BLANK_MSG),
   USER_IDENTIFIER_IS_BLANK(ErrorConstants.USER_IDENTIFIER_IS_BLANK_CODE,ErrorConstants.USER_IDENTIFIER_IS_BLANK_MSG),
   										

   MERCHANTID_IS_BLANK(ErrorConstants.MERCHANTID_IS_BLANK_CODE,
            ErrorConstants.MERCHANTID_IS_BLANK_MSG), MERCHANT_ID_IN_REQUEST_INVALID(
                     ErrorConstants.MERCHANT_ID_IN_REQUEST_INVALID_CODE,
                     ErrorConstants.MERCHANT_ID_IN_REQUEST_INVALID_MSG),

   WRONG_PASSWORD(ErrorConstants.WRONG_PASSWORD_CODE,
            ErrorConstants.WRONG_PASSWORD_MSG), PASSWORD_IS_BLANK(
                     ErrorConstants.PASSWORD_IS_BLANK_CODE,
                     ErrorConstants.PASSWORD_IS_BLANK_MSG), INVALID_CHARACTER_PASSWORD(
                              ErrorConstants.INVALID_CHARACTER_PASSWORD_CODE,
                              ErrorConstants.INVALID_CHARACTER_PASSWORD_MSG),

   EMAIL_IS_BLANK(ErrorConstants.EMAIL_IS_BLANK_CODE,
            ErrorConstants.EMAIL_IS_BLANK_MSG), EMAIL_FORMAT_INCORRECT(
                     ErrorConstants.EMAIL_FORMAT_INCORRECT_CODE,
                     ErrorConstants.EMAIL_FORMAT_INCORREC_MSG),

   SOCIAL_TOKEN_IS_BLANK(ErrorConstants.SOCIAL_TOKEN_IS_BLANK_CODE,
            ErrorConstants.SOCIAL_TOKEN_IS_BLANK_MSG), SOCIAL_SOURCE_IS_BLANK(
                     ErrorConstants.SOCIAL_SOURCE_IS_BLANK_CODE,
                     ErrorConstants.SOCIAL_SOURCE_IS_BLANK_MSG),
   
   TXN_ID_IS_BLANK(ErrorConstants.TXN_ID_IS_BLANK_CODE,ErrorConstants.TXN_ID_IS_BLANK_MSG),
   AMOUNT_IS_BLANK(ErrorConstants.AMOUNT_IS_BLANK_CODE,ErrorConstants.AMOUNT_IS_BLANK_MSG),
   NO_RECORD_TO_EXPORT(ErrorConstants.NO_RECORD_TO_EXPORT_CODE,ErrorConstants.NO_RECORD_TO_EXPORT_MSG),
	USER_NAME_IS_BLANK(ErrorConstants.USER_NAME_IS_BLANK_CODE , ErrorConstants.USER_NAME_IS_BLANK_MSG),
	OTP_CANNOT_BE_BLANK(ErrorConstants.OTP_CANNOT_BE_BLANK_CODE , ErrorConstants.OTP_CANNOT_BE_BLANK_MSG),
	OTP_ID_CANNOT_BE_BLANK(ErrorConstants.OTP_ID_CANNOT_BE_BLANK_CODE , ErrorConstants.OTP_ID_CANNOT_BE_BLANK_MSG),
	ORDER_ID_IS_BLANK(ErrorConstants.ORDER_ID_IS_BLANK_CODE , ErrorConstants.ORDER_ID_IS_BLANK_MSG),
	USER_ID_IS_BLANK(ErrorConstants.USER_ID_IS_BLANK_CODE , ErrorConstants.USER_ID_IS_BLANK_MSG),
	EMAIL_CONTENT_IS_BLANK(ErrorConstants.EMAIL_CONTENT_IS_BLANK_CODE , ErrorConstants.EMAIL_CONTENT_IS_BLANK_MSG),
	ISSUE_TYPE_IS_BLANK(ErrorConstants.ISSUE_TYPE_IS_BLANK_CODE, ErrorConstants.ISSUE_TYPE_IS_BLANK_MSG),
	
	
	 MOBILE_IS_BLANK(ErrorConstants.MOBILE_IS_BLANK_CODE,
	            ErrorConstants.MOBILE_IS_BLANK_MSG), 
	 MOBILE_INVALID_FORMAT(
	                     ErrorConstants.MOBILE_INVALID_CODE,
	                     ErrorConstants.MOBILE_INVALID_MSG),
	
	 COMPANY_NAME_BLANK(ErrorConstants.COMPANY_NAME_BLANK_CODE , ErrorConstants.COMPANY_NAME_BLANK_MSG),
	 CUSTOMER_NAME_BLANK(ErrorConstants.CUSTOMER_NAME_BLANK_CODE , ErrorConstants.CUSTOMER_NAME_BLANK_MSG),
	
	 BUSINESS_ADDRESS_BLANK(ErrorConstants.BUSINESS_ADDRESS_BLANK_CODE , ErrorConstants.BUSINESS_ADDRESS_BLANK_MSG),
	
	 BUSINESS_CATEGORY_BLANK(ErrorConstants.BUSINESS_CATEGORY_BLANK_CODE,ErrorConstants.BUSINESS_CATEGORY_BLANK_MSG),
	
	 NO_OF_STORE_BLANK(ErrorConstants.NO_OF_STORE_BLANK_CODE , ErrorConstants.NO_OF_STORE_BLANK_MSG),
	 DAILY_NO_OF_TXN_BLANK (ErrorConstants.DAILY_NO_OF_TXN_BLANK_CODE , ErrorConstants.DAILY_NO_OF_TXN_BLANK_MSG),
	
	LANDLINE_NUMBER_LENGTH(ErrorConstants.LANDLINE_NUMBER_LENGTH_CODE , ErrorConstants.LANDLINE_NUMBER_LENGTH_MSG),
	LANDLINE_INVALID( ErrorConstants.LANDLINE_INVALID_CODE , ErrorConstants.LANDLINE_INVALID_MSG);

	
   private final String errCode;
   private final String errMsg;

   private RequestExceptionCodes(String errCode, String errMsg) {
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