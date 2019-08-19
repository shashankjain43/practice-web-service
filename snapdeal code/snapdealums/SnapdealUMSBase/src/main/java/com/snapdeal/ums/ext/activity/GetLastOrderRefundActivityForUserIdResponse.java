
package com.snapdeal.ums.ext.activity;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;

public class GetLastOrderRefundActivityForUserIdResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6500243311240943037L;
	@Tag(5)
    private ActivitySRO getLastOrderRefundActivityForUserId;

    public GetLastOrderRefundActivityForUserIdResponse() {
    }

    public GetLastOrderRefundActivityForUserIdResponse(ActivitySRO getLastOrderRefundActivityForUserId) {
        super();
        this.getLastOrderRefundActivityForUserId = getLastOrderRefundActivityForUserId;
    }

    public ActivitySRO getGetLastOrderRefundActivityForUserId() {
        return getLastOrderRefundActivityForUserId;
    }

    public void setGetLastOrderRefundActivityForUserId(ActivitySRO getLastOrderRefundActivityForUserId) {
        this.getLastOrderRefundActivityForUserId = getLastOrderRefundActivityForUserId;
    }

}
