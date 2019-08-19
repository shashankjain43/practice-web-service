
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetMobileSubscriberPinRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 8754001254737162032L;
    @Tag(3)
    private String mobile;

    public GetMobileSubscriberPinRequest() {
    }
    
    public GetMobileSubscriberPinRequest(String mobile) {
        super();
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
