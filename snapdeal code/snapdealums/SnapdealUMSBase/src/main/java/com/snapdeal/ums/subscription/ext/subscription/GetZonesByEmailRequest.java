
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetZonesByEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 8224042186321833571L;
    @Tag(3)
    private String email;

    public GetZonesByEmailRequest() {
    }
    
    public GetZonesByEmailRequest(String email) {
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