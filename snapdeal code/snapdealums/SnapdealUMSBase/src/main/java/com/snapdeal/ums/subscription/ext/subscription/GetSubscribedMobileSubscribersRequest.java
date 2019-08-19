
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSubscribedMobileSubscribersRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 3841671205266092440L;
    @Tag(3)
    private String mobile;

    public GetSubscribedMobileSubscribersRequest() {
    }
    
    public GetSubscribedMobileSubscribersRequest(String mobile) {
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
