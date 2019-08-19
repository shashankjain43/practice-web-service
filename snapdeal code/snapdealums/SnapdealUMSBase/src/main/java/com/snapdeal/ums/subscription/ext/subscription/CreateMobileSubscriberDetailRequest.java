
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class CreateMobileSubscriberDetailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 7247861738796992436L;
    @Tag(3)
    private String mobile;

    public CreateMobileSubscriberDetailRequest() {
    }
    
    public CreateMobileSubscriberDetailRequest(String mobile) {
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
