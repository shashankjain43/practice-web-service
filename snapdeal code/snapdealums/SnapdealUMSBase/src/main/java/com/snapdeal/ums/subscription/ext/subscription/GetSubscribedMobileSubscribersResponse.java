
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;

public class GetSubscribedMobileSubscribersResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 7867945660043994404L;
    @Tag(5)
    private List<MobileSubscriberSRO> getSubscribedMobileSubscribers = new ArrayList<MobileSubscriberSRO>();

    public GetSubscribedMobileSubscribersResponse() {
    }

    public GetSubscribedMobileSubscribersResponse(List<MobileSubscriberSRO> getSubscribedMobileSubscribers) {
        super();
        this.getSubscribedMobileSubscribers = getSubscribedMobileSubscribers;
    }

    public List<MobileSubscriberSRO> getSubscribedMobileSubscribers() {
        return getSubscribedMobileSubscribers;
    }

    public void setSubscribedMobileSubscribers(List<MobileSubscriberSRO> getSubscribedMobileSubscribers) {
        this.getSubscribedMobileSubscribers = getSubscribedMobileSubscribers;
    }

}
