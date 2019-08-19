
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddEmailSubscriberResponse3
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -5000769935344460994L;
    @Tag(5)
    private boolean addEmailSubscriber;

    public AddEmailSubscriberResponse3() {
    }

    public AddEmailSubscriberResponse3(boolean addEmailSubscriber) {
        super();
        this.addEmailSubscriber = addEmailSubscriber;
    }

    public boolean getAddEmailSubscriber() {
        return addEmailSubscriber;
    }

    public void setAddEmailSubscriber(boolean addEmailSubscriber) {
        this.addEmailSubscriber = addEmailSubscriber;
    }

}
