package com.snapdeal.ums.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
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

@Controller
@RequestMapping("/service/ums/activity/")
public class ActivityServiceController {

    @Autowired
    private IActivityService activityService;

    @RequestMapping(value = "processActivity", produces = "application/sd-service")
    @ResponseBody
    public ProcessActivityResponse processActivity(@RequestBody ProcessActivityRequest request) throws TransportException {
        ProcessActivityResponse response = activityService.processActivity(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getActivityByUserId", produces = "application/sd-service")
    @ResponseBody
    public GetActivityByUserIdResponse getActivityByUserId(@RequestBody GetActivityByUserIdRequest request) throws TransportException {
        GetActivityByUserIdResponse response = activityService.getActivityByUserId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getActivityByUserAndActivityType", produces = "application/sd-service")
    @ResponseBody
    public GetActivityByUserAndActivityTypeResponse getActivityByUserAndActivityType(@RequestBody GetActivityByUserAndActivityTypeRequest request) throws TransportException {
        GetActivityByUserAndActivityTypeResponse response = activityService.getActivityByUserAndActivityType(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getActivityByAttribute", produces = "application/sd-service")
    @ResponseBody
    public GetActivityByAttributeResponse getActivityByAttribute(@RequestBody GetActivityByAttributeRequest request) throws TransportException {
        GetActivityByAttributeResponse response = activityService.getActivityByAttribute(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSDCashActivities", produces = "application/sd-service")
    @ResponseBody
    public GetSDCashActivitiesResponse getSDCashActivities(@RequestBody GetSDCashActivitiesRequest request) throws TransportException {
        GetSDCashActivitiesResponse response = activityService.getSDCashActivities(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getLastOrderRefundActivityForUserId", produces = "application/sd-service")
    @ResponseBody
    public GetLastOrderRefundActivityForUserIdResponse getLastOrderRefundActivityForUserId(@RequestBody GetLastOrderRefundActivityForUserIdRequest request)
            throws TransportException {
        GetLastOrderRefundActivityForUserIdResponse response = activityService.getLastOrderRefundActivityForUserId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
