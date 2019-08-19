
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOfferAvailedSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -728877896252012885L;
    @Tag(3)
    private String mobile;

    
    public SendOfferAvailedSmsRequest(String mobile) {
        super();
        this.mobile = mobile;
    }

    public SendOfferAvailedSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
