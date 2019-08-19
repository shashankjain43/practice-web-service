
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberDetailSRO;

public class UpdateEmailSubscriberDetailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 719533248282570595L;
    @Tag(3)
    private EmailSubscriberDetailSRO esd;

    public UpdateEmailSubscriberDetailRequest() {
    }
    
    public UpdateEmailSubscriberDetailRequest(EmailSubscriberDetailSRO esd) {
        super();
        this.esd = esd;
    }

    public EmailSubscriberDetailSRO getEsd() {
        return esd;
    }

    public void setEsd(EmailSubscriberDetailSRO esd) {
        this.esd = esd;
    }

}
