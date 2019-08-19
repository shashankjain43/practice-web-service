package com.snapdeal.ums.services.facebook.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.ext.facebook.FacebookUserRequest;
import com.snapdeal.ums.ext.facebook.FacebookUserResponse;
import com.snapdeal.ums.services.facebook.client.services.IFacebookUserClientService;

@Service("FacebookUserClientService")
public class FacebookUserClientServiceImpl implements IFacebookUserClientService {

    private final static String CLIENT_SERVICE_URL = "/facebook";

    private String              webServiceURL;

    @Autowired
    private IUMSClientService   umsClientService;

    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(FacebookUserClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/facebook/", "facebookuserservice.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    public FacebookUserResponse addIfNotExistsFacebookUser(FacebookUserRequest request) {
        FacebookUserResponse response = new FacebookUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addIfNotExistsFacebookUser";
            response = (FacebookUserResponse) transportService.executeRequest(url, request, null, FacebookUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

}
