
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class UnsububscribeEmailFromZonesRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 814070940452253446L;
    @Tag(3)
    private String email;
    @Tag(4)
    private List<Integer> zoneIds;
    @Tag(5)
    private String reason;
    @Tag(6)
    private String suggestion;

    public UnsububscribeEmailFromZonesRequest() {
    }
    
    public UnsububscribeEmailFromZonesRequest(String email, List<Integer> zoneIds, String reason, String suggestion) {
        super();
        this.email = email;
        this.zoneIds = zoneIds;
        this.reason = reason;
        this.suggestion = suggestion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getZoneIds() {
        return zoneIds;
    }

    public void setZoneIds(List<Integer> zoneIds) {
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
