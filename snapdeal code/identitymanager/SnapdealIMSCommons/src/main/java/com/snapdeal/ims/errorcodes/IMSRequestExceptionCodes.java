package com.snapdeal.ims.errorcodes;

public enum IMSRequestExceptionCodes {

	PASSWORD_MUST_CONTAIN_SIX_LETTER("ER-4101","Password length must be minimum 6 characters"), 
	INVALID_CHARACTER_PASSWORD("ER-4102", "Password entered is not valid, avoid using &*|;\"<>,="),
	
	EMAIL_FORMAT_INCORRECT("ER-4103","Please enter a valid email address"),
	INVALID_MOBILE_NUMBER("ER-4104","Please enter a valid mobile number"),
	NAME_INVALID_CHARACTERS("ER-4105","Please enter a valid value for name."),
//	MAX_CHARACTER_LENGTH(,),
	INVALID_GENDER("ER-4106","Please provide a valid gender."),
	INVALID_DOB("ER-4107","Please provide a valid date of birth in format yyyy-mm-dd"),
	INVALID_SOCIAL_SOURCE("ER-4108","Social source is invalid"),
	MOBILE_AND_EMAIL_BOTH_EMPTY("ER-4109","Either MobileNumer  OR EmailId should be present in request."),
	TOKEN_IS_BLANK("ER-4110","Token is blank"),
	PASSWORD_IS_BLANK("ER-4111","Please enter your password"),
	SOCIAL_SOURCE_IS_BLANK("ER-4112","Social source is blank"),
	OTP_IS_BLANK("ER-4113","Please enter your OTP"),
	OTP_ID_IS_BLANK("ER-4114","OTP ID is blank"),
	USER_ID_IS_BLANK("ER-4115","User ID is blank"),
	PURPOSE_IS_BLANK("ER-4116","Purpose is blank"),
	SOCIAL_ID_IS_BLANK("ER-4117","Social ID is blank"),
	GLOBAL_TOKEN_IS_BLANK("ER-4118","Global Token is blank"),
	VERIFIED_TYPE_IS_BLANK("ER-4119","Verified type is blank"),
	CLIENT_ID_IS_BLANK("ER-4120","Client ID is blank"),
	INVALID_PURPOSE("ER-4121","Purpose is invalid"),
	MOBILE_NUMBER_IS_BLANK("ER-4122","Please enter your mobile number"),
	INVALID_OTP_ID("ER-4123","OTP ID is invalid"),
	INVALID_OTP("ER-4124","Please enter correct OTP, or use Resend OTP"),	
	CONFIG_KEY_BLANK("ER-4125","Config key must not be blank"),
	CONFIG_TYPE_BLANK("ER-4126","Config Type must not be blank"),
	CONFIG_VALUE_BLANK("ER-4127","Config Value must not be blank"),
	CONFIG_KEY_MUST_CONTAIN_ONE_LETTER("ER-4128","Config KEY must have atleast one character"),
	EMAIL_ID_IS_BLANK("ER-4129","Please enter your email address"),
	EMAIL_MAX_LENGTH("ER-4130","Email Id should not exceed 127 characters."),
	CODE_MAX_LENGTH("ER-4131","Code length exceeded permissable limit"),
	CODE_IS_BLANK("ER-4132","Code is blank"),
	SOCIAL_SRC_MAX_LENGTH("ER-4133","Social src length exceeded permissable limit"),
	ABOUT_ME_MAX_LENGTH("ER-4134","About me Exceed its permissible limit of 1024 character."),
	CLIENT_ID_MAX_LENGTH("ER-4135","Client Id length exceeded permissable limit"),
	PURPOSE_MAX_LENGTH("ER-4136","Purpose length exceeded permissable limit"),
	// FIXME: Need to get proper error message for following error code.
	OLD_PASSWORD_IS_BLANK("ER-4137","Please enter your old password"),
	NEW_PASSWORD_IS_BLANK("ER-4138","Please enter your new password"),
	EMAIL_IS_BLANK("ER-4139","Please enter email"),
	SOCIAL_TOKEN_IS_BLANK("ER-4140","Social Token is blank"),
	SOCIAL_SECRET_IS_BLANK("ER-4136","Social secret is blank"),
	SOCIAL_EXPIRY_IS_BLANK("ER-4142","Social expiry is blank"),
	HASH_IS_BLANK("ER-4143","Hash is blank"),
	MACHINE_IDENTIFIER_IS_BLANK("ER-4144","Machine Identifier is blank"),
	GUEST_USER_PURPOSE_IS_BLANK("ER-4145","Guest user purpose is blank"),
	NAME_MAX_LENGTH("ER-4146","Name should not exceed 50 characters"),
	OTP_ID_MAX_LENGTH("ER-4147","OTP ID exceeded its permissible limit of 127 characters"),
	OTP_MAX_LENGTH("ER-4148","OTP length must be between 4 and 6 characters"),
	USER_AGENT_MAX_LENGTH("ER-4149","User agent exceeded its permissible limit of 127 characters"),
	MACHINE_IDENTIFIER_MAX_LENGTH("ER-4150","Machine Identifier exceeded its permissible limit of 127 characters"),
	SOCIAL_ID_MAX_LENGTH("ER-4151", "Social ID exceeded its permissible limit of 127 characters"),
	SOCIAL_TOKEN_MAX_LENGTH("ER-4152","Social Token exceeded its permissible limit of 512 characters"),
	SOCIAL_SECRET_MAX_LENGTH("ER-4153","Social secret exceeded its permissible limit of 127 characters"),
	SOCIAL_EXPIRY_MAX_LENGTH("ER-4154","Social expiry exceeded its permissible limit of 20 characters"),
	PHOTO_URL_MAX_LENGTH("ER-4155","Photo URL exceeded its permissible limit of 1024 characters"),
	TOKEN_MAX_LENGTH("ER-4156" , "Token exceeded its permissible limit of 127 characters"),
	GLOBAL_TOKEN_MAX_LENGTH("ER-4157","Global token exceeded its permissible limit of 127 characters"),
	USER_ID_MAX_LENGTH("ER-4158" , "User ID exceeded its permissible limit of 127 characters"),
	GUEST_USER_PURPOSE_MAX_LENGTH("ER-4159","Guest user purpose exceeded its permissible limit of {0} character"),
	DISPLAY_NAME_MAX_LENGTH("ER-4160","Display name should not exceed 50 characters"),
	DOB_MAX_LENGTH("ER-4161","DOB should not exceed 10 characters"),
	LANGUAGE_PREF_MAX_LENGTH("ER-4162","Language preference should not exceed 127 characters"),
	MIDDLE_NAME_MAX_LENGTH("ER-4163","Middle name should not exceed 50 characters"),
	LAST_NAME_MAX_LENGTH("ER-4164","Last name should not exceed 50 characters"),
	CONFIG_TYPE_MUST_CONTAIN_ONE_LETTER("ER-4165", "Config TYPE must have atleast one character"),
	CONFIG_VALUE_MUST_CONTAIN_ONE_LETTER("ER-4166", "Config VALUE must have atleast one character"),
	MACHINE_IDENTIFIER_OR_USER_AGENT_IS_BLANK("ER-4167", "Machine identifier and user agent are mandatory."),
	UNDER_AGED("ER-4168","User should have more than 18yrs of age."),
	DESCRIPTION_IS_BLANK("ER-4169","description can not be blank for this type of reason"),
	TOKEN_USERID_EMAIL_MOBILE_ALL_EMPTY("ER-4170","one of the parameters from token ,userid,email or moobile is necessary"),
	EMAIL_INVALID("ER-4171","please provide email in format abc@xyz.pqr"),
	EMAIL_NOT_PRESENT("ER-4172","Email Id is not registered with us. Please register"),
	BLANK_PARAMS("ER-4173","mandatory parameters are null"),
	SOCIAL_ID_INVALID("ER-4174","please enter a valid social id"),
	SOCIAL_TOKEN_INVALID("ER-4175","social token in invalid"),
	SOCIAL_SECRET_INVALID("ER-4176","social secret is invalid"),
	SOCIAL_EXPIRY_INVALID("ER-4177","social expiry is invalid"),
	ENCRYPT_KEY_EMPTY("ER-4178","enryption key is blank"),
	UNKNOWN_ERROR_ON_FC("ER-4179","there is some unknown error occured on fc side"),
	MERCHANT_EMPTY("ER-4180","Merchant is blank"), 
	EITHER_EMAIL_OR_TOKEN_MANDATORY("ER-4181","Either email or token is mandatory"),
	UPGRADE_SOURCE_MANDATORY("ER-4182","Upgrade source is mandatory"),
	UPGRADE_CHANNEL_MANDATORY("ER-4183","Upgrade channel is mandatory"),
	
