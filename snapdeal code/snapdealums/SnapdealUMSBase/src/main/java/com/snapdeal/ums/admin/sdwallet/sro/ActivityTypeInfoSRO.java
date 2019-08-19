/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 21-Dec-2012
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.sro;

import java.io.Serializable;
import java.util.List;

import com.dyuproject.protostuff.Tag;

public class ActivityTypeInfoSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6763251699925387333L;
    @Tag(1)
    private List<ActivityTypeSRO> activityTypeSRO;

    public List<ActivityTypeSRO> getActivityTypeSRO() {
        return activityTypeSRO;
    }

    public void setActivityTypeSRO(List<ActivityTypeSRO> activityTypeSRO) {
        this.activityTypeSRO = activityTypeSRO;
    }
}
