
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class AddMobileSubscriberWithPinResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 1949505165503615855L;
    @Tag(5)
    private boolean addMobileSubscriberWithPin;

    public AddMobileSubscriberWithPinResponse() {
    }

    public AddMobileSubscriberWithPinResponse(boolean addMobileSubscriberWithPin) {
        super();
        this.addMobileSubscriberWithPin = addMobileSubscriberWithPin;
    }

    public boolean getAddMobileSubscriberWithPin() {
        return addMobileSubscriberWithPin;
    }

    public void setAddMobileSubscriberWithPin(boolean addMobileSubscriberWithPin) {
        this.addMobileSubscriberWithPin = addMobileSubscriberWithPin;
    }

}
