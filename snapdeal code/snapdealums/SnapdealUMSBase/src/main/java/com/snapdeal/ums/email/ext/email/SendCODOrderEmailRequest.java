
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendCODOrderEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1680978972489974474L;
	@Tag(3)
    private String orderCode;

    public SendCODOrderEmailRequest() {
    }
    
    public SendCODOrderEmailRequest(String orderCode) {
        super();
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

}
