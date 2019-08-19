
package com.snapdeal.ums.ext.activity;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;

public class ProcessActivityRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3830888866260788671L;
	@Tag(3)
    private ActivitySRO activity;

    public ProcessActivityRequest() {
    }

    public ActivitySRO getActivity() {
        return activity;
    }

    public void setActivity(ActivitySRO activity) {
        this.activity = activity;
    }

    public ProcessActivityRequest(ActivitySRO activity) {
        this.activity = activity;
    }

}
