
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberDetailSRO;

public class GetNewMobileSubscriberByChannelInDateRangeResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 6457590768750352010L;
    @Tag(5)
    private  List<MobileSubscriberDetailSRO> getNewMobileSubscriberByChannelInDateRange = new ArrayList<MobileSubscriberDetailSRO>();

    public GetNewMobileSubscriberByChannelInDateRangeResponse() {
    }

    public GetNewMobileSubscriberByChannelInDateRangeResponse( List<MobileSubscriberDetailSRO> getNewMobileSubscriberByChannelInDateRange) {
        super();
        this.getNewMobileSubscriberByChannelInDateRange = getNewMobileSubscriberByChannelInDateRange;
    }

    public  List<MobileSubscriberDetailSRO> getNewMobileSubscriberByChannelInDateRange() {
        return getNewMobileSubscriberByChannelInDateRange;
    }

    public void setNewMobileSubscriberByChannelInDateRange( List<MobileSubscriberDetailSRO> getNewMobileSubscriberByChannelInDateRange) {
        this.getNewMobileSubscriberByChannelInDateRange = getNewMobileSubscriberByChannelInDateRange;
    }

}
