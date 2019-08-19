
package com.snapdeal.ums.admin.ext.smsscheduler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSmsSchedulerRequest2
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 942706454755529632L;
	@Tag(3)
    private List<Integer> cityId = new ArrayList<Integer>();
    @Tag(4)
    private Date date;
    @Tag(5)
    private String status;

    public GetSmsSchedulerRequest2() {
    }

    public List<Integer> getCityId() {
        return cityId;
    }

    public void setCityId(List<Integer> cityId) {
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

    public GetSmsSchedulerRequest2(List<Integer> cityId, Date date, String status) {
        this.cityId = cityId;
        this.date = date;
        this.status = status;
    }

}
