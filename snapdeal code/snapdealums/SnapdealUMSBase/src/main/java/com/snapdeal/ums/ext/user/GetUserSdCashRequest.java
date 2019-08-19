
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetUserSdCashRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1556107587566316544L;
	@Tag(3)
    private Integer userId;

    public GetUserSdCashRequest() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public GetUserSdCashRequest(Integer userId) {
        this.userId = userId;
    }

}
