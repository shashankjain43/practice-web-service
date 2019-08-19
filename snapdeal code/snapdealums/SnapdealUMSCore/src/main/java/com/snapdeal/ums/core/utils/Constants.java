/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 17-Aug-2010
 *  @author bala
 */
package com.snapdeal.ums.core.utils;

public interface Constants {

    int                        TRANSACTION_CODE_SEQUENCE_START_NO                            = 100000;
    String                     TRACKING_PARAMETER_UTM_SOURCE                                 = "utm_source";
    String                     DUMMY_CITY                                                    = "sdindia";
    String                     MEMCACHE_KEY_PREFIX_SUBSCRIBER                                = "subscriber/";
    String                     MEMCACHE_KEY_PREFIX_SUBSCRIBER_PIN                            = "subscriberPin/";
    long                       DEFAULT_RELOAD_CACHE_INTERVAL                                 = 10800000;
    long                       DEFAULT_RELOAD_CACHE_CHECK_INTERVAL                           = 300000;
    String                     DEFAULT_CONTEXT_PATH                                          = "http://www.snapdeal.com/";
    String                     DEFAULT_CONTENT_PATH                                          = "http://i.sdlcdn.com/";
    int                        INITIAL_CAPACITY_CATALOG_MAP                                  = 100000;
    String                     MEMCACHE_KEY_PREFIX_EMAIL_VERIFICATION_CODE                   = "emailVerificationCode/";
    String                     MEMCACHE_KEY_PREFIX_MOBILE                                    = "mobile/";
    int                        MOBILE_CACHE_TIME_TO_LIVE                                     = 30 * 24 * 60 * 60;
    
}
