
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class UnsubscribeMobileRequest2
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -3001463458316336156L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String reason;

    public UnsubscribeMobileRequest2() {
    }
    
    public UnsubscribeMobileRequest2(String mobile, String reason) {
        super();
        this.mobile = mobile;
        this.reason = reason;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
