
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetMobileSubscriberDetailByMobileRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 406406870498410014L;
    @Tag(3)
    private String mobile;

    public GetMobileSubscriberDetailByMobileRequest() {
    }
    
    public GetMobileSubscriberDetailByMobileRequest(String mobile) {
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
