
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class UnsubscribeEmailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 7981557010936602763L;
    @Tag(3)
    private String email;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String reason;

    public UnsubscribeEmailRequest() {
    }
    
    public UnsubscribeEmailRequest(String email, int zoneId, String reason) {
        super();
        this.email = email;
        this.zoneId = zoneId;
        this.reason = reason;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
