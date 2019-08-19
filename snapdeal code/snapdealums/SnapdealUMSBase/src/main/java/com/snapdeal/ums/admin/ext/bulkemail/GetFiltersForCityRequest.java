
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetFiltersForCityRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7566691428413850766L;
	@Tag(3)
    private int cityId;
    @Tag(4)
    private int espId;

    public GetFiltersForCityRequest() {
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getEspId() {
        return espId;
    }

    public void setEspId(int espId) {
        this.espId = espId;
    }

    public GetFiltersForCityRequest(int cityId, int espId) {
        this.cityId = cityId;
        this.espId = espId;
    }

}
