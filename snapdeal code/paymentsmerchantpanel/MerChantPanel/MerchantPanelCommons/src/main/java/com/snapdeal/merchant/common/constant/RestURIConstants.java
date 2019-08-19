package com.snapdeal.merchant.common.constant;

public interface RestURIConstants {

   public static final String BASE_URI = "/api/v1";
   public static final String MERCHANT = "/merchant";
   public static final String GET_PROFILE = "/get/profile";
   public static final String GET_ROLES = "/get/roles";
   public static final String SESSION = "/session";
   public static final String LOGIN = "/login";
   public static final String LOGOUT = "/logout";
   public static final String USER = "/user";
   public static final String ADD_USER = "/adduser";
   public static final String EDIT_USER = "/edituser";
   public static final String CHANGE_PASSWORD = "/updatepwd";
   public static final String FORGOT_PASSWORD = "/genpwd";
   public static final String GET_ALL_USERS = "/getall";
   public static final String VERIFY_USER = "/verify/{loginName}";
   public static final String GET_LOGIN_NAME_PARAM = "{loginName}";
   public static final String VERIFY = "/verify/";
   public static final String TRANSACTION = "/txn";
   public static final String VIEW_TRANSACTION = "/view";
   public static final String SEARCH_TRANSACTION = "/search";

   public static final String APPLICATION_JSON = "application/json";
}