	ONLY_EMAIL_ID_OR_MOBILE_NUMBER_REQUIRED("ER-4184","Please provide only email id or mobile number"),
	INVALID_UPGRADE_SOURCE_SIGN_UP("ER-4185","Upgrade cannot be called on signup directly."),
	
	TARGET_IMS_CONSUMER_BLANK("ER-4195", "Target ims consumer is blank"),
	
	CONFIGURATION_FIELD_MANDATORY("ER-4251","Please provide the configuration field"),
	INVALID_CONFIGURATION_FIELD("ER-4252","Please provide valid configuration field"),
	
	INVALID_CONFIGURE_USER_STATE_BASED_ON("ER-4253","Invalid Configure state based on flag."),
	INVALID_USER_ID("ER-4254","please enter a valid userId"),
	
	BLACKLIST_ENTITY_TYPE_IS_BLANK("ER-4255","Please enter blacklist entity type"),
	BLACKLIST_ENTITY_IS_BLANK("ER-4256","Please enter blacklist entity"),
	
	DCASE_IS_BLANK("ER-4257","discrepency case is blank"), 
	DCASE_IS_INVALID("ER-4257","discrepency case is invalid"), 
	INVALID_VERIFICATION_PURPOSE("ER-4258","Verification Purpose is invalid"),	
	EMPTY_SEARCH_FILTER("ER-4259","Empty User Search Filter"),
	USER_ID_IS_NULL("ER-4260","User id is null"),
	INVALID_SEARCHFIELD("ER-4261","Search Field is Empty"),
	SEARCHFIELD_IS_INVALID("ER-4262","Please enter a valid search field"),
	OTP_NOT_FOUND("ER-4263","Otp Details not found"),
	INVALID_DATE_FORMAT("ER-4264","Invalid Date Format-Please provide a valid date in format yyyy-mm-dd"),
	INVALID_TIMEINTERVAL("ER-4265","Invalid TimeInterval-enter start and end date in max interval of three days" ),
	
