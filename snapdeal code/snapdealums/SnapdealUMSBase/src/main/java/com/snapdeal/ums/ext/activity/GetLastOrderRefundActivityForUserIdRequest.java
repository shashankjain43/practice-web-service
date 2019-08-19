
package com.snapdeal.ums.ext.activity;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetLastOrderRefundActivityForUserIdRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4689723992137541593L;
	@Tag(3)
    private Integer userId;
    @Tag(4)
    private Integer activityTypeId;

    public GetLastOrderRefundActivityForUserIdRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public GetLastOrderRefundActivityForUserIdRequest(Integer userId, Integer activityTypeId) {
        this.userId = userId;
        this.activityTypeId = activityTypeId;
    }

}
