
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;

public class GetMobileSubscriptionsResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 6005394415352029900L;
    @Tag(5)
    private List<MobileSubscriberSRO> getMobileSubscriptions = new ArrayList<MobileSubscriberSRO>();

    public GetMobileSubscriptionsResponse() {
    }

    public GetMobileSubscriptionsResponse(List<MobileSubscriberSRO> getMobileSubscriptions) {
        super();
        this.getMobileSubscriptions = getMobileSubscriptions;
    }

    public List<MobileSubscriberSRO> getMobileSubscriptions() {
        return getMobileSubscriptions;
    }

    public void setMobileSubscriptions(List<MobileSubscriberSRO> getMobileSubscriptions) {
        this.getMobileSubscriptions = getMobileSubscriptions;
    }

}
