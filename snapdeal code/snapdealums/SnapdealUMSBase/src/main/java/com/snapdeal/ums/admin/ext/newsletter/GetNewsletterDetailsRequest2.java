
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewsletterDetailsRequest2
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2373590142242474555L;
	@Tag(3)
    private Date date;

    public GetNewsletterDetailsRequest2() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GetNewsletterDetailsRequest2(Date date) {
        this.date = date;
    }

}
