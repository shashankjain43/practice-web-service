
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewsletterDetailsRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8008668232694723139L;
	@Tag(3)
    private String cityId;
    @Tag(4)
    private Date date;

    public GetNewsletterDetailsRequest() {
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public GetNewsletterDetailsRequest(String cityId, Date date) {
        this.cityId = cityId;
        this.date = date;
    }

}
