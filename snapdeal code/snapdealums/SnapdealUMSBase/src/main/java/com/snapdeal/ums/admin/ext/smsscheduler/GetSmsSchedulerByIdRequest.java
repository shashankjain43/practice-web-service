
package com.snapdeal.ums.admin.ext.smsscheduler;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSmsSchedulerByIdRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7689827424835029168L;
	@Tag(3)
    private int id;

    public GetSmsSchedulerByIdRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GetSmsSchedulerByIdRequest(int id) {
        this.id = id;
    }

}
