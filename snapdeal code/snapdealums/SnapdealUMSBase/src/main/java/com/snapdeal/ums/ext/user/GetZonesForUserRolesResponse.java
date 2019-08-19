
package com.snapdeal.ums.ext.user;

import java.util.ArrayList;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetZonesForUserRolesResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7977355295916013600L;
	@Tag(5)
    private List<Integer> getZonesForUserRoles = new ArrayList<Integer>();

    public GetZonesForUserRolesResponse() {
    }

    public GetZonesForUserRolesResponse(List<Integer> getZonesForUserRoles) {
        super();
        this.getZonesForUserRoles = getZonesForUserRoles;
    }

    public List<Integer> getGetZonesForUserRoles() {
        return getZonesForUserRoles;
    }

    public void setGetZonesForUserRoles(List<Integer> getZonesForUserRoles) {
        this.getZonesForUserRoles = getZonesForUserRoles;
    }

}
