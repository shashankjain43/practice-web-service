
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendVoucherSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 7907286586302942952L;
    @Tag(3)
    private Integer suborderId;

    public SendVoucherSmsRequest() {
    }

    public SendVoucherSmsRequest(Integer suborderId) {
        super();
        this.suborderId = suborderId;
    }

    public Integer getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }

}
