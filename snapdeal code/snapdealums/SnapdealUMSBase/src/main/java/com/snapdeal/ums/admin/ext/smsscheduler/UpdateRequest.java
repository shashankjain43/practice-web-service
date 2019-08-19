
package com.snapdeal.ums.admin.ext.smsscheduler;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.smsscheduler.SmsSchedulerSRO;

public class UpdateRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5871273105356695944L;
	@Tag(3)
    private SmsSchedulerSRO smsScheduler;

    public UpdateRequest() {
    }

    public SmsSchedulerSRO getSmsScheduler() {
        return smsScheduler;
    }

    public void setSmsScheduler(SmsSchedulerSRO smsScheduler) {
        this.smsScheduler = smsScheduler;
    }

    public UpdateRequest(SmsSchedulerSRO smsScheduler) {
        this.smsScheduler = smsScheduler;
    }

}
