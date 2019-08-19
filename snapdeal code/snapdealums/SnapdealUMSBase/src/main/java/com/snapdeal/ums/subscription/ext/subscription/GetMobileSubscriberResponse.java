
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;

public class GetMobileSubscriberResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -1422480968805219285L;
    @Tag(5)
    private MobileSubscriberSRO getMobileSubscriber;

    public GetMobileSubscriberResponse() {
    }

    public GetMobileSubscriberResponse(MobileSubscriberSRO getMobileSubscriber) {
        super();
        this.getMobileSubscriber = getMobileSubscriber;
    }

    public MobileSubscriberSRO getMobileSubscriber() {
        return getMobileSubscriber;
    }

    public void setMobileSubscriber(MobileSubscriberSRO getMobileSubscriber) {
        this.getMobileSubscriber = getMobileSubscriber;
    }

}
