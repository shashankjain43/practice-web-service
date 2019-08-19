
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetMobileAssociationByMobileRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -1030284824259017054L;
    @Tag(3)
    private String mobile;

    public GetMobileAssociationByMobileRequest() {
    }
    
    public GetMobileAssociationByMobileRequest(String mobile) {
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
