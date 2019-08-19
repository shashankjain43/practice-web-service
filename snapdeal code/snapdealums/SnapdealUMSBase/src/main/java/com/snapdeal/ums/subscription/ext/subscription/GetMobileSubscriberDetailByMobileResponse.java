
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberDetailSRO;

public class GetMobileSubscriberDetailByMobileResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 9089339623988334067L;
    @Tag(5)
    private MobileSubscriberDetailSRO getMobileSubscriberDetailByMobile;

    public GetMobileSubscriberDetailByMobileResponse() {
    }

    public GetMobileSubscriberDetailByMobileResponse(MobileSubscriberDetailSRO getMobileSubscriberDetailByMobile) {
        super();
        this.getMobileSubscriberDetailByMobile = getMobileSubscriberDetailByMobile;
    }

    public MobileSubscriberDetailSRO getGetMobileSubscriberDetailByMobile() {
        return getMobileSubscriberDetailByMobile;
    }

    public void setGetMobileSubscriberDetailByMobile(MobileSubscriberDetailSRO getMobileSubscriberDetailByMobile) {
        this.getMobileSubscriberDetailByMobile = getMobileSubscriberDetailByMobile;
    }

}
