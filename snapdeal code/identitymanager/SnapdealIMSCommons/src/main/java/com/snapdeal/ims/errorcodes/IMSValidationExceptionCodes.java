package com.snapdeal.ims.errorcodes;

public enum IMSValidationExceptionCodes {

	PASSWORD_DO_NOT_MATCH("ER-6101","Please enter correct username and password"),
	UNABLE_TO_RESET_PASSWORD("ER-6102","We are unable to reset the password. Please try again or contact customer support."),
	INVALID_CLIENT_TYPE("ER-6103","Permisible value for type are : [USER_FACING,NON_USER_FACING]"),
	INVALID_CLIENT_STATUS("ER-6104","Permisible value for status are : [ACTIVE,INACTIVE]"),
	INVALID_CLIENT_NAME("ER-6105","Client name should be a valid non empty string"),
	INVALID_CLIENT_MERCHANT("ER-6106","Permisible value for merchant are : [SNAPDEAL, FREECHARGE]"),
	INVALID_CLIENT_PLATFORM("ER-6107","Permisible value for client platforms are : [WEB, WAP, APP]"),
	USERID_DOES_NOT_MATCH("ER-6108","User Id does not match on which otp sent.");

	private String errCode;
	private String errMsg;

	private IMSValidationExceptionCodes(String errCode, String errMsg) {
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
