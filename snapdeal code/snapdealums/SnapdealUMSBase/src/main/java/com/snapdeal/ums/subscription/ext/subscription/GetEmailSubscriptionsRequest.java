
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailSubscriptionsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -5383930349959143952L;
    @Tag(3)
    private String email;

    public GetEmailSubscriptionsRequest() {
    }
    
    public GetEmailSubscriptionsRequest(String email) {
        super();
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
