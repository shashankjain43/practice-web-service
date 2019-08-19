
package com.snapdeal.ums.ext.activity;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;

public class GetSDCashActivitiesResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8815444509164130021L;
	@Tag(5)
    private List<ActivitySRO> getSDCashActivities = new ArrayList<ActivitySRO>();

    public GetSDCashActivitiesResponse() {
    }

    public GetSDCashActivitiesResponse(List<ActivitySRO> getSDCashActivities) {
        super();
        this.getSDCashActivities = getSDCashActivities;
    }

    public List<ActivitySRO> getGetSDCashActivities() {
        return getSDCashActivities;
    }

    public void setGetSDCashActivities(List<ActivitySRO> getSDCashActivities) {
        this.getSDCashActivities = getSDCashActivities;
    }

}
