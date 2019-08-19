
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class GetUserByIdResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1305054839161024805L;
	@Tag(5)
    private UserSRO getUserById;

    public GetUserByIdResponse() {
    }

    public GetUserByIdResponse(UserSRO getUserById) {
        super();
        this.getUserById = getUserById;
    }

    public UserSRO getGetUserById() {
        return getUserById;
    }

    public void setUserById(UserSRO getUserById) {
        this.getUserById = getUserById;
    }

}
