
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberDetailSRO;

public class CreateEmailSubscriberDetailResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -7398120403983453622L;
    @Tag(5)
    private EmailSubscriberDetailSRO createEmailSubscriberDetail;

    public CreateEmailSubscriberDetailResponse() {
    }

    public CreateEmailSubscriberDetailResponse(EmailSubscriberDetailSRO createEmailSubscriberDetail) {
        super();
        this.createEmailSubscriberDetail = createEmailSubscriberDetail;
    }

    public EmailSubscriberDetailSRO getCreateEmailSubscriberDetail() {
        return createEmailSubscriberDetail;
    }

    public void setCreateEmailSubscriberDetail(EmailSubscriberDetailSRO createEmailSubscriberDetail) {
        this.createEmailSubscriberDetail = createEmailSubscriberDetail;
    }

}
