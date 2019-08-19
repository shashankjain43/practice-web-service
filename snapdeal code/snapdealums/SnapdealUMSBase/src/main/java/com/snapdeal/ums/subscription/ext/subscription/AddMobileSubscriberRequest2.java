
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddMobileSubscriberRequest2
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7450958685745886798L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private int zoneId;
    @Tag(5)
    private String subscriptionPage;

    public AddMobileSubscriberRequest2() {
    }
    

    public AddMobileSubscriberRequest2(String mobile, int zoneId, String subscriptionPage) {
        super();
        this.mobile = mobile;
        this.zoneId = zoneId;
        this.subscriptionPage = subscriptionPage;
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

}
