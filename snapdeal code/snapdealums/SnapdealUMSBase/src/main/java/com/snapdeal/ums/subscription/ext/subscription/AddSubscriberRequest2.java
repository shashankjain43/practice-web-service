
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddSubscriberRequest2
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -8444910914465893102L;
    @Tag(3)
    private String emailId;
    @Tag(4)
    private String mobile;
    @Tag(5)
    private int zoneId;
    @Tag(6)
    private String subscriptionPage;
    @Tag(7)
    private boolean overrideUnsubscription;
    @Tag(8)
    private boolean isSubscribed;
    @Tag(9)
    private String affiliateTrackingCode;
    @Tag(10)
    private String utmtrackingSource;
    public AddSubscriberRequest2() {
    }
    
    public AddSubscriberRequest2(String emailId, String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription, boolean isActive, String affiliateTrackingCode,
                String utmtrackingSource) {
        super();
        this.emailId = emailId;
        this.mobile = mobile;
        this.zoneId = zoneId;
        this.subscriptionPage = subscriptionPage;
        this.overrideUnsubscription = overrideUnsubscription;
        this.isSubscribed = isActive;
        this.affiliateTrackingCode = affiliateTrackingCode;
        this.utmtrackingSource = utmtrackingSource;
    }
    
    
    public String getUtmtrackingSource() {
        return utmtrackingSource;
    }

    public void setUtmtrackingSource(String utmtrackingSource) {
        this.utmtrackingSource = utmtrackingSource;
    }

    public String getAffiliateTrackingCode() {
        return affiliateTrackingCode;
    }

    public void setAffiliateTrackingCode(String affiliateTrackingCode) {
        this.affiliateTrackingCode = affiliateTrackingCode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
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

    public boolean getIsActive() {
        return isSubscribed;
    }

    public void setIsActive(boolean isActive) {
        this.isSubscribed = isActive;
    }

}
