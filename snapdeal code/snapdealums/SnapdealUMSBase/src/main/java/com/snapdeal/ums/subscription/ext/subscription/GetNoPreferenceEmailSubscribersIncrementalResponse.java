
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetNoPreferenceEmailSubscribersIncrementalResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -3800787204140801156L;
    @Tag(5)
    private List<EmailSubscriberSRO> getNoPreferenceEmailSubscribersIncremental = new ArrayList<EmailSubscriberSRO>();

    public GetNoPreferenceEmailSubscribersIncrementalResponse() {
    }

    public GetNoPreferenceEmailSubscribersIncrementalResponse(List<EmailSubscriberSRO> getNoPreferenceEmailSubscribersIncremental) {
        super();
        this.getNoPreferenceEmailSubscribersIncremental = getNoPreferenceEmailSubscribersIncremental;
    }

    public List<EmailSubscriberSRO> getEmailSubscribersIncremental() {
        return getNoPreferenceEmailSubscribersIncremental;
    }

    public void setEmailSubscribersIncremental(List<EmailSubscriberSRO> getNoPreferenceEmailSubscribersIncremental) {
        this.getNoPreferenceEmailSubscribersIncremental = getNoPreferenceEmailSubscribersIncremental;
    }

}
