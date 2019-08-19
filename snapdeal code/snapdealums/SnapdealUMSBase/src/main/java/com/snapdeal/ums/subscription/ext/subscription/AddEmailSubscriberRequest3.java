
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddEmailSubscriberRequest3
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -9011178365259629427L;
    @Tag(3)
    private String email;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String subscriptionPage;

    public AddEmailSubscriberRequest3() {
    }
    
    public AddEmailSubscriberRequest3(String email, int zoneId, String subscriptionPage) {
        super();
        this.email = email;
        this.zoneId = zoneId;
        this.subscriptionPage = subscriptionPage;
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

}
