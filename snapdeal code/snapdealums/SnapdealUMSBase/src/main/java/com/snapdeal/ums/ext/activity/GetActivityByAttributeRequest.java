
package com.snapdeal.ums.ext.activity;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;

public class GetActivityByAttributeRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2682607109345658791L;
	@Tag(3)
    private ActivitySRO activity;
    @Tag(4)
    private String attribute;

    public GetActivityByAttributeRequest() {
    }

    public ActivitySRO getActivity() {
        return activity;
    }

    public void setActivity(ActivitySRO activity) {
        this.activity = activity;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public GetActivityByAttributeRequest(ActivitySRO activity, String attribute) {
        this.activity = activity;
        this.attribute = attribute;
    }

}
