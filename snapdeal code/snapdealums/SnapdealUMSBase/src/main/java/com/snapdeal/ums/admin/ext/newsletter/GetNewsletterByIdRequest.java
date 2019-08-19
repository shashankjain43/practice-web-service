
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewsletterByIdRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6350274982896576277L;
	@Tag(3)
    private Integer id;

    public GetNewsletterByIdRequest() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GetNewsletterByIdRequest(Integer id) {
        this.id = id;
    }

}
