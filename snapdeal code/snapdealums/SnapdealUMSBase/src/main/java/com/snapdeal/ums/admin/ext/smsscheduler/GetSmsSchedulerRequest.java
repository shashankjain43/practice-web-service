
package com.snapdeal.ums.admin.ext.smsscheduler;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSmsSchedulerRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8467712846660385534L;
	@Tag(3)
    private int cityId;
    @Tag(4)
    private Date date;
    @Tag(5)
    private String status;

    public GetSmsSchedulerRequest() {
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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

    public GetSmsSchedulerRequest(int cityId, Date date, String status) {
        this.cityId = cityId;
        this.date = date;
        this.status = status;
    }

}
