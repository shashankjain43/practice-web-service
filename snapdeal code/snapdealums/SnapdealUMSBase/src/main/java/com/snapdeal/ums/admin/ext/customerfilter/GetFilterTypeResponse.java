
package com.snapdeal.ums.admin.ext.customerfilter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;

public class GetFilterTypeResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1400145964563560760L;
	@Tag(5)
    private CustomerFilterSRO.FilterType getFilterType;

    public GetFilterTypeResponse() {
    }

    public GetFilterTypeResponse(CustomerFilterSRO.FilterType getFilterType) {
        super();
        this.getFilterType = getFilterType;
    }

    public CustomerFilterSRO.FilterType getGetFilterType() {
        return getFilterType;
    }

    public void setGetFilterType(CustomerFilterSRO.FilterType getFilterType) {
        this.getFilterType = getFilterType;
    }

}
