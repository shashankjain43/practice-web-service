
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class UnsubscribeMobileResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -753949358033230148L;
    @Tag(5)
    private boolean unsubscribeMobile;

    public UnsubscribeMobileResponse() {
    }

    public UnsubscribeMobileResponse(boolean unsubscribeMobile) {
        super();
        this.unsubscribeMobile = unsubscribeMobile;
    }

    public boolean getUnsubscribeMobile() {
        return unsubscribeMobile;
    }

    public void setUnsubscribeMobile(boolean unsubscribeMobile) {
        this.unsubscribeMobile = unsubscribeMobile;
    }

}
