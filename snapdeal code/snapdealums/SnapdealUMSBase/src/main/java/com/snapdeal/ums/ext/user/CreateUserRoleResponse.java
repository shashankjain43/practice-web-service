
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;

public class CreateUserRoleResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8233192274714792094L;
	@Tag(5)
    private UserRoleSRO createUserRole;

    public CreateUserRoleResponse() {
    }

    public CreateUserRoleResponse(UserRoleSRO createUserRole) {
        super();
        this.createUserRole = createUserRole;
    }

    public UserRoleSRO getCreateUserRole() {
        return createUserRole;
    }

    public void setCreateUserRole(UserRoleSRO createUserRole) {
        this.createUserRole = createUserRole;
    }

}
