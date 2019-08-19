
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class IsDNDActiveResponse
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = 3345314329609413122L;
    @Tag(5)
    private boolean isDNDActive;

    public IsDNDActiveResponse() {
    }

    public IsDNDActiveResponse(boolean isDNDActive) {
        super();
        this.isDNDActive = isDNDActive;
    }

    public boolean getIsDNDActive() {
        return isDNDActive;
    }

    public void setIsDNDActive(boolean isDNDActive) {
        this.isDNDActive = isDNDActive;
    }

}
