package com.snapdeal.ims.constants;

public interface RestURIConstants {

	public static final String USER = "/api/v1/identity/users";
	public static final String REGISTER = "/register";
	// public static final String FB = "/fb";
	// public static final String GMAIL = "/google";
	public static final String SOCIAL = "/social";

	public static final String GET_USER = "/{userId}";
	public static final String GET_USER_TOKEN = "/token/{token}";
	public static final String GET_USER_EMAIL = "/email/{email}";
	public static final String GET_USER_MOBILE = "/mobile/{mobileNumber}";
	public static final String GET_USER_FB_SOCIALID = "/fb/socialid/{socialId}";
	public static final String GET_USER_GMAIL_SOCIALID = "/google/socialid/{socialId}";

	public static final String UPDATE_USER_TOKEN = "/token/{token}";
	public static final String UPDATE_USER = "/{userId}";
	public static final String UPDATE_MOBILE_NUMBER = "/mobile";
	public static final String UPGRADE_USER_BY_EMAIL="/upgrade/email";
	
	public static final String SIGNIN = "/api/v1/identity/signin/users";
	public static final String SIGNIN_FB = "/api/v1/identity/users/fb";
	public static final String SIGNIN_GMAIL = "/api/v1/identity/users/google";
	public static final String SIGNIN_TOKEN = "/api/v1/identity/signin/token";
	public static final String SIGNOUT = "/api/v1/identity/signout";
	public static final String FORGOT_PASSWORD = "/api/v1/identity/forgotpassword";
	public static final String FORGOT_PASSWORD_VERIFY = "/api/v1/identity/forgetpassword/verify";
	public static final String GET_TRANSFER_TOKEN = "/api/v1/identity/token/transfer";
	public static final String SIGNIN_TRANSFER_TOKEN = "/api/v1/identity/signin/token/transfer";

	public static final String USER_ADDRESS = "/api/v1/identity/users/{userId}/address";
	public static final String GET_ALL_ADDRESS = "";
	public static final String UPDATE_ADDRESS = "/{addressId}";
	public static final String SET_DEFAULT_ADDRESS = "/{addressId}";
	public static final String GET_ADDRESS = "/{addressId}";
	public static final String DELETE_ADDRESS = "/{addressId}";
	public static final String DEFAULT_ADDRESS = "/{addressId}/default";

	public static final String OTP = "/api/v1/identity/";
	public static final String SEND_OTP = "otp/send";
	public static final String RESEND_OTP = "otp/resend";
	public static final String GENERATE_OTP = "otp/send";
	public static final String VALID_OTP = "otp/valid/{otpId}/{otp}";
	public static final String MOBILE_VERIFY = "/mobile/verify";
	public static final String VERIFY_OTP = "otp/verify";
	public static final String MOBILE_VERIFICATION_STATUS = "users/{userId}/mobile/verify";

	public static final String UTILITY_URI = "/api/v1/identity/utility";
	public static final String CACHE_RELOAD = "/cache/reload";

	public static final String FETCH_CONFIGURATION_DETAILS = "/configurationDetails/{configuration}";

	public static final String IS_USER_EXIST = "/exists/user/{userId}";
	public static final String IS_EMAIL_EXIST = "/exists/email/{emailId}";
	public static final String IS_MOBILE_EXIST = "/exists/mobile/{mobileNumber}";

	public static final String FORGOT_PASSWORD_OTP = "/forgotpassword/otp";
	public static final String CHANGE_PASSWORD = "/changepassword";
	public static final String CHANGE_PASSWORD_WITH_LOGIN = "/changepasswordwithlogin";
	public static final String RESET_PASSWORD = "/forgotpassword/otp";
	public static final String RESET_PASSWORD_AND_LOGIN = "/forgotpassword/otpandlogin";

	public static final String APPLICATION_JSON = "application/json";

	public static final String WILD_CARD = "*/*";
	public static final String EMPTY = "";
	public static final Integer DEFAULT_SECURE_KEY_LEN = 12;

	public static final String ACTIVATE_STATE = "/enable";

	public static final String RESEND_VERIFY_EMAIL = "/resend/verify/email";
	public static final String CREATE_SOCIAL_USER_WITH_MOBILE = "/social/mobile";
	public static final String VERIFY_SOCIAL_USER_WITH_MOBILE = "/social/mobile/verify";
	public static final String WHITELIST_EMAIL = "/whitelist/email";
	public static final String BLACKLIST_ENTITY = "/blacklist/entity";
	public static final String BLACKLIST_ENTITY_REMOVE = "/blacklist/entity/{entityType}/{entity}";
	
	public static final String VALIDATE_TOKEN = "/validate/token/{token}" ;
	public static final String GET_IMS_USER_VERIFICATION_URL = "/verification/url";
	
	
	public static final String GET_TOKEN_SIZE = "/token/email/{email}";
	public static final String GET_BLACKLIST = "/blacklist/email"; 
	public static final String GET_DISCREPENCY_COUNT = "/discrepencycount";
	public static final String GET_DISCREPENCY_LIST = "/discrepencycount/list";
	public static final String GET_OTP_DETAILS = "/otpDetails";
	public static final String USER_SEARCH = "/search";
	public static final String GET_WALLET_COUNT = "/upgradeCount";
	public static final String GET_FAILED_EMAIL_LIST = "/upgradeCount/emailList/{merchant}";
	public static final String GET_STATUS = "/checkDetails/{emailId}";
	public static final String GET_USER_HISTORY="/historyDetails";
	
	
	public static final String VERIFY_MOBILE_ONLY="/mobileOnly/verify";
	public static final String GENERATE_OTP_MOBILE_ONLY = "/mobileOnly/generate";
	public static final String IS_MOBILE_ONLY = "/exists/mobileOnly/{mobileNumber}";
			
	
}
