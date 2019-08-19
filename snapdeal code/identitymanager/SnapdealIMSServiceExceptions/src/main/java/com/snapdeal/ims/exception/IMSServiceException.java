package com.snapdeal.ims.exception;


public class IMSServiceException extends ValidationException {


	private static final long serialVersionUID = -1663092887536384506L;

	public IMSServiceException(String errCode, String errMsg) {
		super(errCode,errMsg);
	}
	
}
