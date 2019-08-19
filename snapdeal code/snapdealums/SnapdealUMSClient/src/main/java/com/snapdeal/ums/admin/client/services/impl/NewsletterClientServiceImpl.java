package com.snapdeal.ums.admin.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.admin.client.services.INewsletterClientService;
import com.snapdeal.ums.admin.ext.newsletter.GetAllESPRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetAllESPResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetFailedCitiesForNewsletterRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetFailedCitiesForNewsletterResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByIdRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByIdResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByMsgIdRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByMsgIdResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest3;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse3;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingForCityRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingForCityResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersRequest2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersResponse;
import com.snapdeal.ums.admin.ext.newsletter.PersistRequest;
import com.snapdeal.ums.admin.ext.newsletter.PersistRequest2;
import com.snapdeal.ums.admin.ext.newsletter.PersistResponse;
import com.snapdeal.ums.admin.ext.newsletter.PersistResponse2;
import com.snapdeal.ums.admin.ext.newsletter.SetNewsletterEspMappingFailedRequest;
import com.snapdeal.ums.admin.ext.newsletter.SetNewsletterEspMappingFailedResponse;
import com.snapdeal.ums.admin.ext.newsletter.UpdateRequest;
import com.snapdeal.ums.admin.ext.newsletter.UpdateResponse;
import com.snapdeal.ums.client.services.IUMSClientService;

@Service("NewsletterClientService")
public class NewsletterClientServiceImpl implements INewsletterClientService {

    private final static String CLIENT_SERVICE_URL = "/admin/newsletter";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(NewsletterClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/admin/newsletter/", "newsletterserver.");
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

    public PersistResponse2 persist(PersistRequest2 request)

    {
        PersistResponse2 response = new PersistResponse2();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/persist2";
            response = (PersistResponse2) transportService.executeRequest(url, request, null, PersistResponse2.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetNewsletterDetailsResponse getNewsletterDetails(GetNewsletterDetailsRequest request)

    {
        GetNewsletterDetailsResponse response = new GetNewsletterDetailsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletterDetails";
            response = (GetNewsletterDetailsResponse) transportService.executeRequest(url, request, null, GetNewsletterDetailsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetNewsletterDetailsResponse2 getNewsletterDetails(GetNewsletterDetailsRequest2 request)

    {
        GetNewsletterDetailsResponse2 response = new GetNewsletterDetailsResponse2();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletterDetails2";
            response = (GetNewsletterDetailsResponse2) transportService.executeRequest(url, request, null, GetNewsletterDetailsResponse2.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetNewsletterDetailsResponse3 getNewsletterDetails(GetNewsletterDetailsRequest3 request)

    {
        GetNewsletterDetailsResponse3 response = new GetNewsletterDetailsResponse3();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletterDetails3";
            response = (GetNewsletterDetailsResponse3) transportService.executeRequest(url, request, null, GetNewsletterDetailsResponse3.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetNewslettersResponse getNewsletters(GetNewslettersRequest request)

    {
        GetNewslettersResponse response = new GetNewslettersResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletters";
            response = (GetNewslettersResponse) transportService.executeRequest(url, request, null, GetNewslettersResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetNewslettersResponse getNewsletters(GetNewslettersRequest2 request)

    {
        GetNewslettersResponse response = new GetNewslettersResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletters2";
            response = (GetNewslettersResponse) transportService.executeRequest(url, request, null, GetNewslettersResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetNewsletterByIdResponse getNewsletterById(GetNewsletterByIdRequest request)

    {
        GetNewsletterByIdResponse response = new GetNewsletterByIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletterById";
            response = (GetNewsletterByIdResponse) transportService.executeRequest(url, request, null, GetNewsletterByIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public GetNewsletterByMsgIdResponse getNewsletterByMsgId(GetNewsletterByMsgIdRequest request)

    {
        GetNewsletterByMsgIdResponse response = new GetNewsletterByMsgIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletterByMsgId";
            response = (GetNewsletterByMsgIdResponse) transportService.executeRequest(url, request, null, GetNewsletterByMsgIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetNewsletterESPMappingResponse getNewsletterESPMapping(GetNewsletterESPMappingRequest request)

    {
        GetNewsletterESPMappingResponse response = new GetNewsletterESPMappingResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletterESPMapping";
            response = (GetNewsletterESPMappingResponse) transportService.executeRequest(url, request, null, GetNewsletterESPMappingResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetNewsletterESPMappingForCityResponse getNewsletterESPMappingForCity(GetNewsletterESPMappingForCityRequest request)

    {
        GetNewsletterESPMappingForCityResponse response = new GetNewsletterESPMappingForCityResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewsletterESPMappingForCity";
            response = (GetNewsletterESPMappingForCityResponse) transportService.executeRequest(url, request, null, GetNewsletterESPMappingForCityResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetFailedCitiesForNewsletterResponse getFailedCitiesForNewsletter(GetFailedCitiesForNewsletterRequest request) {
        GetFailedCitiesForNewsletterResponse response = new GetFailedCitiesForNewsletterResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getFailedCitiesForNewsletter";
            response = (GetFailedCitiesForNewsletterResponse) transportService.executeRequest(url, request, null, GetFailedCitiesForNewsletterResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public SetNewsletterEspMappingFailedResponse setNewsletterEspMappingFailed(SetNewsletterEspMappingFailedRequest request) {
        SetNewsletterEspMappingFailedResponse response = new SetNewsletterEspMappingFailedResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/setNewsletterEspMappingFailed";
            response = (SetNewsletterEspMappingFailedResponse) transportService.executeRequest(url, request, null, SetNewsletterEspMappingFailedResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public GetAllESPResponse getAllESP(GetAllESPRequest request) {
        GetAllESPResponse response = new GetAllESPResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllESP";
            response = (GetAllESPResponse) transportService.executeRequest(url, request, null, GetAllESPResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

}
