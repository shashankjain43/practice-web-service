
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class IsDNDActiveRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 2809717259331658200L;
    @Tag(3)
    private String mobile;
    
    public IsDNDActiveRequest(String mobile) {
        super();
        this.mobile = mobile;
    }

    public IsDNDActiveRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
