
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendPendingResponseEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8763132115824714333L;
	@Tag(3)
    private Integer orderId;
	
	 public SendPendingResponseEmailRequest(){}

      public SendPendingResponseEmailRequest(Integer orderId) {
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
