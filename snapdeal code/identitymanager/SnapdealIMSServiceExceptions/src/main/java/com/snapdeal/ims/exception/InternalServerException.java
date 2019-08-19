package com.snapdeal.ims.exception;

import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;

public class InternalServerException extends IMSGenericException {

	private static final long serialVersionUID = 6076890854175525661L;

	public InternalServerException(String errCode,String errMsg) {
		super(errCode,errMsg);
	}

	public InternalServerException(Throwable cause) {
		super(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg(), cause);
		
	}

	public InternalServerException() {
		super(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(), 
				IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
	}

	public InternalServerException(String errCode, String message, 
				Throwable cause) {
		super(errCode,message, cause);
	}
}
