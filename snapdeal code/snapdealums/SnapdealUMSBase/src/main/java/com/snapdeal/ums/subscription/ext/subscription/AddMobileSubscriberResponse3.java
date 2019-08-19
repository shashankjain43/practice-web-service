
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddMobileSubscriberResponse3
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -6451114996538637197L;
    @Tag(5)
    private boolean addMobileSubscriber;

    public AddMobileSubscriberResponse3() {
    }

    public AddMobileSubscriberResponse3(boolean addMobileSubscriber) {
        super();
        this.addMobileSubscriber = addMobileSubscriber;
    }

    public boolean getAddMobileSubscriber() {
        return addMobileSubscriber;
    }

    public void setAddMobileSubscriber(boolean addMobileSubscriber) {
        this.addMobileSubscriber = addMobileSubscriber;
    }

}
