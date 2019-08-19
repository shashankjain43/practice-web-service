
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetEmailSubscriberResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -7217214670149959651L;
    @Tag(5)
    private EmailSubscriberSRO getEmailSubscriber;

    public GetEmailSubscriberResponse() {
    }

    public GetEmailSubscriberResponse(EmailSubscriberSRO getEmailSubscriber) {
        super();
        this.getEmailSubscriber = getEmailSubscriber;
    }

    public EmailSubscriberSRO getEmailSubscriber() {
        return getEmailSubscriber;
    }

    public void setEmailSubscriber(EmailSubscriberSRO getEmailSubscriber) {
        this.getEmailSubscriber = getEmailSubscriber;
    }

}
