/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 09-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.admin.sdwallet.sro.ActivityTypeSRO;

public class ModifySDWalletActivityTypeRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 1101196549221845967L;
    @Tag(7)
    private ActivityTypeSRO   activityTypeSRO;

    public ModifySDWalletActivityTypeRequest() {
    }

    public ActivityTypeSRO getActivityTypeSRO() {
        return activityTypeSRO;
    }

    public void setActivityTypeSRO(ActivityTypeSRO activityTypeSRO) {
        this.activityTypeSRO = activityTypeSRO;
    }
}
