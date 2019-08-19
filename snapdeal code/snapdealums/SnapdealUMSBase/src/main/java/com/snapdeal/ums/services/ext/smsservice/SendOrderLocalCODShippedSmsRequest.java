
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderLocalCODShippedSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 2441903248040315598L;
    @Tag(3)
    private Integer suborder;

    
    public SendOrderLocalCODShippedSmsRequest(Integer suborder) {
        super();
        this.suborder = suborder;
    }

    public SendOrderLocalCODShippedSmsRequest() {
    }

    public Integer getSuborder() {
        return suborder;
    }

    public void setSuborder(Integer suborder) {
        this.suborder = suborder;
    }

}
