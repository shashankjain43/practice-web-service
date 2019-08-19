
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberDetailSRO;

public class GetEmailSubscriberDetailByEmailResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -3325897257904388553L;
    @Tag(5)
    private EmailSubscriberDetailSRO getEmailSubscriberDetailByEmail;

    public GetEmailSubscriberDetailByEmailResponse() {
    }

    public GetEmailSubscriberDetailByEmailResponse(EmailSubscriberDetailSRO getEmailSubscriberDetailByEmail) {
        super();
        this.getEmailSubscriberDetailByEmail = getEmailSubscriberDetailByEmail;
    }

    public EmailSubscriberDetailSRO getGetEmailSubscriberDetailByEmail() {
        return getEmailSubscriberDetailByEmail;
    }

    public void setGetEmailSubscriberDetailByEmail(EmailSubscriberDetailSRO getEmailSubscriberDetailByEmail) {
        this.getEmailSubscriberDetailByEmail = getEmailSubscriberDetailByEmail;
    }

}
