
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SearchZonesByMobileRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -8567134514521602103L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String pin;

    public SearchZonesByMobileRequest() {
    }
    

    public SearchZonesByMobileRequest(String mobile, String pin) {
        super();
        this.mobile = mobile;
        this.pin = pin;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

}
