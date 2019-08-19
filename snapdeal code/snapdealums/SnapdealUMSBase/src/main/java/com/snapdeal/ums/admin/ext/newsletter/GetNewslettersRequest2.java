
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewslettersRequest2
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -143704467546117921L;
	@Tag(3)
    private String cityId;
    @Tag(4)
    private Date date;
    @Tag(5)
    private String state;

    public GetNewslettersRequest2() {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public GetNewslettersRequest2(String cityId, Date date, String state) {
        this.cityId = cityId;
        this.date = date;
        this.state = state;
    }

}
