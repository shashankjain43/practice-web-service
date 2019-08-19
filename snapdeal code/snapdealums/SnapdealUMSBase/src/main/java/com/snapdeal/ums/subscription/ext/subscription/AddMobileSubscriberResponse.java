
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddMobileSubscriberResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1122834310094603864L;
    @Tag(5)
    private boolean addMobileSubscriber;

    public AddMobileSubscriberResponse() {
    }

    public AddMobileSubscriberResponse(boolean addMobileSubscriber) {
        super();
        this.addMobileSubscriber = addMobileSubscriber;
    }

    public boolean getMobileSubscriber() {
        return addMobileSubscriber;
    }

    public void setAddMobileSubscriber(boolean addMobileSubscriber) {
        this.addMobileSubscriber = addMobileSubscriber;
    }

}
