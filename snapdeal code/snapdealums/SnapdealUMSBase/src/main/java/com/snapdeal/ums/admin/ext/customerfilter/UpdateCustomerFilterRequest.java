
package com.snapdeal.ums.admin.ext.customerfilter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.customerfilter.CommunicationAdminFilterSRO;

public class UpdateCustomerFilterRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6494558441292562703L;
	@Tag(3)
    private CommunicationAdminFilterSRO filterSRO;

    public UpdateCustomerFilterRequest() {
    }

    public CommunicationAdminFilterSRO getFilterSRO() {
        return filterSRO;
    }

    public void setFilterDTO(CommunicationAdminFilterSRO filterSRO) {
        this.filterSRO = filterSRO;
    }

    public UpdateCustomerFilterRequest(CommunicationAdminFilterSRO filterSRO) {
        this.filterSRO = filterSRO;
    }

}
