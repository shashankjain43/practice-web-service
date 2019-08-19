package com.snapdeal.ums.admin.server.services.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.admin.server.services.client.services.ICSAdminClientService;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserRequest;
import com.snapdeal.ums.admin.server.services.ext.csadmin.CSexecutiveUserResponse;
import com.snapdeal.ums.client.services.IUMSClientService;

@Service("CSAdminClientService")
public class CSAdminClientServiceImpl implements ICSAdminClientService {

    private final static String CLIENT_SERVICE_URL = "/admin/csadmin";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(CSAdminClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/admin/csadmin/", "csadminservice.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }


    public CSexecutiveUserResponse getAllCzentrixUser(CSexecutiveUserRequest request) {
        CSexecutiveUserResponse response = new CSexecutiveUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllCzentrixUser";
            response = (CSexecutiveUserResponse) transportService.executeRequest(url, request, null, CSexecutiveUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    
    public CSexecutiveUserResponse getAllZendeskUser(CSexecutiveUserRequest request) {
        CSexecutiveUserResponse response = new CSexecutiveUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllZendeskUser";
            response = (CSexecutiveUserResponse) transportService.executeRequest(url, request, null, CSexecutiveUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    

}
