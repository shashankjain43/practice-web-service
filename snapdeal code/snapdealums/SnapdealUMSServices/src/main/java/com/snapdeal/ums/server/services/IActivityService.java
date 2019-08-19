
package com.snapdeal.ums.server.services;

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

public interface IActivityService {


    public ProcessActivityResponse processActivity(ProcessActivityRequest request);

    public GetActivityByUserIdResponse getActivityByUserId(GetActivityByUserIdRequest request);

    public GetActivityByUserAndActivityTypeResponse getActivityByUserAndActivityType(GetActivityByUserAndActivityTypeRequest request);

    public GetActivityByAttributeResponse getActivityByAttribute(GetActivityByAttributeRequest request);

    public GetSDCashActivitiesResponse getSDCashActivities(GetSDCashActivitiesRequest request);

    public GetLastOrderRefundActivityForUserIdResponse getLastOrderRefundActivityForUserId(GetLastOrderRefundActivityForUserIdRequest request);

}
