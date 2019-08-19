package com.snapdeal.ims.common;

import lombok.Getter;

import com.snapdeal.ims.enums.EnvironmentEnum;

@Getter
public enum ConfigurationConstants {

	// Default environment is production.	
		ENVIRONMENT("environment", EnvironmentEnum.PRODUCTION.name()),
		DEFAULT_TOKEN_GENERATION_SERVICE_VERSION("default.tokengeneration.service.version","V01"),
		TOKEN_GENERATION_SERVICE("default.tokengeneration.service.", null),
		DEFAULT_GLOBAL_TOKEN_GENERATION_SERVICE_VERSION("default.globaltokengeneration.service.version","V01"),
		GLOBAL_TOKEN_GENERATION_SERVICE("default.globaltokengeneration.service.", null),
		DEFAULT_GLOBAL_TOKEN_EXPIRY_TIME("default.global.token.expiry.time","30"),
		DAYS_TO_RENEW_TOKEN("days.to.renew.token", null),
		CIPHER_UNIQUE_KEY("cipher.unique.key","U25hcGRlYWxVbmlxdWVLZXk="),

		//Transfer Token Configuration
		DEFAULT_TRANSFER_TOKEN_GENERATION_SERVICE_VERSION("default.transfertokengeneration.service.version","V02"),
		TRANSFER_TOKEN_GENERATION_SERVICE("default.transfertokengeneration.service.",null),
		TRANSFER_TOKEN_EXPIRY_TIME("transfer.token.expiry.time.in.seconds","5"),


		// Client specific configuration.
		GLOBAL_TOKEN_EXPIRY_TIME("global.token.expiry.time", null),
		MAXIMUM_NUMBER_OF_LOGIN_ATTEMPTS("maximum.number.of.login.attempts", "5"),
		USER_LOCKED_FOR_TIME("user.locked.for.time", "900000"),
		USER_LOCKING_WINDOW_FRAME("user.locking.window.frame", "600000"),
		REQUEST_TTL("request.expiry.time", "600"),
		CREATE_USER_BY_EMAIL_VERIFICATION_URL("create.user.email.verification.url", null),
		GUEST_CREATE_USER_BY_EMAIL_VERIFICATION_URL("guest.email.verification.url", null),
		FORGET_PASSWORD_BY_EMAIL_VERIFICATION_URL("forget.password.email.verification.url", null),
		VERIFICATION_CODE_EXPIRY_NEW_USER("verification.code.expiry.in.minutes.new.user", "4320"),
		VERIFICATION_CODE_EXPIRY_FORGOT_PASSWORD("verification.code.expiry.in.minutes.forgot.password", "4320"),
		VERIFICATION_CODE_EXPIRY_UPGRADE_FLOW("verification.code.expiry.upgrade.flow","14400"),
		DEFAULT_VERIFICATION_CODE_EXPIRY("default.verification.code.expiry.in.minutes", "4320"),
		//Aerospike Properties
		AEROSPIKE_CLUSTER("aerospike.cluster",null),

		//Aerospike Client properties
		SOCKET_IDLE("aerospike.maxSocketIdle",null),
		MAX_THREADS("aerospike.maxThreads","300"),
		SHARED_THREADS("aerospike.sharedThreadPool","false"),
		CONNECTION_TIMEOUT("aerospike.connection.timeout","60000"),

		//aerospike read policy
		DEFAULT_MAX_READ_RETRIES("aerospike.default.read.maxRetries","2"),
		DEFAULT_SLEEP_BETWEEN_READ_RETRIES("aerospike.default.read.sleepBetweenRetries","3"),
		DEFAULT_READ_TIMEOUT("aerospike.default.read.timeout","60000"),

		//aerospike update policy
		USERID_BY_GTOKENIDS_MAX_UPDATE_RETRIES("aerospike.userIdByGTokenIds.update.maxRetries","5"),
		USERID_BY_GTOKENIDS_RETRY_THREAD_SLEEP_IN_MILLISECONDS("aerospike.userIdByGTokenIds.retry.sleep.in.milliseconds","10"),