	//special case token validation
	INVALID_TOKEN("ER-2104", "Token is invalid"),	
	
	
	INVALID_TO_DATE_OR_FROM_DATE("ER-4266","Please provide a valid date in format yyyy-mm-dd "
			+ "such that startDate is less than endDate"),
	INVALID_TO_DATE("ER-4267","Please provide a valid date in format yyyy-mm-dd"
			+ "such that startDate and endDate are not greater than currentDate"),
	DATE_IS_BLANK("ER-4268","date for search is blank"),
	WALLET_BALANCE_EXISTS("ER-4269","Wallet not Empty-user cant be suspended"),
	SUSPEND_FAIL("ER-4270","SDMoneyAccount suspension failed.Please Try Again"),
	RESOURCE_MAX_LENGTH("ER-4271","Resource should not exceed 128 characters."),
	RESOURCE_IS_NULL("ER-4272","Resource is null"),
	AUTH_CODE_MAX_LENGTH("ER-6000","Auth Code should not exceed 255 characters"),
	AUTH_CODE_NOT_BLANK("ER-6001","Please enter a valid Auth Code"),
	MERCHANT_MAX_LENGTH("ER-6002","Merchant ID should not exceed 255 characters "),
	MERCHANT_NOT_BLANK("ER-6003","Please enter your merchant ID");
	
	private String errCode;
	private String errMsg;

	private IMSRequestExceptionCodes(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String errCode() {
		return this.errCode;
	}

	public String errMsg() {
		return this.errMsg;
	}

   public static IMSRequestExceptionCodes forName(String errorCode) {
      IMSRequestExceptionCodes errCode = null;
      for (IMSRequestExceptionCodes code : values()) {
         if (code.name().equals(errorCode)) {
            return code;
         }
      }
      return errCode;
   }
}
