
package com.snapdeal.ums.admin.ext.smsscheduler;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.smsscheduler.SmsSchedulerSRO;

public class GetSmsSchedulerListResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2545333441939071126L;
	@Tag(5)
    private List<SmsSchedulerSRO> getSmsSchedulerList = new ArrayList<SmsSchedulerSRO>();

    public GetSmsSchedulerListResponse() {
    }

    public GetSmsSchedulerListResponse(List<SmsSchedulerSRO> getSmsSchedulerList) {
        super();
        this.getSmsSchedulerList = getSmsSchedulerList;
    }

    public List<SmsSchedulerSRO> getGetSmsSchedulerList() {
        return getSmsSchedulerList;
    }

    public void setGetSmsSchedulerList(List<SmsSchedulerSRO> getSmsSchedulerList) {
        this.getSmsSchedulerList = getSmsSchedulerList;
    }

}
