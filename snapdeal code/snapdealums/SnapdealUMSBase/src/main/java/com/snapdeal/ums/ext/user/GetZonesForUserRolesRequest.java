
package com.snapdeal.ums.ext.user;

import java.util.Set;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;

public class GetZonesForUserRolesRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6446782891414338370L;
	@Tag(3)
    private Set<UserRoleSRO> userRoles;

    public GetZonesForUserRolesRequest() {
    }

    public Set<UserRoleSRO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRoleSRO> userRoles) {
        this.userRoles = userRoles;
    }

    public GetZonesForUserRolesRequest(Set<UserRoleSRO> userRoles) {
        this.userRoles = userRoles;
    }

}
