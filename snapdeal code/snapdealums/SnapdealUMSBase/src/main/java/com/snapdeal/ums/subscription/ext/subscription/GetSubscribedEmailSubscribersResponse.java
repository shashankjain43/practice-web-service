/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 20, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetSubscribedEmailSubscribersResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = -750708795932358250L;

    @Tag(5)
    private List<EmailSubscriberSRO> emailSubscriberSRO;

    public List<EmailSubscriberSRO> getEmailSubscriberSRO() {
        return emailSubscriberSRO;
    }

    public void setEmailSubscriberSRO(List<EmailSubscriberSRO> emailSubscriberSRO) {
        this.emailSubscriberSRO = emailSubscriberSRO;
    }
}
