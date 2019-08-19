
package com.snapdeal.ums.admin.ext.customerfilter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO.FilterDomain;

public class GetCustomerFiltersByDomainRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6971587784545711337L;
	@Tag(3)
    private FilterDomain domain;

    public GetCustomerFiltersByDomainRequest() {
    }

    public FilterDomain getDomain() {
        return domain;
    }

    public void setDomain(FilterDomain domain) {
        this.domain = domain;
    }

    public GetCustomerFiltersByDomainRequest(FilterDomain domain) {
        this.domain = domain;
    }

}
