
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class UnsubscribeEmailResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -1197634329065978633L;
    @Tag(5)
    private boolean unsubscribeEmail;

    public UnsubscribeEmailResponse() {
    }

    public UnsubscribeEmailResponse(boolean unsubscribeEmail) {
        super();
        this.unsubscribeEmail = unsubscribeEmail;
    }

    public boolean getUnsubscribeEmail() {
        return unsubscribeEmail;
    }

    public void setUnsubscribeEmail(boolean unsubscribeEmail) {
        this.unsubscribeEmail = unsubscribeEmail;
    }

}
