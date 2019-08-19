
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberDetailSRO;

public class GetEmailSubscriberDetailByUIDResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -8355001805447806819L;
    @Tag(5)
    private EmailSubscriberDetailSRO getEmailSubscriberDetailByUID;

    public GetEmailSubscriberDetailByUIDResponse() {
    }

    public GetEmailSubscriberDetailByUIDResponse(EmailSubscriberDetailSRO getEmailSubscriberDetailByUID) {
        super();
        this.getEmailSubscriberDetailByUID = getEmailSubscriberDetailByUID;
    }

    public EmailSubscriberDetailSRO getEmailSubscriberDetailByUID() {
        return getEmailSubscriberDetailByUID;
    }

    public void setEmailSubscriberDetailByUID(EmailSubscriberDetailSRO getEmailSubscriberDetailByUID) {
        this.getEmailSubscriberDetailByUID = getEmailSubscriberDetailByUID;
    }

}
