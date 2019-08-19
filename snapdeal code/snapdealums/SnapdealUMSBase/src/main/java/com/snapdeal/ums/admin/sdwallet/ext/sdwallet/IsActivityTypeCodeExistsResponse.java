/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class IsActivityTypeCodeExistsResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long serialVersionUID = -8324271172497651554L;
    @Tag(6)
    private boolean           isActivityTypeCodeExists;

    public boolean isActivityTypeCodeExists() {
        return isActivityTypeCodeExists;
    }

    public void setActivityTypeCodeExists(boolean isActivityTypeCodeExists) {
        this.isActivityTypeCodeExists = isActivityTypeCodeExists;
    }

    public IsActivityTypeCodeExistsResponse() {
    }

}
