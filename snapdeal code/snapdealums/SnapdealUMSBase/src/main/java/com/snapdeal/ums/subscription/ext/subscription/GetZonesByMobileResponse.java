
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetZonesByMobileResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -4033604932568085791L;
    @Tag(5)
    private List<Integer> getZonesByMobile = new ArrayList<Integer>();

    public GetZonesByMobileResponse() {
    }

    public GetZonesByMobileResponse(List<Integer> getZonesByMobile) {
        super();
        this.getZonesByMobile = getZonesByMobile;
    }

    public List<Integer> getZonesByMobile() {
        return getZonesByMobile;
    }

    public void setGetZonesByMobile(List<Integer> getZonesByMobile) {
        this.getZonesByMobile = getZonesByMobile;
    }

}
