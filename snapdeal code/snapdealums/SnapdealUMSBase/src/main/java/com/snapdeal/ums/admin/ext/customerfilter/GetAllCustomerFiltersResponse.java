
package com.snapdeal.ums.admin.ext.customerfilter;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;

public class GetAllCustomerFiltersResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7975857032890544428L;
	@Tag(5)
    private List<CustomerFilterSRO> getAllCustomerFilters = new ArrayList<CustomerFilterSRO>();

    public GetAllCustomerFiltersResponse() {
    }

    public GetAllCustomerFiltersResponse(List<CustomerFilterSRO> getAllCustomerFilters) {
        super();
        this.getAllCustomerFilters = getAllCustomerFilters;
    }

    public List<CustomerFilterSRO> getGetAllCustomerFilters() {
        return getAllCustomerFilters;
    }

    public void setGetAllCustomerFilters(List<CustomerFilterSRO> getAllCustomerFilters) {
        this.getAllCustomerFilters = getAllCustomerFilters;
    }

}
