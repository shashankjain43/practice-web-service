
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberDetailSRO;

public class GetNewSubscriberInDateRangeResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 2411761064467066295L;
    @Tag(5)
    private  List<EmailSubscriberDetailSRO> getNewSubscriberInDateRange = new ArrayList<EmailSubscriberDetailSRO>();

    public GetNewSubscriberInDateRangeResponse() {
    }

    public GetNewSubscriberInDateRangeResponse( List<EmailSubscriberDetailSRO> getNewSubscriberInDateRange) {
        super();
        this.getNewSubscriberInDateRange = getNewSubscriberInDateRange;
    }

    public  List<EmailSubscriberDetailSRO> getNewSubscriberInDateRange() {
        return getNewSubscriberInDateRange;
    }

    public void setNewSubscriberInDateRange( List<EmailSubscriberDetailSRO> getNewSubscriberInDateRange) {
        this.getNewSubscriberInDateRange = getNewSubscriberInDateRange;
    }

}
