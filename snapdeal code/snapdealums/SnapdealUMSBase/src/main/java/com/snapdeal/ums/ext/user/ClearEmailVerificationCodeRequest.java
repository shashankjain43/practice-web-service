
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class ClearEmailVerificationCodeRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1961442084102801512L;
	@Tag(3)
    private String email;

    public ClearEmailVerificationCodeRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClearEmailVerificationCodeRequest(String email) {
        this.email = email;
    }

}
