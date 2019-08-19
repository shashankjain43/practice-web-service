
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetLastCreatedTimestampFromSDCashHistoryRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4231024910867891798L;
	@Tag(3)
    private String activityType;

    public GetLastCreatedTimestampFromSDCashHistoryRequest() {
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public GetLastCreatedTimestampFromSDCashHistoryRequest(String activityType) {
        this.activityType = activityType;
    }

}
