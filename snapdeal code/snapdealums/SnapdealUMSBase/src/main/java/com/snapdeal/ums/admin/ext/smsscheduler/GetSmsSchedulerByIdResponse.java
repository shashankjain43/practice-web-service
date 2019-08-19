
package com.snapdeal.ums.admin.ext.smsscheduler;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.smsscheduler.SmsSchedulerSRO;

public class GetSmsSchedulerByIdResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1706082533828454420L;
	@Tag(5)
    private SmsSchedulerSRO getSmsSchedulerById;

    public GetSmsSchedulerByIdResponse() {
    }

    public GetSmsSchedulerByIdResponse(SmsSchedulerSRO getSmsSchedulerById) {
        super();
        this.getSmsSchedulerById = getSmsSchedulerById;
    }

    public SmsSchedulerSRO getGetSmsSchedulerById() {
        return getSmsSchedulerById;
    }

    public void setGetSmsSchedulerById(SmsSchedulerSRO getSmsSchedulerById) {
        this.getSmsSchedulerById = getSmsSchedulerById;
    }

}
