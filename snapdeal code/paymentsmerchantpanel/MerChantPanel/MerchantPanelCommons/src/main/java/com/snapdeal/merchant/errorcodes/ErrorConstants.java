package com.snapdeal.merchant.errorcodes;

public class ErrorConstants {

   public static final String TOKEN_IS_BLANK_CODE = "ER-0100";
   public static final String TOKEN_IS_BLANK_MSG = "Token is null or empty";

   public static final String SESSION_INVALID_CODE = "ER-0101";
   public static final String SESSION_INVALID_MSG = "Session Invalid ";

   public static final String GENERIC_INTERNAL_SERVER_CODE = "ER-500";
   public static final String GENERIC_INTERNAL_SERVER_MSG = "Internal server error";

   public static final String INTERNAL_CLIENT_CODE = "ER-400";
   public static final String INTERNAL_CLIENT_MSG = "Internal Client Exception";

   public static final String USER_PERMISSION_INVALID_CODE = "ER-0103";
   public static final String USER_PERMISSION_INVALID_MSG = "Not authorized to access the resource";

   public static final String USER_DOES_NOT_EXISTS_CODE = "ER-1001";
   public static final String USER_DOES_NOT_EXISTS_MSG = "User does not exists.";

   public static final String WRONG_PASSWORD_CODE = "ER-1002";
   public static final String WRONG_PASSWORD_MSG = "wrong password";

   public static final String EMAIL_IS_BLANK_CODE = "ER-1003";
   public static final String EMAIL_IS_BLANK_MSG = "Email can not be blank";

   public static final String EMAIL_FORMAT_INCORRECT_CODE = "ER-1004";
   public static final String EMAIL_FORMAT_INCORREC_MSG = "Please enter valid email address";

   public static final String PASSWORD_IS_BLANK_CODE = "ER-1006";
   public static final String PASSWORD_IS_BLANK_MSG = "Password can not be empty";

   public static final String INVALID_CHARACTER_PASSWORD_CODE = "ER-1008";
   public static final String INVALID_CHARACTER_PASSWORD_MSG = "Invalid character in password string";

   public static final String SOCIAL_TOKEN_IS_BLANK_CODE = "ER-1009";
   public static final String SOCIAL_TOKEN_IS_BLANK_MSG = "Social token can not be null or empty";

   public static final String SOCIAL_SOURCE_IS_BLANK_CODE = "ER-1010";
   public static final String SOCIAL_SOURCE_IS_BLANK_MSG = "Social source can not be null or empty";

   public static final String MERCHANTID_IS_BLANK_CODE = "ER-1011";
   public static final String MERCHANTID_IS_BLANK_MSG = "Merchant Id can not be null or empty";

   public static final String USER_ALREADY_EXISTS_CODE = "ER-1012";
   public static final String USER_ALREADY_EXISTS_MSG = "User already exists!";

   public static final String MERCHANT_ID_IN_REQUEST_INVALID_CODE = "ER-1013";
   public static final String MERCHANT_ID_IN_REQUEST_INVALID_MSG = "Invalid merchant in request";

   public static final String USERID_IS_BLANK_CODE = "ER-1014";
   public static final String USERID_IS_BLANK_MSG = "User id can not be null or empty";

   public static final String USERID_FORMAT_INCORRECT_CODE = "ER-1017";
   public static final String USERID_FORMAT_INCORRECT_MSG = "User id format is not correct";

   public static final String USERID_AND_PASSWORD_WRONG_CODE = "ER-1018";
   public static final String USERID_AND_PASSWORD_WRONG_MSG = "UserId or Password Wrong";

   public static final String PASSWORD_DOESNOT_MATCH_CODE = "ER-1019";
   public static final String PASSWORD_DOESNOT_MATCH_MSG = "Password doesnot match";

   public static final String USER_DOES_NOT_ASSOCIATED_WITH_MERCHANT_CODE = "ER-1020";
   public static final String USER_DOES_NOT_ASSOCIATED_WITH_MERCHANT_MSG = "User does not associated with this merchant.";

   public static final String ROLE_NAME_ALREADY_EXIST_CODE = "ER-1021";
   public static final String ROLE_NAME_ALREADY_EXIST_MSG = "Role Name already exists";

   public static final String USER_NAME_IS_BLANK_CODE = "ER-1023";
   public static final String USER_NAME_IS_BLANK_MSG = "UserName is Blank";

   public static final String LOGIN_NAME_IS_BLANK_CODE = "ER-1024";
   public static final String LOGIN_NAME_IS_BLANK_MSG = "Login Name is Blank";

   public static final String USER_ID_IS_BLANK_CODE = "ER-1025";
   public static final String USER_ID_IS_BLANK_MSG = "UserId cannot be Blank";
   
