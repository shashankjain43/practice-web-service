
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailSubscriberDetailByEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -4609307368185116319L;
    @Tag(3)
    private String email;

    public GetEmailSubscriberDetailByEmailRequest() {
    }
    
    public GetEmailSubscriberDetailByEmailRequest(String email) {
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
