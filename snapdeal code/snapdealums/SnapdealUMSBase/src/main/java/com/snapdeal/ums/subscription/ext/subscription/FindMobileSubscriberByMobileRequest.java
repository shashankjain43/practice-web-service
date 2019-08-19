/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 20, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class FindMobileSubscriberByMobileRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = -5555637968569216084L;
    
    @Tag(3)
    String mobile;

    public FindMobileSubscriberByMobileRequest(){
        super();
    }
    
    public FindMobileSubscriberByMobileRequest(String mobile){
        super();
        this.mobile = mobile;
    }
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
}