   public static final String ORDER_ID_IS_BLANK_CODE = "ER-1045";
   public static final String ORDER_ID_IS_BLANK_MSG = "Order Id cannot be Blank";
   
   public static final String TXN_ID_IS_BLANK_CODE = "ER-1026";
   public static final String TXN_ID_IS_BLANK_MSG = "Transaction Id cannot be Blank";
   
   public static final String AMOUNT_IS_BLANK_CODE = "ER-1027";
   public static final String AMOUNT_IS_BLANK_MSG = "Amount cannot be Blank";
   
   public static final String OFFLINE_REFUND_NOT_ALLOWED_CODE = "ER-1028";
   public static final String OFFLINE_REFUND_NOT_ALLOWED_MSG = "Offline Merchant refund not allowed here";
   
   public static final String ONLY_ONLINE_REFUND_ALLOWED_CODE = "ER-1029";
   public static final String ONLY_ONLINE_REFUND_ALLOWED_MSG = "Only online Merchant refund allowed here";
   
   public static final String NO_RECORD_TO_EXPORT_CODE = "ER-1030";
   public static final String NO_RECORD_TO_EXPORT_MSG = "No Record To Export";
   
   public static final String EXPORT_FILE_GEN_CODE = "ER-1031";
   public static final String EXPORT_FILE_GEN_MSG = "Could not generate file";
   
   public static final String INVALID_DOWNLOAD_CODE = "ER-1032";
   public static final String INVALID_DOWNLOAD_MSG = "Invalid file name in download request";
   
   public static final String INVALID_FILE_STATUS_CODE = "ER-1033";
   public static final String INVALID_FILE_STATUS_MSG = "Can not download,file status invalid";
   
   public static final String START_DATE_CANNOT_BE_NULL_CODE = "ER-1034";
   public static final String START_DATE_CANNOT_BE_NULL_MSG = "Please select start date";
   
   public static final String END_DATE_CANNOT_BE_NULL_CODE = "ER-1035";
   public static final String END_DATE_CANNOT_BE_NULL_MSG = "Please select end date";
   
   public static final String OTP_ID_CANNOT_BE_BLANK_CODE = "ER-1036";
   public static final String OTP_ID_CANNOT_BE_BLANK_MSG = "OTP Id cannot be Blank";
   
   public static final String OTP_CANNOT_BE_BLANK_CODE = "ER-1037";
   public static final String OTP_CANNOT_BE_BLANK_MSG = "OTP is Blank";
   
   public static final String SUCCESS_MSG_FOR_EXPORT_TXN = "Success! Visit Download History section to download file";
   
   public static final String SUCCESS_MSG_FOR_BULK_REFUND = "Success! Visit Refund section to download file";
   public static final String SUCCESS_MSG_FOR_FORGOT_PASSWORD = "Password reset successfully";
   
   public static final String NO_RECORD_PROVIDED_FOR_BULK_REFUND_CODE ="ER-1038";
   public static final String NO_RECORD_PROVIDED_FOR_BULK_REFUND_MSG ="No Record Provided For Bulk Refund";
   
   public static final String TXNID_SHOULD_BE_PROVIDED_CODE = "ER-1039";
   public static final String TXNID_SHOULD_BE_PROVIDED_MSG = "TxnId should be Provided";
   
   public static final String VALID_AMOUNT_SHOULD_BE_PROVIDED_CODE = "ER-1040";
   public static final String  VALID_AMOUNT_SHOULD_BE_PROVIDED_MSG = "valid Amount should be provided";
   
   public static final String COMMENTS_MUST_BE_PROVIDED_FOR_REFUND_CODE = "ER-1041";
   public static final String COMMENTS_MUST_BE_PROVIDED_FOR_REFUND_MSG = "Comments must be provided for refund";
   
   public static final String REFUND_INVALID_AMOUNT_MSG = "Invalid amount";
   
   public static final String REFUND_SUCCESS_MSG = "Refund Successfully";
   
   public static final String FAILED_TO_PROCESS_RECORD_MSG = "Failed To Process Refund ";
   
   public static final String ACTIVE_MERCHANT_PRODUCTION_KEY_MSG = "You are not authorized to see Production keys. Please contact our customer support";
   
   public static final String EXCEL_EXCEED_FILE_LIMIT_MSG="Excel File Exceed Uploaded Limit";
   public static final String EXCEL_EXCEED_FILE_LIMIT_CODE= "ER-1042";
   
   public static final String USER_IDENTIFIER_IS_BLANK_CODE = "ER-1043";
   public  static final String USER_IDENTIFIER_IS_BLANK_MSG = "User Identifier is Blank";
   
   public static final String INVALID_REQUEST_FOR_MERCHANT_CODE ="ER-1044";
   public static final String INVALID_REQUEST_FOR_MERCHANT_MSG ="Invalid Request from merchant";
   
