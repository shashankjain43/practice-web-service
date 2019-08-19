
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class CreateMobileSubscriberResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -3862255998274730376L;
    @Tag(5)
    private boolean createMobileSubscriber;

    public CreateMobileSubscriberResponse() {
    }

    public CreateMobileSubscriberResponse(boolean createMobileSubscriber) {
        super();
        this.createMobileSubscriber = createMobileSubscriber;
    }

    public boolean getCreateMobileSubscriber() {
        return createMobileSubscriber;
    }

    public void setCreateMobileSubscriber(boolean createMobileSubscriber) {
        this.createMobileSubscriber = createMobileSubscriber;
    }

}
