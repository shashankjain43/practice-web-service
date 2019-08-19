
package com.snapdeal.ums.admin.ext.smsscheduler;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.smsscheduler.SmsSchedulerSRO;

public class GetSmsSchedulerResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3551618531151229753L;
	@Tag(5)
    private List<SmsSchedulerSRO> getSmsScheduler = new ArrayList<SmsSchedulerSRO>();

    public GetSmsSchedulerResponse() {
    }

    public GetSmsSchedulerResponse(List<SmsSchedulerSRO>  getSmsScheduler) {
        super();
        this.getSmsScheduler = getSmsScheduler;
    }

    public List<SmsSchedulerSRO>  getGetSmsScheduler() {
        return getSmsScheduler;
    }

    public void setGetSmsScheduler(List<SmsSchedulerSRO>  getSmsScheduler) {
        this.getSmsScheduler = getSmsScheduler;
    }

}
