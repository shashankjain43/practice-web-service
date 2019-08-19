
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class VerifyUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2167347577597499512L;
	@Tag(3)
    private String email;
    @Tag(4)
    private String code;

    public VerifyUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public VerifyUserRequest(String email, String code) {
        this.email = email;
        this.code = code;
    }

}
