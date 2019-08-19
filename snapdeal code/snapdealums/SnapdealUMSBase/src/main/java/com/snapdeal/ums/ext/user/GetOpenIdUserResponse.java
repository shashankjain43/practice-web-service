
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class GetOpenIdUserResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3276738284315519255L;
	@Tag(5)
    private UserSRO getOpenIdUser;

    public GetOpenIdUserResponse() {
    }

    public GetOpenIdUserResponse(UserSRO getOpenIdUser) {
        super();
        this.getOpenIdUser = getOpenIdUser;
    }

    public UserSRO getGetOpenIdUser() {
        return getOpenIdUser;
    }

    public void setopenIdUser(UserSRO getOpenIdUser) {
        this.getOpenIdUser = getOpenIdUser;
    }

}
