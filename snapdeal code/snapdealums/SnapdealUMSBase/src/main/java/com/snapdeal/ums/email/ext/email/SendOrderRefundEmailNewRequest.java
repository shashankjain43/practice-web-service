
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.OrderCancellationEmailSRO;

public class SendOrderRefundEmailNewRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1965169473591586488L;
	@Tag(3)
    private OrderCancellationEmailSRO orderCancellationEmailSRO;

    public SendOrderRefundEmailNewRequest() {
    }

    public SendOrderRefundEmailNewRequest(OrderCancellationEmailSRO orderCancellationEmailSRO) {
        super();
        this.orderCancellationEmailSRO = orderCancellationEmailSRO;
    }

    public OrderCancellationEmailSRO getOrderCancellationEmailSRO() {
        return orderCancellationEmailSRO;
    }

    public void setOrderCancellationEmailSRO(OrderCancellationEmailSRO orderCancellationEmailSRO) {
        this.orderCancellationEmailSRO = orderCancellationEmailSRO;
    }

}
