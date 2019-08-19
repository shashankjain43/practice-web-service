
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetEmailSubscriptionsResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -1059334479639206650L;
    @Tag(5)
    private List<EmailSubscriberSRO> getEmailSubscriptions = new ArrayList<EmailSubscriberSRO>();

    public GetEmailSubscriptionsResponse() {
    }

    public GetEmailSubscriptionsResponse(List<EmailSubscriberSRO> getEmailSubscriptions) {
        super();
        this.getEmailSubscriptions = getEmailSubscriptions;
    }

    public List<EmailSubscriberSRO> getGetEmailSubscriptions() {
        return getEmailSubscriptions;
    }

    public void setEmailSubscriptions(List<EmailSubscriberSRO> getEmailSubscriptions) {
        this.getEmailSubscriptions = getEmailSubscriptions;
    }

}
