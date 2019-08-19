
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class GetOrCreateVendorUserResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8900826954159114014L;
	@Tag(5)
    private UserSRO getOrCreateVendorUser;

    public GetOrCreateVendorUserResponse() {
    }

    public GetOrCreateVendorUserResponse(UserSRO getOrCreateVendorUser) {
        super();
        this.getOrCreateVendorUser = getOrCreateVendorUser;
    }

    public UserSRO getGetOrCreateVendorUser() {
        return getOrCreateVendorUser;
    }

    public void setVendorUser(UserSRO getOrCreateVendorUser) {
        this.getOrCreateVendorUser = getOrCreateVendorUser;
    }

}
