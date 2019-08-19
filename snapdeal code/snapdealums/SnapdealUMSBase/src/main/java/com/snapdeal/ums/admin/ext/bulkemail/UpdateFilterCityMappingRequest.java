
package com.snapdeal.ums.admin.ext.bulkemail;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.bulkemail.ESPFilterCityMappingSRO;

public class UpdateFilterCityMappingRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1135935345505939655L;
	@Tag(3)
    private ESPFilterCityMappingSRO filterCityMapping;

    public UpdateFilterCityMappingRequest() {
    }

    public ESPFilterCityMappingSRO getFilterCityMapping() {
        return filterCityMapping;
    }

    public void setFilterCityMapping(ESPFilterCityMappingSRO filterCityMapping) {
        this.filterCityMapping = filterCityMapping;
    }

    public UpdateFilterCityMappingRequest(ESPFilterCityMappingSRO filterCityMapping) {
        this.filterCityMapping = filterCityMapping;
    }

}
