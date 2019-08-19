package com.snapdeal.ums.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserResponse;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CsZentrixUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CsZentrixUserResponse;
import com.snapdeal.ums.admin.server.services.ext.csadmin.ZendeskUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.ZendeskUserResponse;
import com.snapdeal.ums.admin.server.services.server.services.ICSAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/service/ums/admin/csadmin/")
public class CSAdminServiceController {

    @Autowired
    private ICSAdminService cSAdminService;

    @RequestMapping(value = "getCSexecutiveUser", produces = "application/sd-service")
    @ResponseBody
    public CSexecutiveUserResponse getCSexecutiveUser(@RequestBody CSexecutiveUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CSexecutiveUserResponse response = cSAdminService.getCSexecutiveUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getAllCzentrixUser", produces = "application/sd-service")
    @ResponseBody
    public CSexecutiveUserResponse getAllCzentrixUser(@RequestBody CSexecutiveUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CSexecutiveUserResponse response = cSAdminService.getAllCzentrixUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "getAllZendeskUser", produces = "application/sd-service")
    @ResponseBody
    public CSexecutiveUserResponse getAllZendeskUser(@RequestBody CSexecutiveUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CSexecutiveUserResponse response = cSAdminService.getAllZendeskUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    @RequestMapping(value = "getZendeskUser", produces = "application/sd-service")
    @ResponseBody
    public ZendeskUserResponse getZendeskUser(@RequestBody ZendeskUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        ZendeskUserResponse response = cSAdminService.getZendeskUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getCsZentrixIdByUser", produces = "application/sd-service")
    @ResponseBody
    public CsZentrixUserResponse getCsZentrixIdByUser(@RequestBody CsZentrixUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CsZentrixUserResponse response = cSAdminService.getCsZentrixIdByUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "persistCsZentrixId", produces = "application/sd-service")
    @ResponseBody
    public CsZentrixUserResponse persistCsZentrixId(@RequestBody CsZentrixUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CsZentrixUserResponse response = cSAdminService.persistCsZentrixId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateCsZentrixId", produces = "application/sd-service")
    @ResponseBody
    public CsZentrixUserResponse updateCsZentrixId(@RequestBody CsZentrixUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CsZentrixUserResponse response = cSAdminService.updateCsZentrixId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "persistZendeskUser", produces = "application/sd-service")
    @ResponseBody
    public ZendeskUserResponse persistZendeskUser(@RequestBody ZendeskUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        ZendeskUserResponse response = cSAdminService.persistZendeskUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateZendeskUser", produces = "application/sd-service")
    @ResponseBody
    public ZendeskUserResponse updateZendeskUser(@RequestBody ZendeskUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        ZendeskUserResponse response = cSAdminService.updateZendeskUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
