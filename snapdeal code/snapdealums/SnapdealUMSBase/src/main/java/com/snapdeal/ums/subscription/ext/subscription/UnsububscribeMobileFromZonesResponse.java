
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class UnsububscribeMobileFromZonesResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -6486259258297640387L;
    @Tag(5)
    private boolean unsububscribeMobileFromZones;

    public UnsububscribeMobileFromZonesResponse() {
    }

    public UnsububscribeMobileFromZonesResponse(boolean unsububscribeMobileFromZones) {
        super();
        this.unsububscribeMobileFromZones = unsububscribeMobileFromZones;
    }

    public boolean isUnsububscribeMobileFromZones() {
        return unsububscribeMobileFromZones;
    }

    public void setUnsububscribeMobileFromZones(boolean unsububscribeMobileFromZones) {
        this.unsububscribeMobileFromZones = unsububscribeMobileFromZones;
    }

}
