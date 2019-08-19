
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddMobileSubscriberWithPinRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -193782061639597373L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String pin;
    @Tag(6)
    private String channel;
    @Tag(7)
    private String affiliateTrackingCode;
    @Tag(8)
    private String utmTracking;

    public AddMobileSubscriberWithPinRequest() {
    }
    
    
    public AddMobileSubscriberWithPinRequest(String mobile, int zoneId, String pin, String channel,String affiliateTrackingCode,
            String utmTracking) {
        super();
        this.mobile = mobile;
        this.zoneId = zoneId;
        this.pin = pin;
        this.channel = channel;
        this.affiliateTrackingCode = affiliateTrackingCode;
        this.utmTracking = utmTracking;
    }

    
    public String getUtmTracking() {
        return utmTracking;
    }


    public void setUtmTracking(String utmTracking) {
        this.utmTracking = utmTracking;
    }


    public String getAffiliateTrackingCode() {
        return affiliateTrackingCode;
    }


    public void setAffiliateTrackingCode(String affiliateTrackingCode) {
        this.affiliateTrackingCode = affiliateTrackingCode;
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

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
