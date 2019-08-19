package com.snapdeal.ums.admin.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.admin.client.services.IBulkEmailClientService;
import com.snapdeal.ums.admin.ext.bulkemail.GetAllEmailBulkEspCityMappingRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetAllEmailBulkEspCityMappingResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetBulkEspCityMappingForCityRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetBulkEspCityMappingForCityResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetFiltersForCityRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetFiltersForCityResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetProfileFieldsForESPRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetProfileFieldsForESPResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsBounceRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsBounceResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsMauRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsMauResponse;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateFilterCityMappingRequest;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateFilterCityMappingResponse;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateRequest;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateResponse;
import com.snapdeal.ums.client.services.IUMSClientService;

@Service("BulkEmailClientService")
public class BulkEmailClientServiceImpl implements IBulkEmailClientService {

    private final static String CLIENT_SERVICE_URL = "/admin/bulkemail";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(BulkEmailClientServiceImpl.class));
	private static final Class Object = null;

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/admin/bulkemail/", "bulkemailServer.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    public UpdateResponse update(UpdateRequest request) {
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

    @Deprecated
    public UpdateFilterCityMappingResponse updateFilterCityMapping(UpdateFilterCityMappingRequest request)

    {
        UpdateFilterCityMappingResponse response = new UpdateFilterCityMappingResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateFilterCityMapping";
            response = (UpdateFilterCityMappingResponse) transportService.executeRequest(url, request, null, UpdateFilterCityMappingResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetFiltersForCityResponse getFiltersForCity(GetFiltersForCityRequest request)

    {
        GetFiltersForCityResponse response = new GetFiltersForCityResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getFiltersForCity";
            response = (GetFiltersForCityResponse) transportService.executeRequest(url, request, null, GetFiltersForCityResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetProfileFieldsForESPResponse getProfileFieldsForESP(GetProfileFieldsForESPRequest request)

    {
        GetProfileFieldsForESPResponse response = new GetProfileFieldsForESPResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getProfileFieldsForESP";
            response = (GetProfileFieldsForESPResponse) transportService.executeRequest(url, request, null, GetProfileFieldsForESPResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetAllEmailBulkEspCityMappingResponse getAllEmailBulkEspCityMapping(GetAllEmailBulkEspCityMappingRequest request)

    {
        GetAllEmailBulkEspCityMappingResponse response = new GetAllEmailBulkEspCityMappingResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllEmailBulkEspCityMapping";
            response = (GetAllEmailBulkEspCityMappingResponse) transportService.executeRequest(url, request, null, GetAllEmailBulkEspCityMappingResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetBulkEspCityMappingForCityResponse getBulkEspCityMappingForCity(GetBulkEspCityMappingForCityRequest request)

    {
        GetBulkEspCityMappingForCityResponse response = new GetBulkEspCityMappingForCityResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getBulkEspCityMappingForCity";
            response = (GetBulkEspCityMappingForCityResponse) transportService.executeRequest(url, request, null, GetBulkEspCityMappingForCityResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetResultsMauResponse getResultsMau(GetResultsMauRequest request)

    {
        GetResultsMauResponse response = new GetResultsMauResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getResultsMau";
            response = (GetResultsMauResponse) transportService.executeRequest(url, request, null, GetResultsMauResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetResultsBounceResponse getResultsBounce(GetResultsBounceRequest request)

    {
        GetResultsBounceResponse response = new GetResultsBounceResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getResultsBounce";
            response = (GetResultsBounceResponse) transportService.executeRequest(url, request, null, GetResultsBounceResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    
    public static void example()
    {
    	BulkEmailClientServiceImpl obj=new BulkEmailClientServiceImpl();
    	Class c = obj.getClass();
    	Class[] nterface=c.getInterfaces();
    	
    	
    	
    }
    
    public static void main(String[] args)
    {
    	
    	example();
    }

}
