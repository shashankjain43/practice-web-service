package com.snapdeal.admin.constants;

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
	public static final String UPDATE_MOBILE_NUMBER = "/{userId}/mobile";

	public static final String SIGNIN = "/api/v1/identity/signin/users";
	public static final String SIGNIN_FB = "/api/v1/identity/users/fb";
	public static final String SIGNIN_GMAIL = "/api/v1/identity/users/google";
	public static final String SIGNIN_TOKEN = "/api/v1/identity/signin/token";
	public static final String SIGNOUT = "/api/v1/identity/signout";

	public static final String USER_ADDRESS = "/api/v1/identity/users/{userId}/address";
	public static final String GET_ALL_ADDRESS = "";
	public static final String UPDATE_ADDRESS = "/{addressId}";
	public static final String SET_DEFAULT_ADDRESS = "/{addressId}";
	public static final String GET_ADDRESS = "/{addressId}";
	public static final String DELETE_ADDRESS = "/{addressId}";
	public static final String DEFAULT_ADDRESS = "/{addressId}/default";

	public static final String SEND_OTP = "/otp/send";
	public static final String RESEND_OTP = "/otp/resend";

	public static final String UTILITY_URI = "/api/v1/identity/utility";
	public static final String CACHE_RELOAD = "/cache/reload";
	public static final String VERIFY_OTP = "/otp/verify";

	public static final String FORGOT_PASSWORD = "/forgotpassword/otp";
	public static final String CHANGE_PASSWORD = "/{userId}/changepassword";
	public static final String RESET_PASSWORD = "/forgotpassword/otp";

	public static final String APPLICATION_JSON = "application/json";

	public static final String WILD_CARD = "*/*";
	public static final String EMPTY = "";
	public static final Integer DEFAULT_SECURE_KEY_LEN = 12;
}
