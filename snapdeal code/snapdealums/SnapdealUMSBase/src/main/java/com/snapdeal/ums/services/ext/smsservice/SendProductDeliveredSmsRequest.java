
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendProductDeliveredSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4810970023636559204L;
    @Tag(3)
    private Integer suborder;
    
    public SendProductDeliveredSmsRequest(Integer suborder) {
        super();
        this.suborder = suborder;
    }

    public SendProductDeliveredSmsRequest() {
    }

    public Integer getSuborder() {
        return suborder;
    }

    public void setSuborder(Integer suborder) {
        this.suborder = suborder;
    }

}
