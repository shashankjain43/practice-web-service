
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddEmailSubscriberResponse2
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 532108596033712550L;
    @Tag(5)
    private boolean addEmailSubscriber;

    public AddEmailSubscriberResponse2() {
    }

    public AddEmailSubscriberResponse2(boolean addEmailSubscriber) {
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
