
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddSubscriberResponse2
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -7507545657589921493L;
    @Tag(5)
    private boolean addSubscriber;

    public AddSubscriberResponse2() {
    }

    public AddSubscriberResponse2(boolean addSubscriber) {
        super();
        this.addSubscriber = addSubscriber;
    }

    public boolean getAddSubscriber() {
        return addSubscriber;
    }

    public void setAddSubscriber(boolean addSubscriber) {
        this.addSubscriber = addSubscriber;
    }

}
