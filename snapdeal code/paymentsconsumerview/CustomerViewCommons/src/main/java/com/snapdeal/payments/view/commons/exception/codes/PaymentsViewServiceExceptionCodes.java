package com.snapdeal.payments.view.commons.exception.codes;

public enum PaymentsViewServiceExceptionCodes {

	USER_ID_DOES_NOT_EXIST("ER-5101","there is no transaction for this userId") ,
	MERCHANT_ID_DOES_NOT_EXIST("ER-5102","There is no transaction for this merchant id"),
	TRANSACTION_DOES_NOT_EXIST("ER-5103","There is no transaction for this request"),
	UNABLE_TO_CONNECT_TO_MERCHANT_ONBOARDING("ER-5104","Unable to connect to merchant onboarding."),
	ERROR_OCCURED_ON_MERCHANT_ONBOARDING("ER-5105","There is some error occured on merchantOnBoarding"),
	MERCHANT_VIEW_SEARCH_CRITERIA_EMPTY("ER-5106","Please provide one search criteria atleast!"),
	JSON_MAPPING_EXCEPTION("ER-5107","Exception while mapping the msg"),
	JSON_PARSE_EXCEPTION("ER-5108","Exception while parsing Message"),
	NULL_POINTER_EXCEPPTION("ER-5109","Exception occured beacuse metaData is null"),
	IO_EXCETPION("ER-5110","IO Excepiotn occured while pprocessing msg"),
	PAYABLES_EXCEPTION("ER-5111","Exception occured while quering payables"),
	TSM_EXCEPTION("ER-5112","exsception occured while quering TSM"),
	CLASS_CAST_EXCEPTION("ER-5113","Exception occured while casting metData"),
	UNKNOWN_EXCEPTION("ER-5114","Unkonwn Exception"),
	SQL_EXCEPTION("ER-5115","Exception occured while quering database"),
	DATA_INTEGRITY_EXCEPTION("ER-5116","data intergity excepiton"),
	REQUEST_VIEW_SEARCH_CRITERA_MISSING("ER-5117","Please provide a valid search criteria"),
	KEYMANAGER_CLIENT_INITIALIZATION_ERROR("ER-5118","invalid request"),
	SDMONEY_EXCEPTION("ER-5119","Exception occured while quering merchant unsettle amount"),
	MIN_PAGE_SIZE("ER-5120","Minimum page size is 1"),
	MAX_LIMIT("ER-5121","You have crossed maximum allowed limit"),
	MIN_LIMIT("ER-5128","Need minimum value greater than Zero"),
	SPLIT_ID_IS_BLANK("ER-5122","Split Id is blank"),
	SPLIT_TYPE_IS_BLANK("ER-5123","Split type is blank"),
	START_DATE_IS_BLANK("ER-5124","Start date is blank"),
	END_DATE_IS_BLANK("ER-5125","End date is Blank"),
	DATE_CAN_NOT_BE_IN_FUTURE("ER-5126","Future date can not be set"),
	ACTION_TYPE_IS_MISSING("ER-5127","Action Type can not be null/empty."),
	INPUT_ORDER_ID_IS_BLANK("ER-5128","order id is blank");
	;
	
	private String errCode;
	private String errMsg;

	private PaymentsViewServiceExceptionCodes(String errCode, String errMsg) {
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
