package com.snapdeal.ums.admin.server.services.server.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserResponse;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CsZentrixUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CsZentrixUserResponse;
import com.snapdeal.ums.admin.server.services.ext.csadmin.ZendeskUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.ZendeskUserResponse;
import com.snapdeal.ums.admin.server.services.server.services.ICSAdminService;
import com.snapdeal.ums.admin.server.services.server.services.ICSAdminServiceInternal;
import com.snapdeal.ums.core.sro.user.CsZentrixSRO;
import com.snapdeal.ums.core.sro.user.ZendeskUserSRO;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;

@Service("umscsAdminService")
@Transactional
public class CSAdminServiceImpl implements ICSAdminService {

    @Autowired
    ICSAdminServiceInternal CSAdminServiceInternal;

    @Autowired
    IUMSConvertorService    umsConverterService;

    @Deprecated
    @Override
    public CSexecutiveUserResponse getCSexecutiveUser(CSexecutiveUserRequest request) {
        CSexecutiveUserResponse response = new CSexecutiveUserResponse();
        for (User user : CSAdminServiceInternal.getCSexecutiveUser()) {
            response.getGetCSexecutiveUser().add(umsConverterService.getUserSROWithoutRolesfromEntity(user));
        }
        return response;
    }
    
    @Override
    public CSexecutiveUserResponse getAllCzentrixUser(CSexecutiveUserRequest request) {
        CSexecutiveUserResponse response = new CSexecutiveUserResponse();
        for (User user : CSAdminServiceInternal.getAllCzentrixUser()) {
            response.getGetCSexecutiveUser().add(umsConverterService.getUserSROWithoutRolesfromEntity(user));
        }
        return response;
    }
    
    @Override
    public CSexecutiveUserResponse getAllZendeskUser(CSexecutiveUserRequest request) {
        CSexecutiveUserResponse response = new CSexecutiveUserResponse();
        for (User user : CSAdminServiceInternal.getAllZendeskUser()) {
            response.getGetCSexecutiveUser().add(umsConverterService.getUserSROWithoutRolesfromEntity(user));
        }
        return response;
    }
    
    @Deprecated
    @Override
    public CsZentrixUserResponse getCsZentrixIdByUser(CsZentrixUserRequest request) {
        CsZentrixUserResponse response = new CsZentrixUserResponse();
        if (request.getUserId() > 0) {
            CsZentrixSRO csZentrixSRO = umsConverterService.getCsZentrixSROfromEntity(CSAdminServiceInternal.getCsZentrixIdByUser(request.getUserId()));
            if (csZentrixSRO != null)
                response.setGetCsZentrixIdByUser(csZentrixSRO);
        } else {
            response.setSuccessful(false);
            response.setMessage("Id should be greater than zero");
        }
        return response;
    }

    @Deprecated
    @Override
    public CsZentrixUserResponse persistCsZentrixId(CsZentrixUserRequest request) {
        CsZentrixUserResponse response = new CsZentrixUserResponse();
        if (request.getCs() != null) {
            CSAdminServiceInternal.persistCsZentrixId(umsConverterService.getCsZentrixEntityfromSRO(request.getCs()));
        } else {
            response.setSuccessful(false);
            response.setMessage("CsZentrixSRO field of request can't be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public CsZentrixUserResponse updateCsZentrixId(CsZentrixUserRequest request) {
        CsZentrixUserResponse response = new CsZentrixUserResponse();
        if (request.getCs() != null) {
            CSAdminServiceInternal.updateCsZentrixId(umsConverterService.getCsZentrixEntityfromSRO(request.getCs()));
        } else {
            response.setSuccessful(false);
            response.setMessage("CsZentrixSRO field of request can't be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public ZendeskUserResponse getZendeskUser(ZendeskUserRequest request) {
        ZendeskUserResponse response = new ZendeskUserResponse();
        if (request.getUserId() > 0) {
            ZendeskUserSRO zendeskSRO = umsConverterService.getZendeskUserSROfromEntity(CSAdminServiceInternal.getZendeskUser(request.getUserId()));
            if (zendeskSRO != null)
                response.setGetZendeskUser(zendeskSRO);
        } else {
            response.setSuccessful(false);
            response.setMessage("Id should be greater than zero");
        }
        return response;
    }

    @Deprecated
    @Override
    public ZendeskUserResponse persistZendeskUser(ZendeskUserRequest request) {
        ZendeskUserResponse response = new ZendeskUserResponse();
        if (request.getZendeskUser() != null) {
            CSAdminServiceInternal.persistZendeskUser(umsConverterService.getZendeskUserEntityfromSRO(request.getZendeskUser()));
        } else {
            response.setSuccessful(false);
            response.setMessage("ZendeskUserSRO field of request can't be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public ZendeskUserResponse updateZendeskUser(ZendeskUserRequest request) {
        ZendeskUserResponse response = new ZendeskUserResponse();
        if (request.getZendeskUser() != null) {
            CSAdminServiceInternal.updateZendeskUser(umsConverterService.getZendeskUserEntityfromSRO(request.getZendeskUser()));
        } else {
            response.setSuccessful(false);
            response.setMessage("ZendeskUserSRO field of request can't be null");
        }
        return response;
    }

}
