package com.snapdeal.ims.activity.exception;

public class ActivityException extends ActivityGenericException {

	private static final long serialVersionUID = 2098153784319255395L;

	public ActivityException(String errCode, String errMsg) {
		super(errCode, errMsg);
		this.setErrCode(errCode);
		this.setErrMsg(errMsg);
	}

	public ActivityException(Throwable cause) {
		super(cause);
	}

	public ActivityException(String errCode, String message,
			Throwable cause) {
		super(errCode, message, cause);
	}

	{
		this.setErrCode(ActivityExceptionCodes.ActivityException.errCode());
		this.setErrMsg(ActivityExceptionCodes.ActivityException.errMsg());
	}
}
