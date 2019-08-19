
package com.snapdeal.ums.client.services;

import com.snapdeal.ums.ext.activity.GetActivityByAttributeRequest;
import com.snapdeal.ums.ext.activity.GetActivityByAttributeResponse;
import com.snapdeal.ums.ext.activity.GetActivityByUserAndActivityTypeRequest;
import com.snapdeal.ums.ext.activity.GetActivityByUserAndActivityTypeResponse;
import com.snapdeal.ums.ext.activity.GetActivityByUserIdRequest;
import com.snapdeal.ums.ext.activity.GetActivityByUserIdResponse;
import com.snapdeal.ums.ext.activity.GetLastOrderRefundActivityForUserIdRequest;
import com.snapdeal.ums.ext.activity.GetLastOrderRefundActivityForUserIdResponse;
import com.snapdeal.ums.ext.activity.GetSDCashActivitiesRequest;
import com.snapdeal.ums.ext.activity.GetSDCashActivitiesResponse;
import com.snapdeal.ums.ext.activity.ProcessActivityRequest;
import com.snapdeal.ums.ext.activity.ProcessActivityResponse;

public interface IActivityClientService {


    public ProcessActivityResponse processActivity(ProcessActivityRequest request);

    @Deprecated
    public GetActivityByUserIdResponse getActivityByUserId(GetActivityByUserIdRequest request);

    @Deprecated
    public GetActivityByUserAndActivityTypeResponse getActivityByUserAndActivityType(GetActivityByUserAndActivityTypeRequest request);

    @Deprecated
    public GetActivityByAttributeResponse getActivityByAttribute(GetActivityByAttributeRequest request);

    @Deprecated
    public GetSDCashActivitiesResponse getSDCashActivities(GetSDCashActivitiesRequest request);

    @Deprecated
    public GetLastOrderRefundActivityForUserIdResponse getLastOrderRefundActivityForUserId(GetLastOrderRefundActivityForUserIdRequest request);

}
