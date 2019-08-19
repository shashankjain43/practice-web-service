
package com.snapdeal.ums.admin.ext.smsscheduler;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSmsSchedulerRequest3
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2429923233185920127L;
	
	//TODO: Naveen check order of int and date in api
	@Tag(3)
	private int cityID;
    
    @Tag(4)
    private Date date;

    public GetSmsSchedulerRequest3() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public GetSmsSchedulerRequest3(int cityID, Date date) {
        this.cityID = cityID;
        this.date = date;
    }


}
