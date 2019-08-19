package com.snapdeal.payments.view.commons.exception.codes;

public enum PaymentsViewValidationExceptionCodes {

	INVALID_USER_ID("ER-6101","userId is in wrong format") ,
	USER_ID_IS_BLANK("ER-6102","User Identifier is blank"),
	MERCHANT_NAME_IS_BLANK("ER-6103","Merchant Name is blank"),
	MERCHANT_ID_IS_BLANK("ER-6104","Merchant Id is blank"),
	INCORRECT_DATE_FORMAT("ER-6105","Incorrect date format provided!"),
	NOT_SUPPORTED_PARAM("ER-6106","Unsupported Param In Request!"),
	START_DATE_IS_MISSING("ER-6107","startDate should not be empty!"),
	END_DATE_IS_MISSING("ER-6108","endDate should not be empty!"),
	TRANSACTION_REFERENCE_IS_BLANK("ER-6109","txn ref is blank");
	
	private String errCode;
	private String errMsg;

	private PaymentsViewValidationExceptionCodes(String errCode, String errMsg) {
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
