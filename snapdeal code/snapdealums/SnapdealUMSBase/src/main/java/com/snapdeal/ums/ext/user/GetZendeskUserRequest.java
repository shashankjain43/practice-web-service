
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetZendeskUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8769293822391854078L;
	@Tag(3)
    private int userId;

    public GetZendeskUserRequest() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public GetZendeskUserRequest(int userId) {
        this.userId = userId;
    }

}
