
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetEmailSubscriberByEmailResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 2437661516775046676L;
    @Tag(5)
    private List<EmailSubscriberSRO> getEmailSubscriberByEmail = new ArrayList<EmailSubscriberSRO>();

    public GetEmailSubscriberByEmailResponse() {
    }

    public GetEmailSubscriberByEmailResponse(List<EmailSubscriberSRO> getEmailSubscriberByEmail) {
        super();
        this.getEmailSubscriberByEmail = getEmailSubscriberByEmail;
    }

    public List<EmailSubscriberSRO> getEmailSubscriberByEmail() {
        return getEmailSubscriberByEmail;
    }

    public void setEmailSubscriberByEmail(List<EmailSubscriberSRO> getEmailSubscriberByEmail) {
        this.getEmailSubscriberByEmail = getEmailSubscriberByEmail;
    }

}
