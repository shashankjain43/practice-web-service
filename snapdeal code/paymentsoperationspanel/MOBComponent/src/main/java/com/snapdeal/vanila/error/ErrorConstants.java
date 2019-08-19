package com.snapdeal.vanila.error;

public class ErrorConstants {

	   public static final String TOKEN_IS_BLANK_CODE = "ER-0100";
	   public static final String TOKEN_IS_BLANK_MSG = "Token is null or empty";

	   public static final String SESSION_INVALID_CODE = "ER-0101";
	   public static final String SESSION_INVALID_MSG = "Session Invalid ";

	   public static final String GENERIC_INTERNAL_SERVER_CODE = "ER-500";
	   public static final String GENERIC_INTERNAL_SERVER_MSG = "Internal server error";

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
	   
	   public static final String FAILED_TO_PROCESS_RECORD_MSG = "Failed To Process Refund , Please Try it after Sometime";
	   
	   public static final String ACTIVE_MERCHANT_PRODUCTION_KEY_MSG = "you are not authorized to see Production keys. Please contact our customer support";
	   
	   
	   

	}
