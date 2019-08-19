
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.CustomerQuerySRO;

public class SendCustomerQueryEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6740346638051549674L;
	@Tag(3)
    private CustomerQuerySRO customerQuery;

    public SendCustomerQueryEmailRequest() {
    }

    public SendCustomerQueryEmailRequest(CustomerQuerySRO customerQuery) {
        super();
        this.customerQuery = customerQuery;
    }

    public CustomerQuerySRO getCustomerQuery() {
        return customerQuery;
    }

    public void setCustomerQuery(CustomerQuerySRO customerQuery) {
        this.customerQuery = customerQuery;
    }

}
