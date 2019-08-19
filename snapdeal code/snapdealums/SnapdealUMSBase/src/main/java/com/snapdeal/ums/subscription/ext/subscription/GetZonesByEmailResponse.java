
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetZonesByEmailResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 3073966125912098659L;
    @Tag(5)
    private List<Integer> getZonesByEmail = new ArrayList<Integer>();

    public GetZonesByEmailResponse() {
    }

    public GetZonesByEmailResponse(List<Integer> getZonesByEmail) {
        super();
        this.getZonesByEmail = getZonesByEmail;
    }

    public List<Integer> getGetZonesByEmail() {
        return getZonesByEmail;
    }

    public void setGetZonesByEmail(List<Integer> getZonesByEmail) {
        this.getZonesByEmail = getZonesByEmail;
    }

}
