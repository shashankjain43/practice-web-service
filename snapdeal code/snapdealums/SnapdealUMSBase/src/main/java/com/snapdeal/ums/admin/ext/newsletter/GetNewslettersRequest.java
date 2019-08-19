
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewslettersRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 934750531788133679L;
	@Tag(3)
    private String cityId;
    @Tag(4)
    private Date date;

    public GetNewslettersRequest() {
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

    public GetNewslettersRequest(String cityId, Date date) {
        this.cityId = cityId;
        this.date = date;
    }

}
