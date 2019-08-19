
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class GetUserInformationsByUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3387491710263445352L;
	@Tag(3)
    private UserSRO user;

    public GetUserInformationsByUserRequest() {
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public GetUserInformationsByUserRequest(UserSRO user) {
        this.user = user;
    }

}
