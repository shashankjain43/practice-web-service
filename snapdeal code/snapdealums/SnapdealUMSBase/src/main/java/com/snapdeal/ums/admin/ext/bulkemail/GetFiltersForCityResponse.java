
package com.snapdeal.ums.admin.ext.bulkemail;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.bulkemail.ESPFilterCityMappingSRO;

public class GetFiltersForCityResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6976374021896629339L;
	@Tag(5)
    private  List<ESPFilterCityMappingSRO> getFiltersForCity = new ArrayList<ESPFilterCityMappingSRO>();

    public GetFiltersForCityResponse() {
    }

    public GetFiltersForCityResponse( List<ESPFilterCityMappingSRO> getFiltersForCity) {
        super();
        this.getFiltersForCity = getFiltersForCity;
    }

    public  List<ESPFilterCityMappingSRO> getGetFiltersForCity() {
        return getFiltersForCity;
    }

    public void setGetFiltersForCity( List<ESPFilterCityMappingSRO> getFiltersForCity) {
        this.getFiltersForCity = getFiltersForCity;
    }

}
