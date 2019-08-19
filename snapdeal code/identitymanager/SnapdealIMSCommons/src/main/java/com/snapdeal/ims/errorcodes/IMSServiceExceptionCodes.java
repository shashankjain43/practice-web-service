package com.snapdeal.ims.errorcodes;

public enum IMSServiceExceptionCodes {
	EMAIL_ALREADY_REGISTERED("ER-5101","This email is already registered with other user, please enter a different email address."),
	MOBILE_ALREADY_REGISTERED("ER-5102","Mobile number is already associated  and with other account "),
	ACCOUNT_ALREADY_EXIST("ER-5103","An account already exists with this username, Use 'Forgot Password' to login"),
	REGISTRATION_FAILED("ER-5104","The registration has failed. Please try again or contact customer support."),
	USER_NOT_EXIST("ER-5105","User does not exist"),
	INVALID_OTP_ENTERED("ER-5106","Please enter correct OTP, or use Resend OTP"),
	INVALID_PURPOSE("ER-5107","Purpose is invalid"),
	INVALID_OTP_ID("ER-5108","OTP ID is invalid"),
	OTP_LIMIT_BREACHED("ER-5109","You have exceeded the maximum limit of OTPs. Please try after {0} minutes."),
	OTP_ALREADY_USED("ER-5110","The entered Otp has already been used. Please generate a new Otp and try  again."),
	MOBILE_NOT_REGISTERED("ER-5111","This Mobile is not registered with us"),
	SMS_NOTIFICATION_ERROR("ER-5112","SMS notification error"),
	WRONG_SMS_CHANNEL_INFO("ER-5113","Wrong smsChannelInfo"),
	ERROR_SENDING_MAIL("ER-5114","Error occured while sending the mail"),

	ERROR_CONNECTING_MAIL_CLIENT("ER-5115","Error occured while connecting to snapdeal email client"),
	INVALID_PATH("ER-5116", "Unable to validate the state of Otp. Please try again."),

