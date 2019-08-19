package com.snapdeal.vanila.enums;

public enum OPSErrorEnum {

	DB_ERROR_MERCHANT_OPS_DATA_BY_ID("ER-1201"),
	DB_ERROR_MERCHANT_OPS_DATA("ER-1202"),
	DB_ERROR_INSERTING_MERCHANT_OPS_DATA("ER-1203");
	
	private OPSErrorEnum(String errorCode) {
		this.errorCode=errorCode;

	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	private String errorCode;

	

}
