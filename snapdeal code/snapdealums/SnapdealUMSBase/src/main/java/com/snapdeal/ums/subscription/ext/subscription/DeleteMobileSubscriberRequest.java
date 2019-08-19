package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class DeleteMobileSubscriberRequest extends ServiceRequest{

    /**
     * 
     */
    private static final long serialVersionUID = 7660692905152673666L;
    /**
     * 
     */
    @Tag(3)
    private String mobile;
    
    

    public DeleteMobileSubscriberRequest() {
        super();
    }

    public DeleteMobileSubscriberRequest(String mobile) {
        super();
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
}
