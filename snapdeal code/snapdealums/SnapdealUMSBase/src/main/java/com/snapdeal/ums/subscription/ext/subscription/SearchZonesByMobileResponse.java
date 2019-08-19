
package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.core.dto.ZoneDTO;

public class SearchZonesByMobileResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -3678427994496886228L;
    @Tag(5)
    private  List<ZoneDTO> searchZonesByMobile = new ArrayList<ZoneDTO>();

    public SearchZonesByMobileResponse() {
    }

    public SearchZonesByMobileResponse( List<ZoneDTO> searchZonesByMobile) {
        super();
        this.searchZonesByMobile = searchZonesByMobile;
    }

    public  List<ZoneDTO> getSearchZonesByMobile() {
        return searchZonesByMobile;
    }

    public void setSearchZonesByMobile( List<ZoneDTO> searchZonesByMobile) {
        this.searchZonesByMobile = searchZonesByMobile;
    }

}
