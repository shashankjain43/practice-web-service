
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetMobileSubscriptionsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 7355201208403623487L;
    @Tag(3)
    private String mobile;

    public GetMobileSubscriptionsRequest() {
    }
    
    public GetMobileSubscriptionsRequest(String mobile) {
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
