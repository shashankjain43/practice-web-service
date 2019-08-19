
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberDetailSRO;

public class UpdateEmailSubscriberVerificationRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -7820364849672205609L;
    @Tag(3)
    private EmailSubscriberDetailSRO emailSubscriberVerification;
    
    
    public UpdateEmailSubscriberVerificationRequest(EmailSubscriberDetailSRO emailSubscriberVerification) {
        super();
        this.emailSubscriberVerification = emailSubscriberVerification;
    }

    public UpdateEmailSubscriberVerificationRequest() {
    }

    public EmailSubscriberDetailSRO getEmailSubscriberVerification() {
        return emailSubscriberVerification;
    }

    public void setEmailSubscriberVerification(EmailSubscriberDetailSRO emailSubscriberVerification) {
        this.emailSubscriberVerification = emailSubscriberVerification;
    }

}
