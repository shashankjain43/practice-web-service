
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendUnsubscribeSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -5934661465882995703L;
    @Tag(3)
    private String mobile;

    
    public SendUnsubscribeSmsRequest(String mobile) {
        super();
        this.mobile = mobile;
    }

    public SendUnsubscribeSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
