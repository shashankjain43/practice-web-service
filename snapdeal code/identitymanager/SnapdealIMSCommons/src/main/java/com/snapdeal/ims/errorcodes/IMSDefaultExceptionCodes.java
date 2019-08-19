package com.snapdeal.ims.errorcodes;

public enum IMSDefaultExceptionCodes {

	AUTHORIZATION("ER-1100", "Authorization Exception"),
	AUTHENTICATION("ER-2100", "Authentication Exception"), 
	INTERNAL_SERVER("ER-3100", "Internal Server Exception"),
	INVALID_REQUEST("ER-4100", "Invalid Request Parameter Exception"),
	SERVICE_EXCEPTION("ER-5100","Service Exception"),
	VALIDATION("ER-6100","Validation Exception"),
	INTERNAL_CLIENT("ER-7100","Internal Client Exception"),
	MIGRATION_EXCEPTION("ER-8100", "Migration Exception");

	private String errCode;
	private String errMsg;

	private IMSDefaultExceptionCodes(String errCode, String errMsg) {
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
