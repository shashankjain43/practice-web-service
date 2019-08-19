
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailVerificationCodeRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5393510125336252912L;
	@Tag(3)
    private String email;

    public GetEmailVerificationCodeRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GetEmailVerificationCodeRequest(String email) {
        this.email = email;
    }

}
