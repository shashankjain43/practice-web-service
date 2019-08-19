
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;

public class CreateSubscriberProfileResponse2
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 5752209872341935531L;
    @Tag(5)
    private SubscriberProfileSRO createSubscriberProfile;

    public CreateSubscriberProfileResponse2() {
    }

    public CreateSubscriberProfileResponse2(SubscriberProfileSRO createSubscriberProfile) {
        super();
        this.createSubscriberProfile = createSubscriberProfile;
    }

    public SubscriberProfileSRO getSubscriberProfile() {
        return createSubscriberProfile;
    }

    public void setSubscriberProfile(SubscriberProfileSRO createSubscriberProfile) {
        this.createSubscriberProfile = createSubscriberProfile;
    }

}
