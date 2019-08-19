
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.sms.sro.SmsMessageSRO;

public class SendRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -4598600220443580888L;
    @Tag(3)
    private SmsMessageSRO suborderId;

    public SendRequest() {
    }

    public SmsMessageSRO getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(SmsMessageSRO suborderId) {
        this.suborderId = suborderId;
    }

}
