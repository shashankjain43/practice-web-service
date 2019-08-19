
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class CreateMobileSubscriberRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 1225960507945242950L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String subscriptionPage;
    @Tag(6)
    private boolean overrideUnsubscription;
    @Tag(7)
    private String pin;
    @Tag(8)
    private String affiliateTrackingCode;
    @Tag(9)
    private String utmTracking;
    

    public CreateMobileSubscriberRequest() {
    }
    
    public CreateMobileSubscriberRequest(String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription, String pin,String affiliateTrackingCode,
              String utmTracking) {
        super();
        this.mobile = mobile;
        this.zoneId = zoneId;
        this.subscriptionPage = subscriptionPage;
        this.overrideUnsubscription = overrideUnsubscription;
        this.pin = pin;
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

    public String getSubscriptionPage() {
        return subscriptionPage;
    }

    public void setSubscriptionPage(String subscriptionPage) {
        this.subscriptionPage = subscriptionPage;
    }

    public boolean getOverrideUnsubscription() {
        return overrideUnsubscription;
    }

    public void setOverrideUnsubscription(boolean overrideUnsubscription) {
        this.overrideUnsubscription = overrideUnsubscription;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
