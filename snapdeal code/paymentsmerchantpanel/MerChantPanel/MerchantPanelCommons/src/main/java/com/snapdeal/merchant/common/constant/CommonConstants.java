package com.snapdeal.merchant.common.constant;

public interface CommonConstants {

   public static final int MIN_PASSWORD_LENGTH = 6;
   public static final int MAX_PASSWORD_LENGTH = 127;
   public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$";
   public static final String PASSWORD_PATTERN = "^[A-Za-z0-9@%+'!~`#\\$?.\\^\\{\\}\\[\\]\\(\\)_\\-:, \\/\\\\]*$";
   public static final String SUBJECT_NEW_ACCOUNT_CREATED_MAIL = "Merchant Panel: New account created";
   public static final String TEMPLATE_KEY_NEW_ACCOUNT_CREATED_MAIL = "snapdeal.merchant.panel.new.account.welcome.mail";
   public static final String PASSWORD_TAG_NAME = "password";
   public static final String NOREPLY_ADDRESS = "noreply@freecharge.in";
   public static final String FROM_ADDRESS = "FreeCharge<noreply@freechargemail.in>";
   public static final String SUBJECT_LINK_FOR_FORGOT_PASSWORD = "Merchant Panel: link for reset password";
   public static final String TEMPLATE_KEY_FORGOT_PASSWORD_CREAYED_MAIL = "snapdeal.merchant.panel.forgot.password.link.mail";
   public static final String USERNAME_REGEX = "^[A-Za-z0-9]";

   public static final int DEFAULT_ERROR_STATUS_CODE = 500;
   public static final String DATE_FORMAT = "yyyy-MM-dd";
   public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
   public static final String OK = "OK";
}
