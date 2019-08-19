package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.admin.sdwallet.sro.ActivityTypeInfoSRO;
import com.snapdeal.ums.admin.sdwallet.sro.ActivityTypeSRO;

public class GetAllActivityTypeDataResponse extends ServiceResponse {

    private static final long   serialVersionUID = 202436231393973703L;

    @Tag(5)
    private ActivityTypeInfoSRO activityTypeInfoSRO;
    
    @Tag(6)
    private ActivityTypeSRO activityTypeSRO;

    public GetAllActivityTypeDataResponse() {
    }

    public ActivityTypeInfoSRO getActivityTypeInfoSRO() {
        return activityTypeInfoSRO;
    }

    public void setActivityTypeInfoSRO(ActivityTypeInfoSRO activityTypeInfoSRO) {
        this.activityTypeInfoSRO = activityTypeInfoSRO;
    }

    public GetAllActivityTypeDataResponse(ActivityTypeInfoSRO activityTypeInfoSRO) {
        super();
        this.activityTypeInfoSRO = activityTypeInfoSRO;
    }

    public void setActivityTypeSRO(ActivityTypeSRO activityTypeSRO) {
        this.activityTypeSRO = activityTypeSRO;
    }

    public ActivityTypeSRO getActivityTypeSRO() {
        return activityTypeSRO;
    }
}
