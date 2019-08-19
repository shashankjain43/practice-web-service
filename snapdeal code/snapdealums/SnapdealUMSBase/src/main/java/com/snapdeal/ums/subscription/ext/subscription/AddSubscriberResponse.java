
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddSubscriberResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 5416983499503726603L;
    @Tag(5)
    private boolean addSubscriber;

    public AddSubscriberResponse() {
    }

    public AddSubscriberResponse(boolean addSubscriber) {
        super();
        this.addSubscriber = addSubscriber;
    }

    public boolean getSubscriber() {
        return addSubscriber;
    }

    public void setAddSubscriber(boolean addSubscriber) {
        this.addSubscriber = addSubscriber;
    }

}
