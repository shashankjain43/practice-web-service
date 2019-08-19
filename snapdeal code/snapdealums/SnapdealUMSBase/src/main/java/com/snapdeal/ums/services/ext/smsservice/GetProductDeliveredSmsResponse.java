
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.sms.sro.SmsMessageSRO;

public class GetProductDeliveredSmsResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -5724274059792540973L;
    @Tag(5)
    private SmsMessageSRO getProductDeliveredSms;

    public GetProductDeliveredSmsResponse() {
    }

    public GetProductDeliveredSmsResponse(SmsMessageSRO getProductDeliveredSms) {
        super();
        this.getProductDeliveredSms = getProductDeliveredSms;
    }

    public SmsMessageSRO getGetProductDeliveredSms() {
        return getProductDeliveredSms;
    }

    public void setGetProductDeliveredSms(SmsMessageSRO getProductDeliveredSms) {
        this.getProductDeliveredSms = getProductDeliveredSms;
    }

}
