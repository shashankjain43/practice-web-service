
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;

public class GetAllMobileSubscribersIncrementalResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 8440716344282634301L;
    @Tag(5)
    private List<MobileSubscriberSRO> getAllMobileSubscribersIncremental = new ArrayList<MobileSubscriberSRO>();

    public GetAllMobileSubscribersIncrementalResponse() {
    }

    public GetAllMobileSubscribersIncrementalResponse(List<MobileSubscriberSRO> getAllMobileSubscribersIncremental) {
        super();
        this.getAllMobileSubscribersIncremental = getAllMobileSubscribersIncremental;
    }

    public List<MobileSubscriberSRO> getAllMobileSubscribersIncremental() {
        return getAllMobileSubscribersIncremental;
    }

    public void setAllMobileSubscribersIncremental(List<MobileSubscriberSRO> getAllMobileSubscribersIncremental) {
        this.getAllMobileSubscribersIncremental = getAllMobileSubscribersIncremental;
    }

}
