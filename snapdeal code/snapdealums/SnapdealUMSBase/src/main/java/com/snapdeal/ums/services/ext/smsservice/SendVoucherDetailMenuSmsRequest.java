
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendVoucherDetailMenuSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 5104749651094001588L;
    @Tag(3)
    private Integer suborder;

    
    public SendVoucherDetailMenuSmsRequest(Integer suborder) {
        super();
        this.suborder = suborder;
    }

    public SendVoucherDetailMenuSmsRequest() {
    }

    public Integer getSuborder() {
        return suborder;
    }

    public void setSuborder(Integer suborder) {
        this.suborder = suborder;
    }

}
