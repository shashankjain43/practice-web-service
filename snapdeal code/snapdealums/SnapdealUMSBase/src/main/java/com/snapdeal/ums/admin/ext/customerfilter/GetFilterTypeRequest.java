
package com.snapdeal.ums.admin.ext.customerfilter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;

public class GetFilterTypeRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6051705757939763050L;
	@Tag(3)
    private CustomerFilterSRO customerFilter;

    public GetFilterTypeRequest() {
    }

    public CustomerFilterSRO getCustomerFilter() {
        return customerFilter;
    }

    public void setCustomerFilter(CustomerFilterSRO customerFilter) {
        this.customerFilter = customerFilter;
    }

    public GetFilterTypeRequest(CustomerFilterSRO customerFilter) {
        this.customerFilter = customerFilter;
    }

}
