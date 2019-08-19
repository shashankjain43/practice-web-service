package com.snapdeal.bulkprocess.exception;

import lombok.Data;

@Data
public class BulkProcessorException extends Exception{

	String errorCode;

	public BulkProcessorException(String errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode=errorCode;

	}

}
