
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendShareDealSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 153070462177484268L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String recipient;
    @Tag(5)
    private Integer deal;

    public SendShareDealSmsRequest() {
    }

    public SendShareDealSmsRequest(String mobile, String recipient, Integer deal) {
        super();
        this.mobile = mobile;
        this.recipient = recipient;
        this.deal = deal;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public Integer getDeal() {
        return deal;
    }

    public void setDeal(Integer deal) {
        this.deal = deal;
    }

}
