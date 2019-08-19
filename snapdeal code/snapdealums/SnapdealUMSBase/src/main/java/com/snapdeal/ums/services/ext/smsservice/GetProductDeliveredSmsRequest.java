
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetProductDeliveredSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 1570481070499055226L;
    @Tag(3)
    private Integer suborder;

    public GetProductDeliveredSmsRequest() {
    }

    public Integer getSuborder() {
        return suborder;
    }

    public void setSuborder(Integer suborder) {
        this.suborder = suborder;
    }

}
