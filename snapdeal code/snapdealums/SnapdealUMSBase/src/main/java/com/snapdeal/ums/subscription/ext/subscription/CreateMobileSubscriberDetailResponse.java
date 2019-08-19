
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberDetailSRO;

public class CreateMobileSubscriberDetailResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 7391170533176906836L;
    @Tag(5)
    private MobileSubscriberDetailSRO createMobileSubscriberDetail;

    public CreateMobileSubscriberDetailResponse() {
    }

    public CreateMobileSubscriberDetailResponse(MobileSubscriberDetailSRO createMobileSubscriberDetail) {
        super();
        this.createMobileSubscriberDetail = createMobileSubscriberDetail;
    }

    public MobileSubscriberDetailSRO getMobileSubscriberDetail() {
        return createMobileSubscriberDetail;
    }

    public void setCreateMobileSubscriberDetail(MobileSubscriberDetailSRO createMobileSubscriberDetail) {
        this.createMobileSubscriberDetail = createMobileSubscriberDetail;
    }

}
