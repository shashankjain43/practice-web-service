
package com.snapdeal.ums.ext.user;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;

public class GetUsersByRoleAndZoneResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9107902570187444504L;
	@Tag(5)
    private List<UserRoleSRO> userRoleSROs =  new ArrayList<UserRoleSRO>();

    public GetUsersByRoleAndZoneResponse() {
    }

    public GetUsersByRoleAndZoneResponse(List<UserRoleSRO> getUsersByRoleAndZone) {
        super();
        this.userRoleSROs = getUsersByRoleAndZone;
    }

    public List<UserRoleSRO> getUsersByRoleAndZone() {
        return userRoleSROs;
    }

    public void seteUsersByRoleAndZone(List<UserRoleSRO> getUsersByRoleAndZone) {
        this.userRoleSROs = getUsersByRoleAndZone;
    }

}
