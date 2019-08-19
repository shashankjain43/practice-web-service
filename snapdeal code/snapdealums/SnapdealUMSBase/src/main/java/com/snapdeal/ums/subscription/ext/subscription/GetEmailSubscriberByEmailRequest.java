
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailSubscriberByEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7814083289317447951L;
    @Tag(3)
    private String email;

    public GetEmailSubscriberByEmailRequest() {
    }
    

    public GetEmailSubscriberByEmailRequest(String email) {
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
