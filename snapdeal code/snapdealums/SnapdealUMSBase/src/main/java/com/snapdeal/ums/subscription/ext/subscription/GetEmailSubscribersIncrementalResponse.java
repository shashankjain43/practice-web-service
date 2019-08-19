
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetEmailSubscribersIncrementalResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 4030851838453759788L;
    @Tag(5)
    private List<EmailSubscriberSRO> getEmailSubscribersIncremental = new ArrayList<EmailSubscriberSRO>();

    public GetEmailSubscribersIncrementalResponse() {
    }

    public GetEmailSubscribersIncrementalResponse(List<EmailSubscriberSRO> getEmailSubscribersIncremental) {
        super();
        this.getEmailSubscribersIncremental = getEmailSubscribersIncremental;
    }

    public List<EmailSubscriberSRO> getEmailSubscribersIncremental() {
        return getEmailSubscribersIncremental;
    }

    public void setEmailSubscribersIncremental(List<EmailSubscriberSRO> getEmailSubscribersIncremental) {
        this.getEmailSubscribersIncremental = getEmailSubscribersIncremental;
    }

}
