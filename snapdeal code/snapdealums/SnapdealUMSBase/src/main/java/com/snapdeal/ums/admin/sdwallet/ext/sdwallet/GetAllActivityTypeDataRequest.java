package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetAllActivityTypeDataRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = -5422692146163579972L;

    @Tag(7)
    private Integer           activityId;

    @Tag(8)
    private String            activityCode;

    public GetAllActivityTypeDataRequest() {

    }

    public GetAllActivityTypeDataRequest(Integer activityId) {
        this.activityId = activityId;
    }

    public GetAllActivityTypeDataRequest(String activityCode) {
        this.activityCode = activityCode;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getActivityCode() {
        return activityCode;
    }

}
