
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.base.utils.DateUtils.DateRange;

public class GetNewMobileSubscriberByChannelInDateRangeRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 1893121639538560020L;
    @Tag(3)
    private com.snapdeal.base.utils.DateUtils.DateRange range;
    @Tag(4)
    private String channel;

    public GetNewMobileSubscriberByChannelInDateRangeRequest() {
    }
    
    public GetNewMobileSubscriberByChannelInDateRangeRequest(DateRange range, String channel) {
        super();
        this.range = range;
        this.channel = channel;
    }

    public com.snapdeal.base.utils.DateUtils.DateRange getRange() {
        return range;
    }

    public void setRange(com.snapdeal.base.utils.DateUtils.DateRange range) {
        this.range = range;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

}
