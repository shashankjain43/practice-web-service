
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetUserPreferenceByMobileRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5142061521725260155L;
	@Tag(3)
    private String phoneNo;

    public GetUserPreferenceByMobileRequest() {
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public GetUserPreferenceByMobileRequest(String phoneNo) {
        this.phoneNo = phoneNo;
    }

}
