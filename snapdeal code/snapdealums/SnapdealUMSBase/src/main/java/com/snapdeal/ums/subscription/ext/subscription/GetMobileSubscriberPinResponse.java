
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetMobileSubscriberPinResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -762613142386046686L;
    @Tag(5)
    private String getMobileSubscriberPin;

    public GetMobileSubscriberPinResponse() {
    }

    public GetMobileSubscriberPinResponse(String getMobileSubscriberPin) {
        super();
        this.getMobileSubscriberPin = getMobileSubscriberPin;
    }

    public String getMobileSubscriberPin() {
        return getMobileSubscriberPin;
    }

    public void setGetMobileSubscriberPin(String getMobileSubscriberPin) {
        this.getMobileSubscriberPin = getMobileSubscriberPin;
    }

}
