
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddMobileSubscriberResponse4
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 2257640467909459865L;
    @Tag(5)
    private boolean addMobileSubscriber;

    public AddMobileSubscriberResponse4() {
    }

    public AddMobileSubscriberResponse4(boolean addMobileSubscriber) {
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
