
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetMobileSubscriberByZoneRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7600309234588770259L;
    @Tag(3)
    private int zoneId;
    @Tag(4)
    private Date startTime;
    @Tag(5)
    private Date endTime;

    public GetMobileSubscriberByZoneRequest() {
    }
    
    public GetMobileSubscriberByZoneRequest(int zoneId, Date startTime, Date endTime) {
        super();
        this.zoneId = zoneId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
