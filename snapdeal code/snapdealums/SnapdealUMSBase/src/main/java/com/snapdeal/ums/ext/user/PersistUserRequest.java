
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class PersistUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4371314051300075088L;
	@Tag(3)
    private UserSRO user;

    public PersistUserRequest() {
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public PersistUserRequest(UserSRO user) {
        this.user = user;
    }

}
