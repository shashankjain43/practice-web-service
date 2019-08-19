
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailMobileAssociationRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -8382346481319158528L;
    @Tag(3)
    private String email;
    @Tag(4)
    private String mobile;

    public GetEmailMobileAssociationRequest() {
    }
    
    public GetEmailMobileAssociationRequest(String email, String mobile) {
        super();
        this.email = email;
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
