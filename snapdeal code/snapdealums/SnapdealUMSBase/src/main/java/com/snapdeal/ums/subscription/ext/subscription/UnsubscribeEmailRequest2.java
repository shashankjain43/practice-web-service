
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class UnsubscribeEmailRequest2
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -5970394599882358881L;
    @Tag(3)
    private String email;
    @Tag(4)
    private String reason;

    public UnsubscribeEmailRequest2() {
    }
    
    public UnsubscribeEmailRequest2(String email, String reason) {
        super();
        this.email = email;
        this.reason = reason;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
