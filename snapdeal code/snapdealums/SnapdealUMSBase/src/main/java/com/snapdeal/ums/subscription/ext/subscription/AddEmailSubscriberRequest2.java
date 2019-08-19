
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddEmailSubscriberRequest2
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 7331379570813689942L;
    @Tag(3)
    private String email;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String subscriptionPage;
    @Tag(6)
    private boolean overrideUnsubscription;

    public AddEmailSubscriberRequest2() {
    }
    
    public AddEmailSubscriberRequest2(String email, int zoneId, String subscriptionPage, boolean overrideUnsubscription) {
        super();
        this.email = email;
        this.zoneId = zoneId;
        this.subscriptionPage = subscriptionPage;
        this.overrideUnsubscription = overrideUnsubscription;
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

}
