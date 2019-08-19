
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class UpdateUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7602573751307965405L;
	@Tag(3)
    private UserSRO user;

    public UpdateUserRequest() {
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public UpdateUserRequest(UserSRO user) {
        this.user = user;
    }

}
