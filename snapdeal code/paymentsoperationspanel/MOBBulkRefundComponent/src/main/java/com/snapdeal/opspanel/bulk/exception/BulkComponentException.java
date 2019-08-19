package com.snapdeal.opspanel.bulk.exception;

import lombok.Data;

@Data
public class BulkComponentException extends Exception{


	private String errorCode;
	private String errorMsg;
	
	public BulkComponentException(String errCode, String errMsg) {
		this.errorCode=errCode;
		this.errorMsg=errMsg;
	}
	




	
}
