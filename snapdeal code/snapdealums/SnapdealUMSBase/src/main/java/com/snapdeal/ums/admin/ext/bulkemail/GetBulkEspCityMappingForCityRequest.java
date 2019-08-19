
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetBulkEspCityMappingForCityRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5361859883987597058L;
	@Tag(3)
    private Integer cityId;

    public GetBulkEspCityMappingForCityRequest() {
    }

    public Integer getCity() {
        return cityId;
    }

    public void setCity(Integer city) {
        this.cityId = city;
    }

    public GetBulkEspCityMappingForCityRequest(Integer city) {
        this.cityId = city;
    }

}
