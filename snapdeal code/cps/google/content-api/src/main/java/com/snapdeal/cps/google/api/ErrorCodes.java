/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */  
package com.snapdeal.cps.google.api;

/**
 *  
 *  @version     1.0, 11-Nov-2014
 *  @author Shashank Jain<jain.shashank@snapdeal.com>
 */
public enum ErrorCodes {
    
    //Quota errors
    TOO_MANY_FEEDS("quota/too_many_feeds"),
    TOO_MANY_ITEMS("quota/too_many_items"),
    TOO_MANY_REQUESTS("quota/too_many_requests"),
    TOO_MANY_SUBACCOUNTS("quota/too_many_subaccounts");
    
    private String msg;

    private ErrorCodes(String msg)
    {
        this.msg = msg;
    }

    public String getMsg()
    {
        return this.msg;
    }
    
    @Override
    public String toString()
    {
        return this.msg;
    }
}
