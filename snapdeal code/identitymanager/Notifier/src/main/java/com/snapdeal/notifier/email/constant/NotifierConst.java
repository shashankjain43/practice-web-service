package com.snapdeal.notifier.email.constant;

public interface NotifierConst {

	public long DEFAULT_EMAIL_WAIT_TIME = 150; // In milliseconds
	public String EMAIL_TASK_TYPE = "EMAIL_TASK";
	public long DEFAULT_EMAIL_EXECUTION_TIMEOUT = 30000;
	public long DEFAULT_EMAIL_RETRY_LIMIT = 11;
	public int DEFAULT_EMAIL_EXPONENTIALBASE = 3;
}
