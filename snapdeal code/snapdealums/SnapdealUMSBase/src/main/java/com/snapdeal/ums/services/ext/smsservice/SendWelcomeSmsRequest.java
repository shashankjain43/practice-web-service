
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendWelcomeSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 8686275989509477537L;
    @Tag(3)
    private String mobile;
    
    
    public SendWelcomeSmsRequest(String mobile) {
        super();
        this.mobile = mobile;
    }

    public SendWelcomeSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
