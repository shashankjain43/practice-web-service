
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSubscriberProfileRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2613956700294787751L;
    @Tag(3)
    private String email;

    public GetSubscriberProfileRequest() {
    }
    
    public GetSubscriberProfileRequest(String email) {
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
