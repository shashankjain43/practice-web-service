
package com.snapdeal.ums.ext.activity;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;

public class GetActivityByUserIdResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6063865124778676905L;
	@Tag(5)
    private List<ActivitySRO>  getActivityByUserId = new ArrayList<ActivitySRO>();

    public GetActivityByUserIdResponse() {
    }

    public GetActivityByUserIdResponse(List<ActivitySRO> getActivityByUserId) {
        super();
        this.getActivityByUserId = getActivityByUserId;
    }

    public List<ActivitySRO> getGetActivityByUserId() {
        return getActivityByUserId;
    }

    public void setGetActivityByUserId(List<ActivitySRO> getActivityByUserId) {
        this.getActivityByUserId = getActivityByUserId;
    }

}
