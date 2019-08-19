
package com.snapdeal.ums.ext.activity;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;

public class GetActivityByAttributeResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5146022908296188710L;
	@Tag(5)
    private List<ActivitySRO> getActivityByAttribute = new ArrayList<ActivitySRO>();

    public GetActivityByAttributeResponse() {
    }

    public GetActivityByAttributeResponse(List<ActivitySRO> getActivityByAttribute) {
        super();
        this.getActivityByAttribute = getActivityByAttribute;
    }

    public List<ActivitySRO> getGetActivityByAttribute() {
        return getActivityByAttribute;
    }

    public void setGetActivityByAttribute(List<ActivitySRO> getActivityByAttribute) {
        this.getActivityByAttribute = getActivityByAttribute;
    }

}
