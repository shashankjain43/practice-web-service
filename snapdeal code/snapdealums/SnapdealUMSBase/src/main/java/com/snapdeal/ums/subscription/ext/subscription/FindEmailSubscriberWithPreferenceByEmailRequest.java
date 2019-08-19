
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class FindEmailSubscriberWithPreferenceByEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4225123628256676529L;
    @Tag(3)
    private String email;

    public FindEmailSubscriberWithPreferenceByEmailRequest() {
    }
    

    public FindEmailSubscriberWithPreferenceByEmailRequest(String email) {
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
