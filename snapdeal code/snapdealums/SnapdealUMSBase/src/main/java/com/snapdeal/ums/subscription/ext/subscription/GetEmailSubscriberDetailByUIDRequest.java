
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailSubscriberDetailByUIDRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 1504193296093029972L;
    @Tag(3)
    private String refCode;

    public GetEmailSubscriberDetailByUIDRequest() {
    }
    
    public GetEmailSubscriberDetailByUIDRequest(String refCode) {
        super();
        this.refCode = refCode;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

}
