
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendWayBillNumberExhaustionEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -4716362832040107427L;
	@Tag(3)
    private String shippingProviderName;
    @Tag(4)
    private String shippingMethodName;
    @Tag(5)
    private long l;

    public SendWayBillNumberExhaustionEmailRequest() {
    }

    public SendWayBillNumberExhaustionEmailRequest(String shippingProviderName, String shippingMethodName, long l) {
        super();
        this.shippingProviderName = shippingProviderName;
        this.shippingMethodName = shippingMethodName;
        this.l = l;
    }

    public String getShippingProviderName() {
        return shippingProviderName;
    }

    public void setShippingProviderName(String shippingProviderName) {
        this.shippingProviderName = shippingProviderName;
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName;
    }

    public long getL() {
        return l;
    }

    public void setL(long l) {
        this.l = l;
    }

}
