
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetOrCreateVendorUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8537025135011521906L;
	@Tag(3)
    private String email;

    public GetOrCreateVendorUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GetOrCreateVendorUserRequest(String email) {
        this.email = email;
    }

}
