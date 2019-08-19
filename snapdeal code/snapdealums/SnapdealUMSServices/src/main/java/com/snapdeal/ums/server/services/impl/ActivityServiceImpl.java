/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 16-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.core.entity.Activity;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
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
import com.snapdeal.ums.server.services.IActivityService;
import com.snapdeal.ums.server.services.IActivityServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;

@Service("umsActivityService")
public class ActivityServiceImpl implements IActivityService {

    @Autowired
    private IActivityServiceInternal umsActivityServiceInternal;
    @Autowired
    private IUMSConvertorService     umsConvertorService;

    @Deprecated
    @Override
    public ProcessActivityResponse processActivity(ProcessActivityRequest request) {
        ProcessActivityResponse response = new ProcessActivityResponse();
        ActivitySRO activitySRO = request.getActivity();

        if (activitySRO != null) {

            Activity activity = umsConvertorService.getActivityfromSRO(activitySRO);
            umsActivityServiceInternal.processActivity(activity);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("activitySRO can not be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetActivityByUserIdResponse getActivityByUserId(GetActivityByUserIdRequest request) {

        GetActivityByUserIdResponse response = new GetActivityByUserIdResponse();
        Integer userId = request.getUserId();

        if (userId != 0) {
            List<ActivitySRO> activitySROs = new ArrayList<ActivitySRO>();
            List<Activity> activities = umsActivityServiceInternal.getActivityByUserId(userId);
            for (Activity activity : activities) {
                activitySROs.add(umsConvertorService.getActivitySROfromEntity(activity));
            }
            response.setGetActivityByUserId(activitySROs);
            response.setSuccessful(true);

        } else {
            response.setSuccessful(false);
            response.setMessage("userId can not be 0");
        }

        return response;
    }

    @Deprecated
    @Override
    public GetActivityByUserAndActivityTypeResponse getActivityByUserAndActivityType(GetActivityByUserAndActivityTypeRequest request) {

        GetActivityByUserAndActivityTypeResponse response = new GetActivityByUserAndActivityTypeResponse();
        Integer activityTypeId = request.getActivity_type_id();
        UserSRO userSRO = request.getUser();
        if ((userSRO != null) && (activityTypeId != 0)) {
            List<ActivitySRO> activitySROs = new ArrayList<ActivitySRO>();
            User user = umsConvertorService.getUserEntityFromSRO(userSRO);
            List<Activity> activities = umsActivityServiceInternal.getActivityByUserAndActivityType(user, activityTypeId);
            for (Activity activity : activities)
                activitySROs.add(umsConvertorService.getActivitySROfromEntity(activity));
            response.setGetActivityByUserAndActivityType(activitySROs);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Null user or Activity type 0");

        }

        return response;
    }

    @Deprecated
    @Override
    public GetActivityByAttributeResponse getActivityByAttribute(GetActivityByAttributeRequest request) {
        GetActivityByAttributeResponse response = new GetActivityByAttributeResponse();
        String attr = request.getAttribute();
        ActivitySRO activitySRO = request.getActivity();
        if ((StringUtils.isNotEmpty(attr)) && (activitySRO != null)) {
            List<ActivitySRO> activitySROs = new ArrayList<ActivitySRO>();
            Activity activity = umsConvertorService.getActivityfromSRO(activitySRO);
            List<Activity> activities = umsActivityServiceInternal.getActivityByAttribute(activity, attr);
            for (Activity act : activities)
                activitySROs.add(umsConvertorService.getActivitySROfromEntity(act));
            response.setGetActivityByAttribute(activitySROs);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("specify proper activit and/or attribute");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetSDCashActivitiesResponse getSDCashActivities(GetSDCashActivitiesRequest request) {

        GetSDCashActivitiesResponse response = new GetSDCashActivitiesResponse();
        int firstResult = request.getFirstResult();
        int maxResults = request.getMaxResults();
        DateRange range = request.getRange();
        if (range != null) {
            List<ActivitySRO> activitySROs = new ArrayList<ActivitySRO>();
            List<Activity> activities = umsActivityServiceInternal.getSDCashActivities(range, firstResult, maxResults);
            for (Activity activity : activities)
                activitySROs.add(umsConvertorService.getActivitySROfromEntity(activity));
            response.setGetSDCashActivities(activitySROs);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("specify proper date range etc.. parameters");

        }
        return response;
    }

    @Deprecated
    @Override
    public GetLastOrderRefundActivityForUserIdResponse getLastOrderRefundActivityForUserId(GetLastOrderRefundActivityForUserIdRequest request) {

        GetLastOrderRefundActivityForUserIdResponse response = new GetLastOrderRefundActivityForUserIdResponse();
        Integer activityTypeId = request.getActivityTypeId();
        Integer userId = request.getUserId();
        if ((userId != 0) && (activityTypeId != 0)) {
            Activity activity = umsActivityServiceInternal.getLastOrderRefundActivityForUserId(userId, activityTypeId);
            response.setGetLastOrderRefundActivityForUserId(umsConvertorService.getActivitySROfromEntity(activity));
        } else {
            response.setSuccessful(false);
            response.setMessage("specify proper userID ,activity ID");
        }
        return response;
    }

}
