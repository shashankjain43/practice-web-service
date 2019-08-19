
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendVoucherEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7362266997894673566L;
	@Tag(3)
    private Integer suborderId;

    public SendVoucherEmailRequest() {
    }
    

    @Override
    public String toString() {
        return "SendVoucherEmailRequest [suborder=" + suborderId + "]";
    }


    public SendVoucherEmailRequest(Integer suborderId) {
        super();
        this.suborderId = suborderId;
    }


    public Integer getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }

}
