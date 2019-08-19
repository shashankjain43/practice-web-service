
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class VerifyUserResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 688996193336339395L;
	@Tag(5)
    private boolean verifyUser;

    public VerifyUserResponse() {
    }

    public VerifyUserResponse(boolean verifyUser) {
        super();
        this.verifyUser = verifyUser;
    }

    public boolean getVerifyUser() {
        return verifyUser;
    }

    public void setVerifyUser(boolean verifyUser) {
        this.verifyUser = verifyUser;
    }

}
