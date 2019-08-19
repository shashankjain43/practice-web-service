
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderSummaryEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6030842334473670778L;
	@Tag(3)
    private Integer orderId;

    public SendOrderSummaryEmailRequest() {
    }

    public SendOrderSummaryEmailRequest(Integer orderId) {
        super();
        this.orderId = orderId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


}
