
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class FindEmailSubscriberWithPreferenceByEmailResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -128643552254798513L;
    @Tag(5)
    private EmailSubscriberSRO findEmailSubscriberWithPreferenceByEmail;

    public FindEmailSubscriberWithPreferenceByEmailResponse() {
    }

    public FindEmailSubscriberWithPreferenceByEmailResponse(EmailSubscriberSRO findEmailSubscriberWithPreferenceByEmail) {
        super();
        this.findEmailSubscriberWithPreferenceByEmail = findEmailSubscriberWithPreferenceByEmail;
    }

    public EmailSubscriberSRO getEmailSubscriberWithPreferenceByEmail() {
        return findEmailSubscriberWithPreferenceByEmail;
    }

    public void setFindEmailSubscriberWithPreferenceByEmail(EmailSubscriberSRO findEmailSubscriberWithPreferenceByEmail) {
        this.findEmailSubscriberWithPreferenceByEmail = findEmailSubscriberWithPreferenceByEmail;
    }

}
