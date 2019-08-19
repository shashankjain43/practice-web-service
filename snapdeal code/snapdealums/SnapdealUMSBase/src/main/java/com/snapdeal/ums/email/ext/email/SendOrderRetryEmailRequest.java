
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderRetryEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4523599776740902889L;
	@Tag(3)
    private Integer orderId;
    @Tag(4)
    private String retryUrl;

    public SendOrderRetryEmailRequest() {
    }

    public SendOrderRetryEmailRequest(Integer orderId, String retryUrl) {
        super();
        this.orderId = orderId;
        this.retryUrl = retryUrl;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getRetryUrl() {
        return retryUrl;
    }

    public void setRetryUrl(String retryUrl) {
        this.retryUrl = retryUrl;
    }

}
