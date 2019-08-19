
package com.snapdeal.ums.admin.ext.customerfilter;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;

public class GetCustomerFiltersByDomainResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6041499423989306806L;
	@Tag(5)
    private List<CustomerFilterSRO> getCustomerFiltersByDomain = new ArrayList<CustomerFilterSRO>();

    public GetCustomerFiltersByDomainResponse() {
    }


    

    public GetCustomerFiltersByDomainResponse(List<CustomerFilterSRO> getCustomerFiltersByDomain) {
        super();
        this.getCustomerFiltersByDomain = getCustomerFiltersByDomain;
    }

    public List<CustomerFilterSRO> getGetCustomerFiltersByDomain() {
        return getCustomerFiltersByDomain;
    }

    public void setGetCustomerFiltersByDomain(List<CustomerFilterSRO> getCustomerFiltersByDomain) {
        this.getCustomerFiltersByDomain = getCustomerFiltersByDomain;
    }

}
