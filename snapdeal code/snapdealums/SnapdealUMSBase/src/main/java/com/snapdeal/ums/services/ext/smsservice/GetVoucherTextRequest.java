
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetVoucherTextRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 7736210623777411003L;
    @Tag(3)
    private Integer order;

    public GetVoucherTextRequest() {
    }
    
    public GetVoucherTextRequest(Integer order) {
        super();
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

}
