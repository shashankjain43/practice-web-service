/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 20, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;

public class FindMobileSubscriberByMobileResponse extends ServiceResponse{

    /**
     * 
     */
    private static final long serialVersionUID = 2101071826610320414L;
    
    @Tag(5)
    private MobileSubscriberSRO mobileSubscriberSRO;

    public MobileSubscriberSRO getMobileSubscriberSRO() {
        return mobileSubscriberSRO;
    }

    public void setMobileSubscriberSRO(MobileSubscriberSRO mobileSubscriberSRO) {
        this.mobileSubscriberSRO = mobileSubscriberSRO;
    }

}
