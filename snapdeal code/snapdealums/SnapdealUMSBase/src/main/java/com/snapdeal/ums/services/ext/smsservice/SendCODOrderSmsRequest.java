
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendCODOrderSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2774339404892824175L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String customerName;
    @Tag(5)
    private String orderCode;

    
    public SendCODOrderSmsRequest(String mobile, String customerName, String orderCode) {
        super();
        this.mobile = mobile;
        this.customerName = customerName;
        this.orderCode = orderCode;
    }

    public SendCODOrderSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
