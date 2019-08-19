
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetUserByIdRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6916753994157864523L;
	@Tag(3)
    private int id;

    public GetUserByIdRequest() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GetUserByIdRequest(int id) {
        this.id = id;
    }

}
