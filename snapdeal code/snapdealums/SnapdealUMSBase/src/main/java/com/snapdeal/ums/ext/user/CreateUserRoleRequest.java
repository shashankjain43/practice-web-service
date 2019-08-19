
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;

public class CreateUserRoleRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7713067963507795482L;
	@Tag(3)
    private UserRoleSRO userRole;

    public CreateUserRoleRequest() {
    }

    public UserRoleSRO getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleSRO userRole) {
        this.userRole = userRole;
    }

    public CreateUserRoleRequest(UserRoleSRO userRole) {
        this.userRole = userRole;
    }

}
