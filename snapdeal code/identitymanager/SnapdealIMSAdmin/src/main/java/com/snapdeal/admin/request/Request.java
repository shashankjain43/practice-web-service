package com.snapdeal.admin.request;

import com.snapdeal.ims.exception.ValidationException;



public interface Request {
	public void validate() throws ValidationException;
}
