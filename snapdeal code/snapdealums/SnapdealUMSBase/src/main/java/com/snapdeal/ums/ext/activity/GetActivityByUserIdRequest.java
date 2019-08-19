
package com.snapdeal.ums.ext.activity;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetActivityByUserIdRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1776571764426115983L;
	@Tag(3)
    private int userId;

    public GetActivityByUserIdRequest() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public GetActivityByUserIdRequest(int userId) {
        this.userId = userId;
    }

}
