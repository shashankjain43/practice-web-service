
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.base.utils.DateUtils.DateRange;

public class GetUserSDCashHistoryInDateRangeRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2734259385068135185L;
	@Tag(3)
    private com.snapdeal.base.utils.DateUtils.DateRange range;
    @Tag(4)
    private Integer userId;
    @Tag(5)
    private String activityType;

    public GetUserSDCashHistoryInDateRangeRequest() {
    }

    public com.snapdeal.base.utils.DateUtils.DateRange getRange() {
        return range;
    }

    public void setRange(com.snapdeal.base.utils.DateUtils.DateRange range) {
        this.range = range;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public GetUserSDCashHistoryInDateRangeRequest(DateRange range, Integer userId, String activityType) {
        this.range = range;
        this.userId = userId;
        this.activityType = activityType;
    }

}
