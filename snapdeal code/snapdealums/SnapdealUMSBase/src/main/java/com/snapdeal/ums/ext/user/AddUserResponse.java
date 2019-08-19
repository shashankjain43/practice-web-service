
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class AddUserResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2167669177697137472L;
	@Tag(5)
    private UserSRO userSRO;

    public AddUserResponse() {
    }

    public AddUserResponse(UserSRO userSRO) {
        super();
        this.userSRO = userSRO;
    }

    public UserSRO getAddUser() {
        return userSRO;
    }

    public void setAddUser(UserSRO userSRO) {
        this.userSRO = userSRO;
    }

}
