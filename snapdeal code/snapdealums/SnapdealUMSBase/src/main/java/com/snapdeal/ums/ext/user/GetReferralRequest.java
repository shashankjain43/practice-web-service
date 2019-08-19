
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class GetReferralRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4607069275830520407L;
	@Tag(3)
    private UserSRO user;

    public GetReferralRequest() {
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public GetReferralRequest(UserSRO user) {
        this.user = user;
    }

}
