
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddMobileSubscriberRequest4
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7237777837254034226L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String subscriptionPage;
    @Tag(6)
    private boolean overrideUnsubscription;
    @Tag(7)
    private boolean isSubscribed;
    @Tag(8)
    private String affiliateTrackingCode;
    @Tag(9)
    private String trackingUtmSource;

    public AddMobileSubscriberRequest4() {
    }
    
    public AddMobileSubscriberRequest4(String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription, boolean isSubscribed, String affiliateTrackingCode,  String trackingUtmSource) {
        super();
        this.mobile = mobile;
        this.zoneId = zoneId;
        this.subscriptionPage = subscriptionPage;
        this.overrideUnsubscription = overrideUnsubscription;
        this.isSubscribed = isSubscribed;
        this.affiliateTrackingCode = affiliateTrackingCode;
        this.trackingUtmSource = trackingUtmSource;
    }
    
    
    public String getTrackingUtmSource() {
        return trackingUtmSource;
    }

    public void setTrackingUtmSource(String trackingUtmSource) {
        this.trackingUtmSource = trackingUtmSource;
    }

    public String getAffiliateTrackingCode() {
        return affiliateTrackingCode;
    }

    public void setAffiliateTrackingCode(String affiliateTrackingCode) {
        this.affiliateTrackingCode = affiliateTrackingCode;
    }

    public void setSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
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

    public boolean getIsSubscribed() {
        return isSubscribed;
    }

    public void setIsSubscribed(boolean isSubscribed) {
        this.isSubscribed = isSubscribed;
    }

}
