
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewsletterDetailsRequest3
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5765969121399119127L;
	@Tag(3)
    private Date date;
    @Tag(4)
    private String state;

    public GetNewsletterDetailsRequest3() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public GetNewsletterDetailsRequest3(Date date, String state) {
        this.date = date;
        this.state = state;
    }

    

}
