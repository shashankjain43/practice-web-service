
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class IsOrderPresentInHistoryRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -707608020186923312L;
	@Tag(3)
    private String orderId;

    public IsOrderPresentInHistoryRequest() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public IsOrderPresentInHistoryRequest(String orderId) {
        this.orderId = orderId;
    }

}
