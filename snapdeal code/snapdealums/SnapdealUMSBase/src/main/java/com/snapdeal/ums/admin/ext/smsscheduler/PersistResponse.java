
package com.snapdeal.ums.admin.ext.smsscheduler;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class PersistResponse
    extends ServiceResponse
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 8331454991138298897L;
	@Tag(5)
	Integer smsSchedulerId ;
	public PersistResponse() {
    }
    public Integer getSmsSchedulerId() {
        return smsSchedulerId;
    }
    public void setSmsSchedulerId(Integer smsSchedulerId) {
        this.smsSchedulerId = smsSchedulerId;
    }
	
	

}
