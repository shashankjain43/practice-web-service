/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 15, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class UpdateReferralClickRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = -3170096790568097763L;

    @Tag(3)
    private UserSRO           user;
    
    @Tag(4)
    private String            referralChannel;

    public UpdateReferralClickRequest(){
        super();
    }
    
    public UpdateReferralClickRequest(UserSRO user , String referralChannel){
        super();
        this.user = user;
        this.referralChannel = referralChannel;
        
    }
    
    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public String getReferralChannel() {
        return referralChannel;
    }

    public void setReferralChannel(String referralChannel) {
        this.referralChannel = referralChannel;
    }
    
}
