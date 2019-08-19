
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddEmailSubscriberRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4504330494255357944L;
    @Tag(3)
    private String email;
    @Tag(4)
    private int zoneId;

    public AddEmailSubscriberRequest() {
    }
    
    public AddEmailSubscriberRequest(String email, int zoneId) {
        super();
        this.email = email;
        this.zoneId = zoneId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

}
