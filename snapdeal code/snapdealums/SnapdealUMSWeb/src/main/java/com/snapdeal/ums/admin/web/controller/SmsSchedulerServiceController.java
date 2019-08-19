package com.snapdeal.ums.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
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
import com.snapdeal.ums.admin.server.services.ISmsSchedulerService;

@Controller
@RequestMapping("/service/ums/admin/smsscheduler/")
public class SmsSchedulerServiceController {

    @Autowired
    private ISmsSchedulerService smsSchedulerService;

    @RequestMapping(value = "update", produces = "application/sd-service")
    @ResponseBody
    public UpdateResponse update(@RequestBody UpdateRequest request) throws TransportException {
        UpdateResponse response = smsSchedulerService.update(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "persist", produces = "application/sd-service")
    @ResponseBody
    public PersistResponse persist(@RequestBody PersistRequest request) throws TransportException {
        PersistResponse response = smsSchedulerService.persist(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSmsSchedulerById", produces = "application/sd-service")
    @ResponseBody
    public GetSmsSchedulerByIdResponse getSmsSchedulerById(@RequestBody GetSmsSchedulerByIdRequest request) throws TransportException {
        GetSmsSchedulerByIdResponse response = smsSchedulerService.getSmsSchedulerById(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSmsSchedulerList", produces = "application/sd-service")
    @ResponseBody
    public GetSmsSchedulerListResponse getSmsSchedulerList(@RequestBody GetSmsSchedulerListRequest request) throws TransportException {
        GetSmsSchedulerListResponse response = smsSchedulerService.getSmsSchedulerList(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSmsSchedulerList2", produces = "application/sd-service")
    @ResponseBody
    public GetSmsSchedulerListResponse getSmsSchedulerList(@RequestBody GetSmsSchedulerListRequest2 request) throws TransportException {
        GetSmsSchedulerListResponse response = smsSchedulerService.getSmsSchedulerList(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSmsScheduler", produces = "application/sd-service")
    @ResponseBody
    public GetSmsSchedulerResponse getSmsScheduler(@RequestBody GetSmsSchedulerRequest request) throws TransportException {
        GetSmsSchedulerResponse response = smsSchedulerService.getSmsScheduler(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSmsScheduler2", produces = "application/sd-service")
    @ResponseBody
    public GetSmsSchedulerResponse getSmsScheduler(@RequestBody GetSmsSchedulerRequest2 request) throws TransportException {
        GetSmsSchedulerResponse response = smsSchedulerService.getSmsScheduler(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSmsScheduler3", produces = "application/sd-service")
    @ResponseBody
    public GetSmsSchedulerResponse getSmsScheduler(@RequestBody GetSmsSchedulerRequest3 request) throws TransportException {
        GetSmsSchedulerResponse response = smsSchedulerService.getSmsScheduler(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSmsScheduler4", produces = "application/sd-service")
    @ResponseBody
    public GetSmsSchedulerResponse getSmsScheduler(@RequestBody GetSmsSchedulerRequest4 request) throws TransportException {
        GetSmsSchedulerResponse response = smsSchedulerService.getSmsScheduler(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
