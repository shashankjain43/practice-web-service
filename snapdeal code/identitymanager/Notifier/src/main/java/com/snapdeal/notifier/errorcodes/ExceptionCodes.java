package com.snapdeal.notifier.errorcodes;

public enum ExceptionCodes {

	GENERIC_INTERNAL_SERVER(
			"ER-3101",
			"Error while processing request. If the issue persists, please contact customer support."), ERROR_LOADING_TEMPLATE(
			"ER-3102", "Error loading mesasge template. Please try again"), ERROR_PARSING_XML(
			"ER-3103", "Error occured while parsing XML. Please try again"), CONFIGURATION_PARAMETER_MISSING(
			"ER-3104", "Configuration parameters are mandatory."), CONFIGURATION_NOT_PRESENT(
			"ER-3105", "Configuration not present for configType or key."), CIPHER_ERROR(
			"ER-3106", "Cipher Initialization error."), ENCRYPTION_ERROR(
			"ER-3107", "Encryption Error."), DECRTYPTION_ERROR("ER-3108",
			"Decryption Error."), TOKEN_GENERATION_SERVICE_NOT_SUPPORTED(
			"ER-3109",
			"Token Generation Service not supported for version or type"), ERROR_SENDING_SMS(
			"ER-5132", "error occured while sending sms"), FILE_NOT_FOUND(
			"ER-3110", "Value first sms template file not found"), CLIENT_CONFIGURATION_PARAMETER_MISSING(
			"ER-3111", "Client configuration parameter are mandatory."), CLIENT_CONFIGURATION_NOT_PRESENT(
			"ER-3112", "Client configuration not present for client Id."), WRONG_SMS_CHANNEL_INFO(
			"ER-5113", "Wrong smsChannelInfo"), ;

	private String errCode;
	private String errMsg;

	private ExceptionCodes(String errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public String errCode() {
		return this.errCode;
	}

	public String errMsg() {
		return this.errMsg;
	}

}
