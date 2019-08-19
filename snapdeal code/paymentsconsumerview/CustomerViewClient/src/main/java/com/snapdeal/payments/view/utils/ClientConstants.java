package com.snapdeal.payments.view.utils;

import com.snapdeal.payments.view.enums.EnvironmentEnum;

/**
 * Client side constant configuration.<br/>
 */
public interface ClientConstants {

	// NOTE: Environment and secure enable flag.
	// Do not check-in if changed.
	EnvironmentEnum ENVIRONMENT = EnvironmentEnum.PRODUCTION;

	// SDK version needs to be updated for every release.
	String CLIENT_SDK_VERSION = "1.0";

	// Time out for the http/https request, default set to 5 sec
	int TIMEOUT_TIME = 5000;

	Boolean IS_SECURE_ENABLED = Boolean.TRUE;

	// SDK version needs to be updated for every release.

	String HTTPS = "https";

	// Time out for the http/https request, default set to 5 sec

	public static final String REQUEST_RECV_TIMESTAMP = "startTime";
	public static final String RESPONSE_SENT_TIMESTAMP = "endTime";
}