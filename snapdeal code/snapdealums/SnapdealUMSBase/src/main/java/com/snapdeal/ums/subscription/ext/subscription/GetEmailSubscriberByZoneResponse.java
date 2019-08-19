
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;

public class GetEmailSubscriberByZoneResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 6995375881388422842L;
    @Tag(5)
    private List<EmailSubscriberSRO> getEmailSubscriberByZone = new ArrayList<EmailSubscriberSRO>();

    public GetEmailSubscriberByZoneResponse() {
    }

    public GetEmailSubscriberByZoneResponse(List<EmailSubscriberSRO> getEmailSubscriberByZone) {
        super();
        this.getEmailSubscriberByZone = getEmailSubscriberByZone;
    }

    public List<EmailSubscriberSRO> getGetEmailSubscriberByZone() {
        return getEmailSubscriberByZone;
    }

    public void setEmailSubscriberByZone(List<EmailSubscriberSRO> getEmailSubscriberByZone) {
        this.getEmailSubscriberByZone = getEmailSubscriberByZone;
    }

}
