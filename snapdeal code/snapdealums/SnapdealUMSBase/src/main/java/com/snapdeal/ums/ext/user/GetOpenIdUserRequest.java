
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class GetOpenIdUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -777663944358405383L;
	@Tag(3)
    private UserSRO rpxUser;

    public GetOpenIdUserRequest() {
    }

    public UserSRO getRpxUser() {
        return rpxUser;
    }

    public void setRpxUser(UserSRO rpxUser) {
        this.rpxUser = rpxUser;
    }

    public GetOpenIdUserRequest(UserSRO rpxUser) {
        this.rpxUser = rpxUser;
    }

}
