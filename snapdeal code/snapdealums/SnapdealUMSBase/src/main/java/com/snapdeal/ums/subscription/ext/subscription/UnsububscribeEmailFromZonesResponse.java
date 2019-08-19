
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class UnsububscribeEmailFromZonesResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -8425734557056317471L;
    @Tag(5)
    private boolean unsububscribeEmailFromZones;

    public UnsububscribeEmailFromZonesResponse() {
    }

    public UnsububscribeEmailFromZonesResponse(boolean unsububscribeEmailFromZones) {
        super();
        this.unsububscribeEmailFromZones = unsububscribeEmailFromZones;
    }

    public boolean isUnsububscribeEmailFromZones() {
        return unsububscribeEmailFromZones;
    }

    public void setUnsububscribeEmailFromZones(boolean unsububscribeEmailFromZones) {
        this.unsububscribeEmailFromZones = unsububscribeEmailFromZones;
    }

}
