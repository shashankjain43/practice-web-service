
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderReplacedSummarySmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -4596817962852657879L;
    @Tag(3)
    private Integer order;

    
    public SendOrderReplacedSummarySmsRequest(Integer order) {
        super();
        this.order = order;
    }
    

    public SendOrderReplacedSummarySmsRequest() {
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
