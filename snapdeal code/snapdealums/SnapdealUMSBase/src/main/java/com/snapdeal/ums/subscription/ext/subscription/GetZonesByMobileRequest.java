
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetZonesByMobileRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -3723553774517756695L;
    @Tag(3)
    private String mobile;

    public GetZonesByMobileRequest() {
    }
    

    public GetZonesByMobileRequest(String mobile) {
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
