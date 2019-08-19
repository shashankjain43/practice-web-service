package com.snapdeal.ims.constants;

public interface ServiceCommonConstants {

   public static final String SUCCESS = "SUCCESS";
   public static final String VersionOne = "V01";

   // Token separator used while creating token.
   public static final String TOKEN_DELIM = "#";

    // FreeCharge name delimiter, FC has only one name field.
    // In IMS, we have firstName, middleName and lastName. This information is
    // stored in FC side using delimiter.
   public static final String FC_NAME_DELIM = " ";

   public static final String GLOBAL_CONFIG_TYPE = "global";

   public static final String REGISTERED = "registered";

   public static final String UNVERIFIED = "unverified";

   // ACTIVE means user is not locked and
   // INACTIVE means user is locked
   public static final String USER_LOCKED = "LOCKED";
   public static final String USER_UNLOCKED = "UNLOCKED";
   
   public static final String URL_TAG = "url";
   public static final String EMAIL_TAG = "email";
   public static final String MOBILE_TAG = "mobile";
   public static final String MERCHANT_TAG = "merchant";
   
   public static final String CUSTOMER_TAG = "custName";
   
   public static final String USERID = "userId" ;
   public static final String NAME = "name" ;
   //public static final String CREATE_USER_VERIFICATION_EMAIL_SUBJECT = "Welcome to Snapdeal";
   public static final String CREATE_USER_VERIFICATION_EMAIL_TEMPLATE = "src/main/resources/conf/HTML.vm";
   public static final String CREATE_USER_VERIFICATION_EMAIL_TASK_TYPE = "VerificationEmailTask";
   //public static final String REPLY_TO = "noreply@snapdeals.co.in";
   //public static final String FROM = "Snapdeal<noreply@snapdeals.co.in>";
   public static final String PASSWORD_TAG = "passwd";
   public static final String DEFAULT_SOCIAL_USER_PASSWORD = "DEFAULT_SOCIAL_USER_PASSWORD";
   public static final String DEFAULT_MOBILE_ONLY_PASSWORD = "DEFAULT_MOBILE_ONLY_PASSWORD";
   
   public static final int IMS_USERID_AUTO_INCREMENT_START_VALUE = 130000000;
   public static final long DEFAULT_ON_FAILURE_TASK_REEXECUTION_WAIT_TIME = 150; //150ms
   public static final String CREATE_SDWALLET_TASK_TYPE = "IMSCreateSDWallet";
   public static final String NOTIFY_MIGRATION_STATE_CHANGE = "IMSSMigrationStateChangeNotification";
   public static final String CREATE_COMPLETE_TASK_TYPE = "IMSCOMPLETETASK";
   public static final long DEFAULT_CREATE_COMPLETE_TASK_TYPE = 20;

   public static final long DEFAULT_CREATE_SDWALLET_RETRY_LIMIT = 14;
   
   public static final String CREATE_SNS_TASK_TYPE = "IMSSNSService";
   public static final long DEFAULT_CREATE_SNS_TASK_RETRY_LIMIT = 11;
   public static final long DEFAULT_ON_SNS_SERVICE_FAILURE_TASK_REEXECUTION_WAIT_TIME = 5 * 60 * 1000; // 5mins
   public static final int SNS_SERVICE_EXPONENTIAL_BASE = 3;
   
   public static final String CREATE_FORTKNOX_MERGE = "IMSFortKnoxMerge";
   public static final long DEFAULT_FORTKNOX_MERGE_RETRY_LIMIT = 14;
   public static final long FORTKNOX_FAILURE_TASK_REEXECUTION_WAIT_TIME = 150; //150 ms  
   public static final String FORTKNOX_SUCCESS_STATUS = "0";
   public static final int WALLET_EXPONENTIAL_BASE = 3;
   public static final int FORTKNOX_FAILURE_TASK_EXPONENTIAL_BASE =3;
   
   public static final String CREATE_NOTIFIER_EMAIL_SENDER_MERGE = "EMAIL_SENDER";
   public static final long DEFAULT_NOTIFIER_EMAIL_SENDER_RETRY_LIMIT = 11;
   public static final long NOTIFIER_EMAIL_SENDER_FAILURE_TASK_REEXECUTION_WAIT_TIME = 150; //150 ms  
   public static final String NOTIFIER_EMAIL_SENDER_SUCCESS_STATUS = "0";
   public static final int NOTIFIER_EMAIL_SENDER_EXPONENTIAL_BASE = 3;
   public static final int NOTIFIER_EMAIL_SENDER_FAILURE_TASK_EXPONENTIAL_BASE =3;
   public static final String CREATE_MOJOCLIENT = "MOJOCLIENT";
   public static final long DEFAULT_MOJOCLIENT_RETRY_LIMIT = 14;
   public static final long MOJOCLIENT_FAILURE_TASK_REEXECUTION_WAIT_TIME = 150; //150 ms  
   public static final String MOJOCLIENT_SUCCESS_STATUS = "0";
   public static final int MOJOCLIENT_FAILURE_TASK_EXPONENTIAL_BASE =3;
   
   public static final String HTTP_REQUEST_URI_PATTERN_KEY = "org.springframework.web.servlet.HandlerMapping.bestMatchingPattern";
   public static final String FREECHARGE = "FREECHARGE";
   public static final String SNAPDEAL = "SNAPDEAL";
   public static final String RESPONSE200 = "HTTP 200 - OK";
   
   public static final String EMAIL_LOGGER_NAME = "email_log";
   
   public static final String MOBILE_ONLY  = "mobile_only";
}
