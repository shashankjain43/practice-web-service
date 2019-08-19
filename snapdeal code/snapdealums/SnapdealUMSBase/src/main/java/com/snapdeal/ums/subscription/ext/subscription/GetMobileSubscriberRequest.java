
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetMobileSubscriberRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -5855555173494710819L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private int zone;

    public GetMobileSubscriberRequest() {
    }
    
    public GetMobileSubscriberRequest(String mobile, int zone) {
        super();
        this.mobile = mobile;
        this.zone = zone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

}
