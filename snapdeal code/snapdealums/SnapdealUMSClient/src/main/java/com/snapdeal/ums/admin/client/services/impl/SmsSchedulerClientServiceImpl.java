package com.snapdeal.ums.admin.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.admin.client.services.ISmsSchedulerClientService;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerByIdRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerByIdResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListRequest2;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest2;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest3;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest4;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.PersistRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.PersistResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.UpdateRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.UpdateResponse;
import com.snapdeal.ums.client.services.IUMSClientService;

@Service("SmsSchedulerClientService")
public class SmsSchedulerClientServiceImpl implements ISmsSchedulerClientService {

    private final static String CLIENT_SERVICE_URL = "/admin/smsscheduler";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(SmsSchedulerClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/admin/smsscheduler/", "smsschedulerserver.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    public UpdateResponse update(UpdateRequest request)

    {
        UpdateResponse response = new UpdateResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/update";
            response = (UpdateResponse) transportService.executeRequest(url, request, null, UpdateResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public PersistResponse persist(PersistRequest request)

    {
        PersistResponse response = new PersistResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/persist";
            response = (PersistResponse) transportService.executeRequest(url, request, null, PersistResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetSmsSchedulerByIdResponse getSmsSchedulerById(GetSmsSchedulerByIdRequest request)

    {
        GetSmsSchedulerByIdResponse response = new GetSmsSchedulerByIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSmsSchedulerById";
            response = (GetSmsSchedulerByIdResponse) transportService.executeRequest(url, request, null, GetSmsSchedulerByIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetSmsSchedulerListResponse getSmsSchedulerList(GetSmsSchedulerListRequest request)

    {
        GetSmsSchedulerListResponse response = new GetSmsSchedulerListResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSmsSchedulerList";
            response = (GetSmsSchedulerListResponse) transportService.executeRequest(url, request, null, GetSmsSchedulerListResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetSmsSchedulerListResponse getSmsSchedulerList(GetSmsSchedulerListRequest2 request)

    {
        GetSmsSchedulerListResponse response = new GetSmsSchedulerListResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSmsSchedulerList2";
            response = (GetSmsSchedulerListResponse) transportService.executeRequest(url, request, null, GetSmsSchedulerListResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest request)

    {
        GetSmsSchedulerResponse response = new GetSmsSchedulerResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSmsScheduler";
            response = (GetSmsSchedulerResponse) transportService.executeRequest(url, request, null, GetSmsSchedulerResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest2 request)

    {
        GetSmsSchedulerResponse response = new GetSmsSchedulerResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSmsScheduler2";
            response = (GetSmsSchedulerResponse) transportService.executeRequest(url, request, null, GetSmsSchedulerResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest3 request)

    {
        GetSmsSchedulerResponse response = new GetSmsSchedulerResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSmsScheduler3";
            response = (GetSmsSchedulerResponse) transportService.executeRequest(url, request, null, GetSmsSchedulerResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest4 request)

    {
        GetSmsSchedulerResponse response = new GetSmsSchedulerResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSmsScheduler4";
            response = (GetSmsSchedulerResponse) transportService.executeRequest(url, request, null, GetSmsSchedulerResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

}
