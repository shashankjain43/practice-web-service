
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetMobileSubscribersAndDNDStatusResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -4349243235422347759L;
    @Tag(5)
    private List<Object[]> getMobileSubscribersAndDNDStatus = new ArrayList<Object[]>();

    public GetMobileSubscribersAndDNDStatusResponse() {
    }

    public GetMobileSubscribersAndDNDStatusResponse(List<Object[]> getMobileSubscribersAndDNDStatus) {
        super();
        this.getMobileSubscribersAndDNDStatus = getMobileSubscribersAndDNDStatus;
    }

    public List<Object[]> getMobileSubscribersAndDNDStatus() {
        return getMobileSubscribersAndDNDStatus;
    }

    public void setGetMobileSubscribersAndDNDStatus(List<Object[]> getMobileSubscribersAndDNDStatus) {
        this.getMobileSubscribersAndDNDStatus = getMobileSubscribersAndDNDStatus;
    }

}
