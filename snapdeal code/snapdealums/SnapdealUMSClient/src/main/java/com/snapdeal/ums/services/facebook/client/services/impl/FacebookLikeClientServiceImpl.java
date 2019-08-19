package com.snapdeal.ums.services.facebook.client.services.impl;

import javax.annotation.PostConstruct;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.ext.facebook.FacebookLikesRequest;
import com.snapdeal.ums.ext.facebook.FacebookLikesResponse;
import com.snapdeal.ums.services.facebook.client.services.IFacebookLikeClientService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("FacebookLikeClientService")
public class FacebookLikeClientServiceImpl implements IFacebookLikeClientService {

    private final static String CLIENT_SERVICE_URL = "/facebook";

    private String              webServiceURL;

    @Autowired
    private IUMSClientService   umsClientService;

    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(FacebookLikeClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/facebook/", "facebooklikeservice.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    public FacebookLikesResponse getUserFacebookLikesBySDId(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserFacebookLikesBySDId";
            response = (FacebookLikesResponse) transportService.executeRequest(url, request, null, FacebookLikesResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public FacebookLikesResponse getUserFacebookLikesByFBId(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserFacebookLikesByFBId";
            response = (FacebookLikesResponse) transportService.executeRequest(url, request, null, FacebookLikesResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public FacebookLikesResponse getUserFacebookLikesByEmail(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getUserFacebookLikesByEmail";
            response = (FacebookLikesResponse) transportService.executeRequest(url, request, null, FacebookLikesResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public FacebookLikesResponse addFaceBookLike(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addFaceBookLike";
            response = (FacebookLikesResponse) transportService.executeRequest(url, request, null, FacebookLikesResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public FacebookLikesResponse updateFacebookLike(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateFacebookLike";
            response = (FacebookLikesResponse) transportService.executeRequest(url, request, null, FacebookLikesResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

}
