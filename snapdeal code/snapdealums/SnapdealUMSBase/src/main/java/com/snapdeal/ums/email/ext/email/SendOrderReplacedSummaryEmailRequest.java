
package com.snapdeal.ums.email.ext.email;

import java.util.Set;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendOrderReplacedSummaryEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6962711165969990317L;
	@Tag(3)
    private Integer orderId;
    @Tag(4)
    private Set<Integer> suborderIds;

    public SendOrderReplacedSummaryEmailRequest() {
    }

    public SendOrderReplacedSummaryEmailRequest(Integer orderId, Set<Integer> suborderIds) {
        super();
        this.orderId = orderId;
        this.suborderIds = suborderIds;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public void setSuborderIds(Set<Integer> suborderIds) {
        this.suborderIds = suborderIds;
    }

    public Set<Integer> getSuborderIds() {
        return suborderIds;
    }

}
