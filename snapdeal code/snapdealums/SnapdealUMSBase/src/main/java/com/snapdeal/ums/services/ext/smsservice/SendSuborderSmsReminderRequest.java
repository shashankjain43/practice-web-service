
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendSuborderSmsReminderRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 8477959844079359045L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private Integer suborder;

    
    public SendSuborderSmsReminderRequest(String mobile, Integer suborder) {
        super();
        this.mobile = mobile;
        this.suborder = suborder;
    }

    public SendSuborderSmsReminderRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSuborder() {
        return suborder;
    }

    public void setSuborder(Integer suborder) {
        this.suborder = suborder;
    }

}
