
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class IsUserExistsResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8966841487885929800L;
	@Tag(5)
    private boolean isUserExists;

    public IsUserExistsResponse() {
    }

    public IsUserExistsResponse(boolean isUserExists) {
        super();
        this.isUserExists = isUserExists;
    }

    public boolean getIsUserExists() {
        return isUserExists;
    }

    public void setIsUserExists(boolean isUserExists) {
        this.isUserExists = isUserExists;
    }

}
