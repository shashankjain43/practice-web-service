package com.snapdeal.ums.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.client.services.IActivityClientService;
import com.snapdeal.ums.client.services.IUMSClientService;
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

@Service("activityClientService")
public class ActivityClientServiceImpl implements IActivityClientService {

    private final static String CLIENT_SERVICE_URL = "/activity";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(ActivityClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/activity/", "activityServer.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    public ProcessActivityResponse processActivity(ProcessActivityRequest request)

    {
        ProcessActivityResponse response = new ProcessActivityResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/processActivity";
            response = (ProcessActivityResponse) transportService.executeRequest(url, request, null, ProcessActivityResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetActivityByUserIdResponse getActivityByUserId(GetActivityByUserIdRequest request)

    {
        GetActivityByUserIdResponse response = new GetActivityByUserIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getActivityByUserId";
            response = (GetActivityByUserIdResponse) transportService.executeRequest(url, request, null, GetActivityByUserIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetActivityByUserAndActivityTypeResponse getActivityByUserAndActivityType(GetActivityByUserAndActivityTypeRequest request)

    {
        GetActivityByUserAndActivityTypeResponse response = new GetActivityByUserAndActivityTypeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getActivityByUserAndActivityType";
            response = (GetActivityByUserAndActivityTypeResponse) transportService.executeRequest(url, request, null, GetActivityByUserAndActivityTypeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetActivityByAttributeResponse getActivityByAttribute(GetActivityByAttributeRequest request)

    {
        GetActivityByAttributeResponse response = new GetActivityByAttributeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getActivityByAttribute";
            response = (GetActivityByAttributeResponse) transportService.executeRequest(url, request, null, GetActivityByAttributeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetSDCashActivitiesResponse getSDCashActivities(GetSDCashActivitiesRequest request)

    {
        GetSDCashActivitiesResponse response = new GetSDCashActivitiesResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSDCashActivities";
            response = (GetSDCashActivitiesResponse) transportService.executeRequest(url, request, null, GetSDCashActivitiesResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetLastOrderRefundActivityForUserIdResponse getLastOrderRefundActivityForUserId(GetLastOrderRefundActivityForUserIdRequest request)

    {
        GetLastOrderRefundActivityForUserIdResponse response = new GetLastOrderRefundActivityForUserIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getLastOrderRefundActivityForUserId";
            response = (GetLastOrderRefundActivityForUserIdResponse) transportService.executeRequest(url, request, null, GetLastOrderRefundActivityForUserIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

}
