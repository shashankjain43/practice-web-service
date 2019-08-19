
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;

public class UpdateSubscriberProfileRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4490772221767932796L;
    @Tag(3)
    private SubscriberProfileSRO profile;

    public UpdateSubscriberProfileRequest() {
    }
    
    public UpdateSubscriberProfileRequest(SubscriberProfileSRO profile) {
        super();
        this.profile = profile;
    }

    public SubscriberProfileSRO getProfile() {
        return profile;
    }

    public void setProfile(SubscriberProfileSRO profile) {
        this.profile = profile;
    }

}
