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

public class GetSubscribedEmailSubscribersRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = -4230563485705135411L;
    /**
     * 
     */
    @Tag(3)
    private String email;
    
    public GetSubscribedEmailSubscribersRequest(){
        super();
    }
    
    public GetSubscribedEmailSubscribersRequest(String email){
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
