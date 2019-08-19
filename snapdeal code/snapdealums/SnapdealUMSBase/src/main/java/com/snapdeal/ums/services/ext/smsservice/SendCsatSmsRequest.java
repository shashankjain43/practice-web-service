
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendCsatSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2466159917910247502L;
    @Tag(3)
    private String mobile;

    public SendCsatSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
