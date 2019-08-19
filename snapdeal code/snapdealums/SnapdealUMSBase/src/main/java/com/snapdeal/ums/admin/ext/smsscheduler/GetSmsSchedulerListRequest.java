
package com.snapdeal.ums.admin.ext.smsscheduler;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSmsSchedulerListRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5196226388748170550L;
	@Tag(3)
    private Date date;
    @Tag(4)
    private String status;

    public GetSmsSchedulerListRequest() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GetSmsSchedulerListRequest(Date date, String status) {
        this.date = date;
        this.status = status;
    }

}
