
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class UnsubscribeMobileResponse2
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 7285113530166633205L;
    @Tag(5)
    private boolean unsubscribeMobile;

    public UnsubscribeMobileResponse2() {
    }

    public UnsubscribeMobileResponse2(boolean unsubscribeMobile) {
        super();
        this.unsubscribeMobile = unsubscribeMobile;
    }

    public boolean isUnsubscribeMobile() {
        return unsubscribeMobile;
    }

    public void setUnsubscribeMobile(boolean unsubscribeMobile) {
        this.unsubscribeMobile = unsubscribeMobile;
    }

}
