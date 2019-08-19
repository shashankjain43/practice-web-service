
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class UpdateDNDStatusRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -5932105616034050284L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private boolean dndStatus;

    public UpdateDNDStatusRequest() {
    }   
    

    public UpdateDNDStatusRequest(String mobile, boolean dndStatus) {
        super();
        this.mobile = mobile;
        this.dndStatus = dndStatus;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean getDndStatus() {
        return dndStatus;
    }

    public void setDndStatus(boolean dndStatus) {
        this.dndStatus = dndStatus;
    }

}
