
package com.snapdeal.ums.admin.ext.customerfilter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO.FilterDomain;

public class GetCustomerFilterByNameRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8494354443762595275L;
	@Tag(3)
    private String name;
    @Tag(4)
    private FilterDomain domain;

    public GetCustomerFilterByNameRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FilterDomain getDomain() {
        return domain;
    }

    public void setDomain(FilterDomain domain) {
        this.domain = domain;
    }

    public GetCustomerFilterByNameRequest(String name, FilterDomain domain) {
        this.name = name;
        this.domain = domain;
    }

}
