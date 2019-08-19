
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;

public class UpdateMobileSubscriberRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 8575497987024309508L;
    @Tag(3)
    private MobileSubscriberSRO mobileSubscriber;

    public UpdateMobileSubscriberRequest() {
    }
    
    public UpdateMobileSubscriberRequest(MobileSubscriberSRO mobileSubscriber) {
        super();
        this.mobileSubscriber = mobileSubscriber;
    }

    public MobileSubscriberSRO getMobileSubscriber() {
        return mobileSubscriber;
    }

    public void setMobileSubscriber(MobileSubscriberSRO mobileSubscriber) {
        this.mobileSubscriber = mobileSubscriber;
    }

}
