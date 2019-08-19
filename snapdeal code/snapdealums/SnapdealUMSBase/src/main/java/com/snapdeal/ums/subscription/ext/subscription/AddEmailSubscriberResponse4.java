
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddEmailSubscriberResponse4
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -1863610254056079824L;
    @Tag(5)
    private boolean addEmailSubscriber;

    public AddEmailSubscriberResponse4() {
    }

    public AddEmailSubscriberResponse4(boolean addEmailSubscriber) {
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
