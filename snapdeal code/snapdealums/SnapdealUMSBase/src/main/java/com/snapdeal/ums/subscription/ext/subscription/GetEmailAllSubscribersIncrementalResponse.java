
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetEmailAllSubscribersIncrementalResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -1990543830999441096L;
    @Tag(5)
    private List<EmailSubscriberSRO> getEmailAllSubscribersIncremental = new ArrayList<EmailSubscriberSRO>();

    public GetEmailAllSubscribersIncrementalResponse() {
    }

    public GetEmailAllSubscribersIncrementalResponse(List<EmailSubscriberSRO> getEmailAllSubscribersIncremental) {
        super();
        this.getEmailAllSubscribersIncremental = getEmailAllSubscribersIncremental;
    }

    public List<EmailSubscriberSRO> getEmailAllSubscribersIncremental() {
        return getEmailAllSubscribersIncremental;
    }

    public void setEmailAllSubscribersIncremental(List<EmailSubscriberSRO> getEmailAllSubscribersIncremental) {
        this.getEmailAllSubscribersIncremental = getEmailAllSubscribersIncremental;
    }

}
