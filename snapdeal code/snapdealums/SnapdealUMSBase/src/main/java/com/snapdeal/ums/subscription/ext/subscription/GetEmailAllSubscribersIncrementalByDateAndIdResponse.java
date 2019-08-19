/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 02-Jul-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetEmailAllSubscribersIncrementalByDateAndIdResponse  extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = 1031207159435718680L;
    @Tag(5)
    private List<EmailSubscriberSRO> emailSubscribersSRO = new ArrayList<EmailSubscriberSRO>();

    public GetEmailAllSubscribersIncrementalByDateAndIdResponse() {
    }

    public GetEmailAllSubscribersIncrementalByDateAndIdResponse(List<EmailSubscriberSRO> getEmailAllSubscribersIncrementalInDateRange) {
        super();
        this.emailSubscribersSRO = getEmailAllSubscribersIncrementalInDateRange;
    }

    /**
     * @return the emailSubscribersSRO
     */
    public List<EmailSubscriberSRO> getEmailSubscribersSRO() {
        return emailSubscribersSRO;
    }

    /**
     * @param emailSubscribersSRO the emailSubscribersSRO to set
     */
    public void setEmailSubscribersSRO(List<EmailSubscriberSRO> emailSubscribersSRO) {
        this.emailSubscribersSRO = emailSubscribersSRO;
    }


}
