
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class UnsubscribeEmailResponse2
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -2549640824620103303L;
    @Tag(5)
    private boolean unsubscribeEmail;

    public UnsubscribeEmailResponse2() {
    }

    public UnsubscribeEmailResponse2(boolean unsubscribeEmail) {
        super();
        this.unsubscribeEmail = unsubscribeEmail;
    }

    public boolean isUnsubscribeEmail() {
        return unsubscribeEmail;
    }

    public void setUnsubscribeEmail(boolean unsubscribeEmail) {
        this.unsubscribeEmail = unsubscribeEmail;
    }

}
