
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;

public class GetMobileSubscriberByZoneResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -8684742450183550827L;
    @Tag(5)
    private List<MobileSubscriberSRO> getMobileSubscriberByZone = new ArrayList<MobileSubscriberSRO>();

    public GetMobileSubscriberByZoneResponse() {
    }

    public GetMobileSubscriberByZoneResponse(List<MobileSubscriberSRO> getMobileSubscriberByZone) {
        super();
        this.getMobileSubscriberByZone = getMobileSubscriberByZone;
    }

    public List<MobileSubscriberSRO> getMobileSubscriberByZone() {
        return getMobileSubscriberByZone;
    }

    public void setMobileSubscriberByZone(List<MobileSubscriberSRO> getMobileSubscriberByZone) {
        this.getMobileSubscriberByZone = getMobileSubscriberByZone;
    }

}
