
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class CreateUserResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5776893791959289442L;
	@Tag(5)
    private UserSRO createUser;

    public CreateUserResponse() {
    }

    public CreateUserResponse(UserSRO createUser) {
        super();
        this.createUser = createUser;
    }

    public UserSRO getCreateUser() {
        return createUser;
    }

    public void setCreateUser(UserSRO createUser) {
        this.createUser = createUser;
    }

}
