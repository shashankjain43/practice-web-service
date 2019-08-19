
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendPendingCODOrderSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7697599547099547594L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String orderCode;

    public SendPendingCODOrderSmsRequest(String mobile, String orderCode) {
        super();
        this.mobile = mobile;
        this.orderCode = orderCode;
    }

    public SendPendingCODOrderSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
