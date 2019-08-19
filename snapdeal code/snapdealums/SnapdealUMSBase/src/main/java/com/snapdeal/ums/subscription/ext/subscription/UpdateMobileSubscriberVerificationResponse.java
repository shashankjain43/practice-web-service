
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class UpdateMobileSubscriberVerificationResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -5046990472015284679L;
    @Tag(5)
    private boolean updateMobileSubscriberVerification;

    public UpdateMobileSubscriberVerificationResponse() {
    }

    public UpdateMobileSubscriberVerificationResponse(boolean updateMobileSubscriberVerification) {
        super();
        this.updateMobileSubscriberVerification = updateMobileSubscriberVerification;
    }

    public boolean getUpdateMobileSubscriberVerification() {
        return updateMobileSubscriberVerification;
    }

    public void setUpdateMobileSubscriberVerification(boolean updateMobileSubscriberVerification) {
        this.updateMobileSubscriberVerification = updateMobileSubscriberVerification;
    }

}
