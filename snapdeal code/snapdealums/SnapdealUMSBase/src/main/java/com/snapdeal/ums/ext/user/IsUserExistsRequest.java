
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class IsUserExistsRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4514222569922957216L;
	@Tag(3)
    private String email;

    public IsUserExistsRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public IsUserExistsRequest(String email) {
        this.email = email;
    }

}
