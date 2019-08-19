
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class UnsubscribeMobileRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 1684633684803922241L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String reason;

    public UnsubscribeMobileRequest() {
    }
    

    public UnsubscribeMobileRequest(String mobile, int zoneId, String reason) {
        super();
        this.mobile = mobile;
        this.zoneId = zoneId;
        this.reason = reason;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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
