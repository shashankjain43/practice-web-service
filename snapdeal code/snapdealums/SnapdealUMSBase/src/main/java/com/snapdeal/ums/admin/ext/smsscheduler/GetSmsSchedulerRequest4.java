
package com.snapdeal.ums.admin.ext.smsscheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSmsSchedulerRequest4
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6276124477873315217L;
	@Tag(3)
    private List<Integer> cityIds = new ArrayList<Integer>();
    @Tag(4)
    private Date date;

    public GetSmsSchedulerRequest4() {
    }

    public List<Integer> getcityIds() {
        return cityIds;
    }

    public void setcityIds(List<Integer> cityIds) {
        this.cityIds = cityIds;
    }

    public Date getStatus() {
        return date;
    }

    public void setStatus(Date status) {
        this.date = status;
    }

    public GetSmsSchedulerRequest4(List<Integer> cityIds, Date date) {
        this.cityIds = cityIds;
        this.date = date;
    }

}
