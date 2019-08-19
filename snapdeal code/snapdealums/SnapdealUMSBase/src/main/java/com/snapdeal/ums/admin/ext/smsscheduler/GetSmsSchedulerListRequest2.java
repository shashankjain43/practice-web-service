
package com.snapdeal.ums.admin.ext.smsscheduler;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSmsSchedulerListRequest2
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9181809341973120456L;
	@Tag(3)
    private Date date;

    public GetSmsSchedulerListRequest2() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GetSmsSchedulerListRequest2(Date date) {
        this.date = date;
    }

}
