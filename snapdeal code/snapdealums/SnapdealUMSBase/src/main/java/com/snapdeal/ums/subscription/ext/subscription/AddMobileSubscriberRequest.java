
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class AddMobileSubscriberRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2678105050051354291L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private int zoneId;

    public AddMobileSubscriberRequest() {
    }
    

    public AddMobileSubscriberRequest(String mobile, int zoneId) {
        super();
        this.mobile = mobile;
        this.zoneId = zoneId;
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

}
