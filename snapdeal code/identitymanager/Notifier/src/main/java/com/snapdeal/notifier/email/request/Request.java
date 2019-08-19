package com.snapdeal.notifier.email.request;

import com.snapdeal.notifier.exception.ValidationException;

public interface Request {
	public void validate() throws ValidationException;
}
