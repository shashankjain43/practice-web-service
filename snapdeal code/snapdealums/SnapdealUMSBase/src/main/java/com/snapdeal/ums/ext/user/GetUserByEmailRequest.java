
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;

@AuditableClass
public class GetUserByEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7721733864437982349L;
	
	@AuditableField
	@Tag(3)
    private String email;

    public GetUserByEmailRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GetUserByEmailRequest(String email) {
        this.email = email;
    }

}