	CLIENT_ALREADY_EXISTS("ER-5117","Client already exists. Please try again with different client name and merchant"),
	PASSWORD_MISMATCH("ER-5118","The passwords don't match. Please enter again"),
	USER_ID_FORMAT_INCORRECT("ER-5119","User Id Format is incorrect"),
	FEATURE_NOT_SUPPORTED("ER-5120","This feature is not supported currently. Please try again."),
	CLIENT_NOT_EXIST("ER-5121","Client does not exist."),
	CONFIG_ALREADY_EXISTS("ER-5122","Config already exists, please try again with different configKey and configType"),
	CONFIG_NOT_EXISTS("ER-5123","Config does not exists, please try again with different configKey and configType"),
   USER_LOCKED("ER-5125", "Your account is temporarily locked, please try after {0} minutes"),
   USER_LAST_ATTEMPT("ER-5126", "Please enter correct password otherwise, your account will be temporarily locked"),
   USER_IS_LOCKED("ER-5127", "Your account is still locked, please try after {0} minutes"),	
	WRONG_PASSWORD("ER-5128", "Please enter correct old password"), 
	NEW_PASSWORD_CAN_NOT_BE_SAME_AS_OLD_PASSWORD("ER-5129", "New password can not be same as previous password"),
	CODE_IS_INVALID_OR_EXPIRED("ER-5130","Email Verification code is invalid or has expired"), 
	USER_ALREADY_VERIFIED("ER-5131","User Already Verified"),
   ERROR_SENDING_SMS("ER-5132","error occured while sending sms"),
	ERROR_ON_UPDATING_USER("ER-5132","error occured while updating user on fc side."),
	ERROR_ON_FORGOT_PASSWORD("ER-5133","error occured while calling forgotPassword apis on fc side."),
	INVALID_DATE_FORMAT_FC("ER-5134","format date should be of format dd-MM-YYYY"),
	ENCRYPT_KEY_MISMATCH("ER-5135","enryption key does not match with enryption key of forgotpassword"),
	INVALID_SOCIAL_SRC("ER-5136","social src is not valid"),
	WRONG_EMAIL_ID("ER-5137" ,"emailId is incorrect"),
   MOBILE_NUMBER_IS_BLANK("ER-5138","Please enter your mobile number"),
   VERIFY_OTP_LIMIT_BREACHED("ER-5139","Maximum limit of verification attempts reached. Please try again after {0} minutes"),  
   MISMATCH_EMAIL_ID_MOBILE("ER-5140","Email ID and mobile number does not belong to same account"),
   WRONG_MOBILE_NUMBER("ER-5141","mobile number is incorrect"),
	SOCIAL_SOURCE_NOT_FOUND("ER-5142","Please enter Social Source"),
	FEATURE_IS_SUPPORTED_SIGNUP("ER-5143","This api is not Supported for signup"), 
	INVALID_SOCIAL_ID("ER-5144", "Invalid social Id"),
   INVALID_IMS_TARGET_CONSUMER("ER-5145", "Invalid ims target consumer"),
	EMAIL_IS_ALREADY_UPGRRADED("ER-5146","Email is already upgraded"),
	INVALID_CALL("ER-5147","invalid call"),
	MOBILE_USER_NOT_VERIFIED("ER-5148","Email id corresponding to this mobile is not linked from {0}"),
   TRANSFER_TOKEN_EXPIRED("ER-5149", "Transfer token has expired"),
   USER_IS_DISABLED("ER-5150","user is disabled,can't perform this function"),
   BLACK_LISTED_EMAIL("ER-5151", "Email id black-listed, cannot create user."),
   INVALID_VERIFICATION_PURPOSE("ER-5152","Verification Purpose is invalid"),
   MISMATCH_EMAIL_VERIFICATION("ER-5153","Mismatch between verification code email and email in verfiylinkstate request"),
   EMAIL_SENDING_FAILED("ER-5154","There was a error in sending email, please try again"),
   LOGIN_VIA_DIFFERNT_EMAIL_ADDRESS("ER-5155","User loged in via differnt email address,please login with correct EmailID"), 
   ERROR_ON_UPDATING_USER_HISTORY("ER-5155","There was a error in updating user history"),
   EMPTY_SEARCH_FIELD("ER-5156","Empty search Field"),
   USER_HISTORY_EMPTY("ER-5157","User history is empty"), 
   MOBILE_NUMBER_MISMATCH("ER-5158","Mobile Number doesnot matches the otp sent"),
   MERCHANT_NOT_ENTERED("ER-5159","Merchant not entered"),
   UNABLE_TO_DECRYPT_CODE("ER-5160","Email Verification code is invalid"),
   USERID_NOT_ASSOCIATED_WITH_CODE("ER-5161","User is not associated with code"),
   ERROR_ON_GETTING_KEY_FROM_VAULT("ER-5162","Exception in while storing key in vault"),
   ERROR_IN_DECRYPTING_KEY("ER-5163","Key not Decrypted successfully"),
   ACCOUNT_ALREADY_EXISTS_MOBILEONLY("ER-5164","Your Mobile is already verified,Please login via OTP"),
   MERCHANT_MISMATCHED_FOR_ACCESS_TOKEN("ER-5165", "Merchant Id in request doesn't match with that of auth code"),
   AUTH_CODE_EXPIRED("ER-5166", "Auth Code has expired"),
   ACCESS_TOKEN_EXPIRED("ER-5167", "Access token has expired"),
   REFRESH_TOKEN_EXPIRED("ER-5168", "Refresh token has expired");
	
	
	private String errCode;
	private String errMsg;

	private IMSServiceExceptionCodes(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String errCode() {
		return this.errCode;
	}

	public String errMsg() {
		return this.errMsg;
	}

   public static IMSServiceExceptionCodes forName(String errorCode) {
      IMSServiceExceptionCodes errCode = null;
      for (IMSServiceExceptionCodes code : values()) {
         if (code.name().equals(errorCode)) {
            return code;
         }
      }
      return errCode;
   }
}
