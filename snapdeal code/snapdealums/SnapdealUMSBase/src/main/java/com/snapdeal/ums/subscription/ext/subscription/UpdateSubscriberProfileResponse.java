
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;

public class UpdateSubscriberProfileResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 8270913914359320088L;
    @Tag(5)
    private SubscriberProfileSRO updateSubscriberProfile;

    public UpdateSubscriberProfileResponse() {
    }

    public UpdateSubscriberProfileResponse(SubscriberProfileSRO updateSubscriberProfile) {
        super();
        this.updateSubscriberProfile = updateSubscriberProfile;
    }

    public SubscriberProfileSRO getUpdatedSubscriberProfile() {
        return updateSubscriberProfile;
    }

    public void setUpdatedSubscriberProfile(SubscriberProfileSRO updateSubscriberProfile) {
        this.updateSubscriberProfile = updateSubscriberProfile;
    }

}