   public static final String EMAIL_CONTENT_IS_BLANK_CODE = "ER-1045";
   public static final String EMAIL_CONTENT_IS_BLANK_MSG = "Email Content is blank";
   
   public static final String ISSUE_TYPE_IS_BLANK_CODE = "ER-1046";
   public static final String ISSUE_TYPE_IS_BLANK_MSG = "Issue type is blank";
   
   public static final String UNABLE_TO_RETRIEVE_INFO_MSG = "Unable to retrieve information, Please try after sometime ";

   public static final String EMAIL_SUCCESS_MSG = "The request has been successfully submitted, We will get in touch with you shortly";
   
   public static final String EMAIL_FAIL_CODE = "ER-1047";
   public static final String EMAIL_FAIL_MSG = "We have encountered an error while submitting your request. Please Try again";
   
   public static final String UNABLE_TO_UPLOAD_FILE_CODE ="ER-1048";
   public static final String UNABLE_TO_UPLOAD_FILE_MSG ="Fail to Upload File";
   
   public static final String UNABLE_TO_PROCESS_BULK_REFUND_REQUEST_CODE = "ER-1049";
   public static final String UNABLE_TO_PROCESS_BULK_REFUND_REQUEST_MSG = "Unable to  proceed your request.Please try after some time";


   public static final String UNABLE_TO_SUBMIT_BULK_REQUEST_CODE ="ER-1050";
   public static final String UNABLE_TO_SUBMIT_BULK_REQUEST_MSG ="Unable to submit your request. Please contact our customer support";
   
   public static final String GENERIC_ERROR_CODE_FOR_AGGREGATOR_EXCEPTION ="ER-1051";
   public static final String UNABLE_TO_PROCESS_REQUEST = "Unable to  proceed your request.Please try after some time";
   
   public static final String MOBILE_INVALID_CODE = "ER-1052";
   public static final String MOBILE_INVALID_MSG = "Mobile Number is Not Valid";
   
   public static final String MOBILE_IS_BLANK_CODE = "ER-1053";
   public static final String MOBILE_IS_BLANK_MSG="Mobile can not be blank";

   public static final String COMPANY_NAME_BLANK_CODE="ER-1054";
   public static final String COMPANY_NAME_BLANK_MSG="Company name Can not be blank.";
   
   public static final String CUSTOMER_NAME_BLANK_CODE = "ER-1055";
   public static final String CUSTOMER_NAME_BLANK_MSG="Name can not be blank.";
   
   public static final String BUSINESS_ADDRESS_BLANK_CODE = "ER-1056";
   public static final String BUSINESS_ADDRESS_BLANK_MSG = "Business Addesss can not Blank .";
   
   public static final String BUSINESS_CATEGORY_BLANK_CODE ="ER-1057";
   public static final String BUSINESS_CATEGORY_BLANK_MSG ="Buisness Category can not Blank";
   
   public static final String NO_OF_STORE_BLANK_CODE = "ER-1058";
   public static final String NO_OF_STORE_BLANK_MSG = "Number of Stores can not be Blank";
   
   public static final String DAILY_NO_OF_TXN_BLANK_CODE = "ER-1059";
   public static final String DAILY_NO_OF_TXN_BLANK_MSG = "Daily transaction Volume can not be Empty";
   
   public static final String LANDLINE_NUMBER_LENGTH_CODE = "ER-1060";
   public static final String LANDLINE_NUMBER_LENGTH_MSG = "Enter Landline Number should be between 6 to 8 lenght.";
   
   public static final String LANDLINE_INVALID_CODE = "ER-1061";
   public static final String LANDLINE_INVALID_MSG = "Enter Valid Landline Number.";
   
  public static final String SUPER_ADMIN_LOGIN_ERROR_CODE = "ER-1062";
  public static final String SUPER_ADMIN_LOGIN_ERROR_MSG = "Unable to process your request please try again after some time.";
  
  public static final String UNABLE_TO_PROCESS_REQUEST_CODE = "ER-1063";
  public static final String UNABLE_TO_PROCESS_REQUEST_MSG = "Unable to process your request.Please try again after some time";
  
  public static final String SUB_ORDINATE_LOGIN_ERROR_CODE = "ER-1064";
  public static final String SUB_ORDINATE_LOGIN_ERROR_MSG = "you can't login to Merchant Panel.";
  
  public static final String USER_TO_MERCHANT_MAPPING_ERROR_CODE="ER-1065";
  public static final String USER_TO_MERCHANT_MAPPING_ERROR_MSG="You are not associated to any Merchant.";
  
  public static final String AUTO_LOGIN_ERROR_CODE = "ER-1066";
  public static final String AUTO_LOGIN_ERROR_MSG = "Account Created. Internal Server Error. Please Login After SomeTime.";
}

