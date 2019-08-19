package com.snapdeal.ims.request;

import com.snapdeal.ims.exception.ValidationException;



public interface Request {
	public void validate() throws ValidationException;
}
