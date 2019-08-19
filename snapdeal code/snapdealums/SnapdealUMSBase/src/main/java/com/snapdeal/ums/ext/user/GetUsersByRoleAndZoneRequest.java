
package com.snapdeal.ums.ext.user;

import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetUsersByRoleAndZoneRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1977746964009445214L;
	@Tag(3)
    private String role;
    @Tag(4)
    private List<Integer> zones;

    public GetUsersByRoleAndZoneRequest() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Integer> getZones() {
        return zones;
    }

    public void setZones(List<Integer> zones) {
        this.zones = zones;
    }

    public GetUsersByRoleAndZoneRequest(String role, List<Integer> zones) {
        this.role = role;
        this.zones = zones;
    }

}
