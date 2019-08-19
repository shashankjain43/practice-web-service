/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Aug 12, 2010
 *  @author singla
 */
package com.snapdeal.ums.core.utils;

public interface QueryNames {

    String GET_USER_BY_EMAIL                      = "getUserByEmail";
    String GET_USER_BY_ID                         = "getUserById";
    String GET_USER_REFERRAL_TRACKING             = "getUserReferralTracking";
    
    //Mobile Subscriber Queries
    String GET_MOBILE_SUBSCRIBER_FROM_MOBILE      = "getMobileSubscriberFromMobile";
    String GET_MOBILE_SUBSCRIBERS_FROM_ZONE       = "getMobileSubscriberFromZone";
    
    //Email Subscriber Queries
    String GET_EMAIL_SUBSCRIBER_FROM_EMAIL        = "getEmailSubscriberFromEmail";
    String GET_EMAIL_SUBSCRIBERS_FROM_ZONE        = "getEmailSubscriberFromZone";
    String GET_EMAIL_SUBSCRIBER_DETAIL_FROM_EMAIL = "getEmailSubscriberVerificationFromEmail";
    
    String GET_ALL_EMAIL_SUBSCRIBERS              = "getAllEmailSubscribers";
    
    //Startup properties
    String GET_ALL_PROPERTIES                     = "getAllProperties";
    
    interface QueryParameters {
        String EMAIL              = "email";
        String STARTTIME          = "startTime";
        String ENDTIME            = "endTime";
        String ZONE_ID            = "zoneId";
        String MOBILE             = "mobile";
    }
    
}
