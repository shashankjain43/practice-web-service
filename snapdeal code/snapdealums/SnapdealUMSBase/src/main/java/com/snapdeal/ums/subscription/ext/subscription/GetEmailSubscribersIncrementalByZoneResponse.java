/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 22, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetEmailSubscribersIncrementalByZoneResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = 2759528027498752603L;

    @Tag(5)
    private List<EmailSubscriberSRO> getEmailSubscribersIncremental = new ArrayList<EmailSubscriberSRO>();

    public GetEmailSubscribersIncrementalByZoneResponse() {
    }

    public GetEmailSubscribersIncrementalByZoneResponse(List<EmailSubscriberSRO> getEmailSubscribersIncremental) {
        super();
        this.getEmailSubscribersIncremental = getEmailSubscribersIncremental;
    }

    public List<EmailSubscriberSRO> getEmailSubscribersIncremental() {
        return getEmailSubscribersIncremental;
    }

    public void setEmailSubscribersIncremental(List<EmailSubscriberSRO> getEmailSubscribersIncremental) {
        this.getEmailSubscribersIncremental = getEmailSubscribersIncremental;
    }
}
