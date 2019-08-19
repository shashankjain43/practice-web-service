package com.snapdeal.merchant.client.util;

public enum ClientDefaultExceptionCodes {

	SERVICE_EXCEPTION("ER-1","Merchant Panel Exception"),
	INTERNAL_CLIENT("ER-2","Merchant Panel Client Exception");

	private String errCode;
	private String errMsg;

	private ClientDefaultExceptionCodes(String errCode, String errMsg) {
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
