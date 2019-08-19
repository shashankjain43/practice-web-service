
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderConfirmationProductSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -4462920520500084266L;
    @Tag(3)
    private Integer suborder;

    
    public SendOrderConfirmationProductSmsRequest(Integer suborder) {
        super();
        this.suborder = suborder;
    }

    public SendOrderConfirmationProductSmsRequest() {
    }

    public Integer getSuborder() {
        return suborder;
    }

    public void setSuborder(Integer suborder) {
        this.suborder = suborder;
    }

}
