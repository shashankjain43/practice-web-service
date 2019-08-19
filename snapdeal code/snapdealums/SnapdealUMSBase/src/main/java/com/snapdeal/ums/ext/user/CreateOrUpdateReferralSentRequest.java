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

public class CreateOrUpdateReferralSentRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 8477601535147862480L;

    @Tag(3)
    private UserSRO           user;

    @Tag(4)
    private String            referralChannel;

    @Tag(5)
    private int               sentCount;
    
    public CreateOrUpdateReferralSentRequest(){
        super();
    }
    
    public CreateOrUpdateReferralSentRequest(UserSRO user, String referralChannel, int sentCount){
        super();
        this.user = user;
        this.referralChannel = referralChannel;
        this.sentCount = sentCount;
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

    public int getSentCount() {
        return sentCount;
    }

    public void setSentCount(int sentCount) {
        this.sentCount = sentCount;
    }

}
