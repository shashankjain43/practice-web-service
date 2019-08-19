
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class UpdateEmailSubscriberRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 3096071280068805936L;
    @Tag(3)
    private EmailSubscriberSRO emailSubscriberVerification;

    public UpdateEmailSubscriberRequest() {
    }
    
    public UpdateEmailSubscriberRequest(EmailSubscriberSRO emailSubscriberVerification) {
        super();
        this.emailSubscriberVerification = emailSubscriberVerification;
    }

    public EmailSubscriberSRO getEmailSubscriberVerification() {
        return emailSubscriberVerification;
    }

    public void setEmailSubscriberVerification(EmailSubscriberSRO emailSubscriberVerification) {
        this.emailSubscriberVerification = emailSubscriberVerification;
    }

}