		VERIFICATION_EMAIL_TEST_MODE("email.verification.testmode", "0"), 
		VERIFICATION_TEST_EMAIL_ID("email.verification.testmode.id", "Tech.ims@snapdeal.com"), 
		FORGET_PASSWORD_BY_EMAIL_TEMPLATE("forget.password.email.template", null), 
		GUEST_CREATE_USER_BY_EMAIL_TEMPLATE("guest.email.template", null), 
		CREATE_USER_BY_EMAIL_TEMPLATE("create.user.email.template", null),
		WELCOME_USER_BY_EMAIL_TEMPLATE("welcome.user.email.template", null),
		UPGRADE_USER_EMAIL_TEMPLATE("upgrade.user.email.template", null),
		FORGOT_PASSWORD_SUCCESS_EMAIL_TEMPLATE("forgot.password.success.email.template", null),
		UPDATE_MOBILE_NUMBER_EMAIL_TEMPLATE("update.mobile.number.email.template", null),
		//  SOCIAL_LINKING_EMAIL_TEMPLATE("social.link.email.template", null),
		//  NORMAL_ACCOUNT_LINKING_EMAIL_TEMPLATE("normal.account.link.email.template", null),


		SEND_EMAIL_REPLY_TO_EMAIL_ID("send.email.replyto.emailid", null),
		SEND_EMAIL_SUBJECT("send.email.subject", null),
		// SEND_EMAIL_SUBJECT_FREECHARGE("send.email.subject.freecharge",null),
		SEND_EMAIL_FROM_EMAIL_ID("send.email.from.emailid", null),
		SEND_EMAIL_UPGRADE_ACCOUNT("email.subject.upgrade.account","Congratulations on upgrading to your new FreeCharge account!"),
		SEND_EMAIL_UPGRADE_ACCOUNT_VERIFY("email.subject.upgrade.account.verify","Congratulations on upgrading to your new FreeCharge account, verify your email now!"),
		SEND_EMAIL_UPDATE_MOBILE("email.subject.update.mobile","Your mobile number registered with FreeCharge account has been updated."),
		// SEND_EMAIL_SOCIAL_LINK_ACCOUNT("email.subject.social.link.account",null),
		// SEND_EMAIL_NORMAL_LINK_ACCOUNT("email.subject.normal.link.account",null),
		SEND_EMAIL_WELCOME_EMAIL("email.subject.welcome.email","Congratulations on creating your new FreeCharge account via $merchant!"),
		SEND_EMAIL_FORGOT_PASSWORD_SUCCESS("email.subject.forgot.password.success","You have successfully changed your FreeCharge account password at $merchant"),


		LOGIN_SOCIALUSER_VALIDATE_ENABLE("login.socialuser.validate.enable","true"),

		DUMMY_MIGRATION_ENABLED("dummy.migration.enabled", "false"),
		UPGRADE_PERCENTAGE("upgrade.percentage","-1"),
		UPGRADE_ENABLED("upgrade.enabled","false"),
		UPGRADE_SKIP("upgrade.skip","true"),

		ONE_CHECK_ACCOUNT_CREATION_ENABLE("one.check.account.creation.enable","false"),

		//aerospike  UserSroproperties
		USER_SRO_TTL_INTERVAL("userSro.aerospike.ttl", "5184000"),
		USER_SRO_TTL_REFRESH_THRESHOLD_INTERVAL("userSro.aerospike.ttl.refresh.threshold", "86400"), 
		LINK_UPGRADE_GLOBAL_TOKEN_GENERATION_SERVICE_VERSION("link.upgrade.globaltokengeneration.service.version","V02"), 
		LINK_UPGRADE_TOKEN_GENERATION_SERVICE_VERSION("link.upgrade.tokengeneration.service.version","V02"),
		
		//Oauth Properties
		OAUTH_TOKEN_GENERATION_SERVICE_VERSION("oauth.tokengeneration.service.version","V03"),
		OAUTH_REFRESH_TOKEN_GENERATION_SERVICE_VERSION("oauth.globaltokengeneration.service.version","V03"),
		OAUTH_CODE_GENERATION_SERVICE_VERSION("oauth.transfertokengeneration.service.version","V03"),
		OAUTH_CODE_EXPIRY_TIME("oath.code.expiry.time.in.seconds","120"),
		OAUTH_REFRESH_TOKEN_EXPIRY_TIME("refresh.token.expiry.time", null),
		OAUTH_DEFAULT_REFRESH_TOKEN_EXPIRY_TIME("default.refresh.token.expiry.time","365"),
		OAUTH_ACCESS_TOKEN_EXPIRY_TIME("access.token.expiry.time", null),
		OAUTH_DEFAULT_ACCESS_TOKEN_EXPIRY_TIME("default.access.token.expiry.time","365"),
		
