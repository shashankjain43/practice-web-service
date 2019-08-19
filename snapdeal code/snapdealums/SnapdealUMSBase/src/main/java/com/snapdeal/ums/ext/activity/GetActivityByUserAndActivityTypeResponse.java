
package com.snapdeal.ums.ext.activity;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;

public class GetActivityByUserAndActivityTypeResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -416420936238330976L;
	@Tag(5)
    private List<ActivitySRO> getActivityByUserAndActivityType;

    public GetActivityByUserAndActivityTypeResponse() {
    }

    public GetActivityByUserAndActivityTypeResponse(List<ActivitySRO> getActivityByUserAndActivityType) {
        super();
        this.getActivityByUserAndActivityType = getActivityByUserAndActivityType;
    }

    public List<ActivitySRO> getGetActivityByUserAndActivityType() {
        return getActivityByUserAndActivityType;
    }

    public void setGetActivityByUserAndActivityType(List<ActivitySRO> getActivityByUserAndActivityType) {
        this.getActivityByUserAndActivityType = getActivityByUserAndActivityType;
    }

}
