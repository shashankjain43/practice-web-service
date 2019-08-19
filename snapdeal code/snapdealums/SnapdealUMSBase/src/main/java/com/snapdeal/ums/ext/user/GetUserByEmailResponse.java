
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;

@AuditableClass
public class GetUserByEmailResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1606598051430921754L;
	@AuditableField
	@Tag(5)
    private UserSRO getUserByEmail;

    public GetUserByEmailResponse() {
    }

    public GetUserByEmailResponse(UserSRO getUserByEmail) {
        super();
        this.getUserByEmail = getUserByEmail;
    }

    public UserSRO getGetUserByEmail() {
        return getUserByEmail;
    }

    public void setGetUserByEmail(UserSRO getUserByEmail) {
        this.getUserByEmail = getUserByEmail;
    }

}