		//otp properties
		API_URL("sms.apiurl.valuefirst","http://api.myvaluefirst.com/psms/servlet/psms.Eservice2"),
		SMS_TMEPLATE_OTP_TMEPLATEID("sms.template.otp.templateId","1"),
		SMS_TEMPLATE_OTP_CHANNELID("sms.template.otp.channelId","2"),
		SMS_TEMEPLATE_OTP_TEMPLATENAME("sms.template.otp.templateName","otp"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY("sms.template.otp.templateBody","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_DNDSCRUBBINGON("sms.template.otp.dndScrubbingOn","0")	,
		SMS_APIURL_VALUEFIRST("sms.apiurl.valuefirst","http://api.myvaluefirst.com/psms/servlet/psms.Eservice2"),

		VERIFY_SUCCESS_MESSAGE("verify.success.message","SUCCESS"),
		VERIFY_FAILUE_MESSAGE("verify.failure.message","FAILURE"),
		VERIFICATION_OTP_EMAIL_TEMPLATE("verification.otp.email.template", "Your verification Pin: $verificationCode"),
		VERIFICATION_OTP_EMAIL_SUBJECT("verification.otp.email.subject","OTP Code"),

		SNAPDEAL_EMAIL_CLIENT_REPLY_EMAILID("snapdeal.email.client.reply.emailId","snapdeal@snapdeal.com"),
		SNAPDEAL_EMAIL_CLIENT_FROMTO_EMAILID("snapdeal.email.client.fromTo.emailId","SNAPDEAL<noreply@snapdeals.co.in>"),
		SNAPDEAL_EMAI_CLIENT_CLIENT_NAME("snapdeal.email.client.client.name","snapdeal"),
		SNAPDEAL_EMAIL_CLIENT_TEXTCONTENT("snapdeal.email.client.textContent","hi"),

		FREECHARGE_EMAIL_CLIENT_REPLY_EMAILID("freecharge.email.client.reply.emailId","noreply@freecharge.in"),

		OTP_LENGTH("otp.length","4"),
		OTP_EXPIRYINMINS("otp.expiryInMins","15"),
		OTP_RESED_ATTEMPTS_LIMIT("otp.reSendAttemptsLimit","3"),
		OTP_INVALID_ATTEMPTS_LIMIT("otp.invalidAttemptsLimit","2"),
		OTP_ENCRYPTION_ENABLED("otp.encryption.enabled", "false"),
		USER_BLOCK_TIME_IN_MINS("user.blockTimeInMins","5"),
		VALIDATE_SOCIAL_SOURCE("validate.social.source","true"),
		FIX_OTP_FOR_TESTING("fix.otp.for.testing","false"),
		RESET_PASSWORD_SEND_OTP_ON_MOBILE("reset.password.send.otp.on.mobile","true"),
		RESET_PASSWORD_SEND_OTP_ON_EMAIL("reset.password.send.otp.on.email","true"),
		// This defines the name of sd money wallet client.
		// For fix SNAPDEALTECH-56906
		SD_MONEY_WALLET_CLIENT_NAME("sd.money.wallet.client.name","SD_MONEY_WALLET"), 
		FC_NOTIFIER_ENABLED("fc.notifier.enabled", "true"), 
		MERGE_CARD_ENABLED("merge.card.enabled", "true"),

		NOTIFIER_SEND_TEST_CALL("notifier.send.test.call", "false"),
		NOTIFIER_SEND_TEST_EMAIL("notifier.send.test.email", "false"),
		NOTIFIER_SEND_TEST_SMS("notifier.send.test.sms", "false"),
		NOTIFIER_TEST_EMAIL_ID("notifier.test.email.id", "tech.ims@snapdeal.com"),
		NOTIFIER_TEST_SMS_NUMBER("notifier.test.sms.number", "9099999990"),

		//Whitelist API
		WHITELIST_APIS_ENABLED("apis.whiteList.enabled", "false"),
		WHITELIST_APIS_IS_ALLOWED_DEFAULT_VALUE("apis.whiteList.default.value", null),

		//TODO change this property key 
		SNAPDEAL_EMAIL_CLIENT_FROMTO_EMAILID_FREECHARGE("snapdeal.email.client.fromTo.emailId.freecharge","FreeCharge<noreply@freechargemail.in>"),

		VERIFICATION_OTP_EMAIL_SUBJECT_FORGOT_PASSWORD("verification.otp.email.subject.forgot.password","OTP Code"),
		VERIFICATION_OTP_EMAIL_SUBJECT_LINK_ACCOUNT("verification.otp.email.subject.link.account","OTP Code"),
		VERIFICATION_OTP_EMAIL_SUBJECT_MONEY_OUT("verification.otp.email.subject.money.out","OTP Code"),

		SMS_TEMPLATE_OTP_TEMPLATE_BODY_LOGIN("sms.template.otp.templateBody.login","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_FORGOT_PASSWORD("sms.template.otp.templateBody.forgot.password","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_MOBILE_VERIFICATION("sms.template.otp.templateBody.mobile.verification","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_USER_SIGNUP("sms.template.otp.templateBody.user.signup","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_UPDATE_MOBILE("sms.template.otp.templateBody.update.mobile","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_UPGRADE_USER("sms.template.otp.templateBody.upgrade.user","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_LINK_ACCOUNT("sms.template.otp.templateBody.link.account","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_ONECHECK_SOCIAL_SIGNUP("sms.template.otp.templateBody.onecheck","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_MONEY_OUT("sms.template.otp.templateBody.money.out","Your verification Pin: $verificationCode"),
		VERIFICATION_FLOW_COMPLETED_LINK(
				"verification.upgrade.flow.complete",
				"www.freecharge.com/user/complete?verificationCode=${"
						+ EnumConstants.VERIFICATION_FLOW_CODE_PLACEHOLDER + "}&email=${"
						+ EnumConstants.VERIFICATION_FLOW_EMAIL_PLACEHOLDER +"}"),
		VERIFICATION_FLOW_ONE_SIDE_MIGRATED_LINK(
				"verification.upgrade.flow.link", "www.freecharge.com/user/link?verificationCode=${"
						+ EnumConstants.VERIFICATION_FLOW_CODE_PLACEHOLDER + "}&email=${"
						+ EnumConstants.VERIFICATION_FLOW_EMAIL_PLACEHOLDER +"}"),
		VERIFICATION_FLOW_PARKING_LINK(
				"verification.upgrade.flow.parking", "www.snapdeal.com/parking?verificationCode=${"
						+ EnumConstants.VERIFICATION_FLOW_CODE_PLACEHOLDER + "}&email=${"
						+ EnumConstants.VERIFICATION_FLOW_EMAIL_PLACEHOLDER +"}"),
		USER_MIGRATION_STATUS_NOTIFY_ENABLED("user.migration.status.notify.enabled","true"),

		SMS_TEMPLATE_OTP_TEMPLATE_BODY_WALLET_PAY("sms.template.otp.templateBody.wallet.pay","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_ONECHECK_WALLET_LOAD("sms.template.otp.templateBody.wallet.load","Your verification Pin: $verificationCode"),
		SMS_TEMPLATE_OTP_TEMPLATE_BODY_WALLET_ENQUIRY("sms.template.otp.templateBody.wallet.enquiry","Your verification Pin: $verificationCode"),

		//Added to check verifyUserWithLinkedState Api for VERIFY_NEW_USER case
		VERIFY_HAPPY_FLOW_TESTING_MODE("verify.happy.flow.testing.mode", "false"), 
		ENABLE_NEW_G_TOKEN_GENERATION("enable.new.global.token.generation.logic", "false"),
		GLOBALTOKEN_CLEANUP_SIZE_VALUE("global.token.cleanup.size", "50"),
		TIME_INTERVAL_VALIDATE_DISCREPENCY("ims.discrepency.allowedTimeInterval", "259200000"), 
		GTOKEN_HARD_SIGNOUT_ON_LIMIT_REACHED("ims.globalToken.hardSignout", "false"), 
		VERIFYLINKSTATUS_PASSWORD_RESET_SAME_PWD("ims.verifylink.state.resetpassword.same", "false"),
		//Vault 
		VAULT_ENABLED("vault.net.ssl.vaultEnabled", "false"),
		CLIENT_KEY_ENCYPTION_ENABLED("ims.client.key.encryption.enabled","false"),
		DEFAULT_SECURE_KEY_LEN("default.secure.key.len","12"),
		VAULT_CONF_PATH_ABSOLUTE("vault.conf.path.absolute","config.path"),
		EMAIL_SMS_RETRIES("fcnotifier.email.sms.maxRetries","3"),
		SLEEP_BETWEEN_TOKEN_CLEANUP("ims.aerospike.sleep.cleanup","50"), 
		IMS_SNSPUBLISH("ims.sns.publish.enabled","false");

		public static class EnumConstants {
			public static final String VERIFICATION_FLOW_CODE_PLACEHOLDER = "upgradeFlowCode";
			public static final String VERIFICATION_FLOW_EMAIL_PLACEHOLDER = "upgradeFlowEmail";
		}
		
		private String key;
		private String defaultValue;

		private ConfigurationConstants(String key, String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

}
