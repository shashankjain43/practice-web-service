
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.sms.sro.SmsMessageSRO;

public class GetVoucherSmsResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 8201872410876952384L;
    @Tag(5)
    private SmsMessageSRO getVoucherSms;

    public GetVoucherSmsResponse() {
    }

    public GetVoucherSmsResponse(SmsMessageSRO getVoucherSms) {
        super();
        this.getVoucherSms = getVoucherSms;
    }

    public SmsMessageSRO getGetVoucherSms() {
        return getVoucherSms;
    }

    public void setGetVoucherSms(SmsMessageSRO getVoucherSms) {
        this.getVoucherSms = getVoucherSms;
    }

}
