
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddEmailSubscriberRequest4
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 783115079830198185L;
    @Tag(3)
    private String email;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String subscriptionPage;
    @Tag(6)
    private boolean overrideUnsubscription;
    @Tag(7)
    private boolean isActive;
    @Tag(8)
    private String affiliateTrackingCode;
    @Tag(9)
    private String trackingUtmSource;

    public AddEmailSubscriberRequest4() {
    }
    
    public AddEmailSubscriberRequest4(String email, int zoneId, String subscriptionPage, boolean overrideUnsubscription, boolean isActive, String affiliateTrackingCode, String trackingUtmSource) {
        super();
        this.email = email;
        this.zoneId = zoneId;
        this.subscriptionPage = subscriptionPage;
        this.overrideUnsubscription = overrideUnsubscription;
        this.isActive = isActive;
        this.affiliateTrackingCode = affiliateTrackingCode;
        this.trackingUtmSource = trackingUtmSource;
    }
    
    public String getTrackingUtmSource() {
        return trackingUtmSource;
    }

    public void setTrackingUtmSource(String trackingUtmSource) {
        this.trackingUtmSource = trackingUtmSource;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getAffiliateTrackingCode() {
        return affiliateTrackingCode;
    }

    public void setAffiliateTrackingCode(String affiliateTrackingCode) {
        this.affiliateTrackingCode = affiliateTrackingCode;
    }
}
