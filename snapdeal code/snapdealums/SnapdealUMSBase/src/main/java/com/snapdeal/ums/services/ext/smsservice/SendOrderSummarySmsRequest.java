
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderSummarySmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -6232989208848424443L;
    @Tag(3)
    private Integer order;
    

    public SendOrderSummarySmsRequest(Integer order) {
        super();
        this.order = order;
    }

    public SendOrderSummarySmsRequest() {
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
