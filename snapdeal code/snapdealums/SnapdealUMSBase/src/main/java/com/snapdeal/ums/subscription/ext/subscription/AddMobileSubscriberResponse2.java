
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddMobileSubscriberResponse2
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 6719496588006945925L;
    @Tag(5)
    private boolean addMobileSubscriber;

    public AddMobileSubscriberResponse2() {
    }

    public AddMobileSubscriberResponse2(boolean addMobileSubscriber) {
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
