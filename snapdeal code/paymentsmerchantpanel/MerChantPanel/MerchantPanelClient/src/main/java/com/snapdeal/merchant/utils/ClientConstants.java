package com.snapdeal.merchant.utils;

import com.snapdeal.merchant.enums.EnvironmentEnum;

/**
 * Client side constant configuration.
 */
public interface ClientConstants {

   // NOTE: Environment and secure enable flag.
   // Do not check-in if changed.
   EnvironmentEnum ENVIRONMENT = EnvironmentEnum.TESTING;
   Boolean IS_SECURE_ENABLED = Boolean.TRUE;

   // SDK version needs to be updated for every release.
   String CLIENT_SDK_VERSION = "1.0";

   String HTTPS = "https";

   // Time out for the http/https request, default set to 5 sec
   int TIMEOUT_TIME = 5000;
}
