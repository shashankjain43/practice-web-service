package com.snapdeal.ims.common.constant;

public interface RestURIConstants {
	public static final String CLIENT = "/api/v1/identity/client";
	public static final String CREATE_CLIENT = "/createClient";
	public static final String GET_CLIENT_BY_ID = "/getClientById";
	public static final String GET_CLIENT_BY_NAME = "/getClientByName";
	public static final String GET_CLIENT_BY_STATUS = "/getClientByStatus";
	public static final String GET_CLIENT_BY_TYPE = "/getClientByType";
	public static final String GET_CLIENT_BY_CLIENT = "/getClientByClient";

	public static final String BASE_URI = "/api/v1/identity";

	public static final String CREATE_EMAIL_MOBILE = "/users";
	public static final String CREATE_USERS_EMAIL = "/users/email";
	public static final String CREATE_USERS_EMAIL_MOBILE = "/users/email/mobile";
	public static final String CREATE_USERS_MOBILE_GENERATE = "/users/mobile/generate";
	public static final String CREATE_USERS_MOBILE_VERIFY = "/users/mobile/verify";
	public static final String CREATE_GUEST_USERS = "/users/guest/create";
	public static final String CREATE_GUEST_USERS_VERIFY = "/users/guest/verify";

	public static final String CREATE_SIGN_IN_FB = "/users/fb";
	public static final String CREATE_SIGN_IN_GMAIL = "/users/google";
	public static final String CREATE_SIGN_IN_SOCIAL = "/users/social";

	public static final String USERS = "/users";

	public static final String GET_USER_EMAIL = "/users/email";
	public static final String GET_USER_MOBILE = "/users/mobile";
	public static final String GET_USER_FB_SOCIALID = "/users/fb/socialid";
	public static final String GET_USER_GMAIL_SOCIALID = "/users/google/socialid";

	public static final String FORGOT_PASSWORD = "/forgotpassword";
	public static final String FORGOT_PASSWORD_VERIFY = "/forgetpassword/verify";

	public static final String GET_UPDATE_USER_TOKEN = "/users/token";

	public static final String IS_USER_EXIST = "/users/exists/user/";
	public static final String IS_EMAIL_EXIST = "/users/exists/email/";
	public static final String IS_MOBILE_EXIST = "/users/exists/mobile/";

	public static final String SIGN_IN_EMAIL_MOBILE = "/signin/users";
	public static final String SIGN_IN_TOKEN = "/signin/token";
	public static final String SIGN_IN_TRANSFER_TOKEN = "/signin/token/transfer";
	public static final String SIGN_OUT = "/signout";

	public static final String UPDATE_MOBILE = "/mobile";

	public static final String FORGOT_PASSWORD_GENEREATE_VERIFY_OTP = "/users/forgotpassword/otp";
	public static final String FORGOT_PASSWORD_GENEREATE_VERIFY_OTP_AND_LOGIN = "/users/forgotpassword/otpandlogin";
	public static final String CHANGE_PASSWORD = "/changepassword";
	public static final String CHANGE_PASSWORD_WITH_LOGIN = "/changepasswordwithlogin";

	public static final String SEND_OTP = "/otp/send";
	public static final String RESEND_OTP = "/otp/resend";
	public static final String VALID_OTP = "/otp/valid";
	public static final String MOBILE_VERIFY = "/mobile/verify";
	public static final String VERIFY_OTP = "/otp/verify";
	public static final String MOBILE_VERIFICATION_STATUS = "/mobile/verify";
	
	public static final String GET_CONFIGURATION_DETAILS = "/configurationDetails";

	public static final String UPGRADE_USER = "/users/upgrade";
	public static final String IS_USER_UPGRADE = "/users/upgrade/status";
	public static final String VERIFY_UPGRADE_USER = "/users/upgrade/verify";
	public static final String VERIFY_UPGRADE_LINKED_USER = "/users/upgrade/linkedverify";

	public static final String APPLICATION_JSON = "application/json";

	public static final String WILD_CARD = "*/*";

	public static final String ACTIVATE_STATE = "/enable";
	public static final String EMPTY = "";
	public static final String RESEND_VERIFY_EMAIL = "/resend/verify/email";
	public static final String CREATE_SOCIAL_USER_WITH_MOBILE = "/social/mobile";

	public static final String VERIFY_SOCIAL_USER_WITH_MOBILE = "/social/mobile/verify";
	public static final String GET_TRANSFER_TOKEN = "/token/transfer";
	public static final String WHITELIST_EMAIL = "/whitelist/email";
	public static final String BLACKLIST_ENTITY = "/blacklist/entity";
	public static final String BLACKLIST_ENTITY_REMOVE = "/blacklist/entity/{entityType}/{entity}";
	
	public static final String VALIDATE_TOKEN = "/validate/token" ;
	public static final String GET_IMS_USER_VERIFICATION_URL = "/verification/url";
	
	public static final String GET_TOKEN_SIZE = "/users/token/email/{email}";
	public static final String GET_BLACKLIST = "/users/blacklist/email"; 
	public static final String GET_DISCREPENCY_COUNT = "/users/discrepencycount";
	public static final String GET_DISCREPENCY_LIST = "/users/discrepencycount/list";
	public static final String GET_OTP_DETAILS = "/users/otpDetails";
	public static final String USER_SEARCH = "/users/search";
	public static final String GET_WALLET_COUNT = "/users/upgradeCount";
	public static final String GET_FAILED_EMAIL_LIST = "/users/upgradeCount/failedEmailList";
	public static final String GET_STATUS = "/users/checkDetails/{emailId}";
	public static final String GET_USER_HISTORY="/users/historyDetails";
	public static final String UPGRADE_USERS_EMAIL="/users/upgrade/email";
	
	public static final String GET_AUTH_CODE="/oauth/authcode";
	public static final String GET_ACCESS_TOKEN="/oauth/accesstoken";
	public static final String GET_OAUTH_TOKEN_DETAILS = "/oauth/gettokendetails";
}
