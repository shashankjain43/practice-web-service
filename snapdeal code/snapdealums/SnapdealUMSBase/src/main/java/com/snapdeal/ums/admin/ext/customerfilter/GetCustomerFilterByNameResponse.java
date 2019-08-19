
package com.snapdeal.ums.admin.ext.customerfilter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;

public class GetCustomerFilterByNameResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6366599721131133865L;
	@Tag(5)
    private CustomerFilterSRO getCustomerFilterByName;

    public GetCustomerFilterByNameResponse() {
    }

    public GetCustomerFilterByNameResponse(CustomerFilterSRO getCustomerFilterByName) {
        super();
        this.getCustomerFilterByName = getCustomerFilterByName;
    }

    public CustomerFilterSRO getGetCustomerFilterByName() {
        return getCustomerFilterByName;
    }

    public void setGetCustomerFilterByName(CustomerFilterSRO getCustomerFilterByName) {
        this.getCustomerFilterByName = getCustomerFilterByName;
    }

}
