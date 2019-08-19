
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;

public class CreateSubscriberProfileResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1488018696327912973L;
    @Tag(5)
    private SubscriberProfileSRO createSubscriberProfile;

    public CreateSubscriberProfileResponse() {
    }

    public CreateSubscriberProfileResponse(SubscriberProfileSRO createSubscriberProfile) {
        super();
        this.createSubscriberProfile = createSubscriberProfile;
    }

    public SubscriberProfileSRO getSubscriberProfile() {
        return createSubscriberProfile;
    }

    public void setCreateSubscriberProfile(SubscriberProfileSRO createSubscriberProfile) {
        this.createSubscriberProfile = createSubscriberProfile;
    }

}
