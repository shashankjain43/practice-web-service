
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddEmailSubscriberResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -871488905689471285L;
    @Tag(5)
    private boolean addEmailSubscriber;

    public AddEmailSubscriberResponse() {
    }

    public AddEmailSubscriberResponse(boolean addEmailSubscriber) {
        super();
        this.addEmailSubscriber = addEmailSubscriber;
    }

    public boolean getEmailSubscriber() {
        return addEmailSubscriber;
    }

    public void setAddEmailSubscriber(boolean addEmailSubscriber) {
        this.addEmailSubscriber = addEmailSubscriber;
    }

}
