
package com.snapdeal.ums.admin.ext.smsscheduler;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.smsscheduler.SmsSchedulerSRO;

public class PersistRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3443206283210289175L;
	@Tag(3)
    private SmsSchedulerSRO smsScheduler;

    public PersistRequest() {
    }

    public SmsSchedulerSRO getSmsScheduler() {
        return smsScheduler;
    }

    public void setSmsScheduler(SmsSchedulerSRO smsScheduler) {
        this.smsScheduler = smsScheduler;
    }

    public PersistRequest(SmsSchedulerSRO smsScheduler) {
        this.smsScheduler = smsScheduler;
    }

}
