
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetConfirmationLinkRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6315750048679433543L;
	@Tag(3)
    private String email;

    public GetConfirmationLinkRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GetConfirmationLinkRequest(String email) {
        this.email = email;
    }

}
