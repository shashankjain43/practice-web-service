
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailAssociationByEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 1496311837013456508L;
    @Tag(3)
    private String email;

    public GetEmailAssociationByEmailRequest() {
    }
    
    public GetEmailAssociationByEmailRequest(String email) {
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
