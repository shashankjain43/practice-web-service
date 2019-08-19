
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.user.UserSRO;


public class UpdateUserResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5094938658590100918L;
	@Tag(5)
    private UserSRO updateUser;

    public UpdateUserResponse() {
    }

    public UpdateUserResponse(UserSRO updateUser) {
        super();
        this.updateUser = updateUser;
    }

    public UserSRO getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(UserSRO updateUser) {
        this.updateUser = updateUser;
    }

}
