
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;

public class GetMobileSubscribersResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 2356243757520103375L;
    @Tag(5)
    private List<MobileSubscriberSRO> getMobileSubscribers = new ArrayList<MobileSubscriberSRO>();

    public GetMobileSubscribersResponse() {
    }

    public GetMobileSubscribersResponse(List<MobileSubscriberSRO> getMobileSubscribers) {
        super();
        this.getMobileSubscribers = getMobileSubscribers;
    }

    public List<MobileSubscriberSRO> getMobileSubscribers() {
        return getMobileSubscribers;
    }

    public void setMobileSubscribers(List<MobileSubscriberSRO> getMobileSubscribers) {
        this.getMobileSubscribers = getMobileSubscribers;
    }

}
