package com.snapdeal.ims.exception;

import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;

public class IMSFileNotFoundException extends IMSGenericException {

	private static final long serialVersionUID = 1L;

	public IMSFileNotFoundException(String errCode, String errMsg) {
		super(errCode, errMsg);
	}

	public IMSFileNotFoundException(Throwable cause) {
		super(IMSInternalServerExceptionCodes.FILE_NOT_FOUND.errCode(),
				IMSInternalServerExceptionCodes.FILE_NOT_FOUND.errMsg(), cause);
	}

	public IMSFileNotFoundException(String errCode, String message,
			Throwable cause) {
		super(errCode, message, cause);
	}

	public IMSFileNotFoundException() {
		super(IMSInternalServerExceptionCodes.FILE_NOT_FOUND.errCode(),
				IMSInternalServerExceptionCodes.FILE_NOT_FOUND.errMsg());
	}
}
