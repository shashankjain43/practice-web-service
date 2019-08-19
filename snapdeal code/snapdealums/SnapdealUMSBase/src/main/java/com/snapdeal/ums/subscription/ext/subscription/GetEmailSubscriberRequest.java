
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetEmailSubscriberRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 7966721120675572528L;
    @Tag(3)
    private String email;
    @Tag(4)
    private int zone;

    public GetEmailSubscriberRequest() {
    }
    
    public GetEmailSubscriberRequest(String email, int zone) {
        super();
        this.email = email;
        this.zone = zone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

}
