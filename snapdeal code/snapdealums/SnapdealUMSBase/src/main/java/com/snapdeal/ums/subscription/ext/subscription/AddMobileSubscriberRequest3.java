
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddMobileSubscriberRequest3
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 7576130842439055067L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String subscriptionPage;
    @Tag(6)
    private boolean overrideUnsubscription;

    public AddMobileSubscriberRequest3() {
    }
    
    
    public AddMobileSubscriberRequest3(String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription) {
        super();
        this.mobile = mobile;
        this.zoneId = zoneId;
        this.subscriptionPage = subscriptionPage;
        this.overrideUnsubscription = overrideUnsubscription;
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

}
