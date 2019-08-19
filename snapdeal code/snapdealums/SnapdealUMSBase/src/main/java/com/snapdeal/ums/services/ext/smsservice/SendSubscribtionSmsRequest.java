
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendSubscribtionSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -3927950934802644488L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String params;
    @Tag(5)
    private String email;

    
    public SendSubscribtionSmsRequest(String mobile, String params, String email) {
        super();
        this.mobile = mobile;
        this.params = params;
        this.email = email;
    }

    public SendSubscribtionSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
