
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;

public class GetSubscriberProfileResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1833967575973818286L;
    @Tag(5)
    private SubscriberProfileSRO getSubscriberProfile;

    public GetSubscriberProfileResponse() {
    }

    public GetSubscriberProfileResponse(SubscriberProfileSRO getSubscriberProfile) {
        super();
        this.getSubscriberProfile = getSubscriberProfile;
    }

    public SubscriberProfileSRO getGetSubscriberProfile() {
        return getSubscriberProfile;
    }

    public void setSubscriberProfile(SubscriberProfileSRO getSubscriberProfile) {
        this.getSubscriberProfile = getSubscriberProfile;
    }

}
