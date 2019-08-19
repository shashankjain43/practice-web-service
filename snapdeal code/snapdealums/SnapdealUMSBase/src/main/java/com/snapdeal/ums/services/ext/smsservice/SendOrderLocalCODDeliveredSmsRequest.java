
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderLocalCODDeliveredSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2031673110467412652L;
    @Tag(3)
    private Integer suborder;

    
    public SendOrderLocalCODDeliveredSmsRequest(Integer suborder) {
        super();
        this.suborder = suborder;
    }

    public SendOrderLocalCODDeliveredSmsRequest() {
    }

    public Integer getSuborder() {
        return suborder;
    }

    public void setSuborder(Integer suborder) {
        this.suborder = suborder;
    }

}
