
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class UnsububscribeMobileFromZonesRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -1854442314714619758L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private  List<Integer> zoneIds;
    @Tag(5)
    private String reason;
    @Tag(6)
    private String suggestion;

    public UnsububscribeMobileFromZonesRequest() {
    }
    
    public UnsububscribeMobileFromZonesRequest(String mobile, List<Integer> zoneIds, String reason, String suggestion) {
        super();
        this.mobile = mobile;
        this.zoneIds = zoneIds;
        this.reason = reason;
        this.suggestion = suggestion;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public  List<Integer> getZoneIds() {
        return zoneIds;
    }

    public void setZoneIds( List<Integer> zoneIds) {
        this.zoneIds = zoneIds;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

}
