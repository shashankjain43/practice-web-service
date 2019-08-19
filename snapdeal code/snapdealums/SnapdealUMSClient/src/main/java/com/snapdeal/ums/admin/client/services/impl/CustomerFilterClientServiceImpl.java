package com.snapdeal.ums.admin.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.admin.client.services.ICustomerFilterClientService;
import com.snapdeal.ums.admin.ext.customerfilter.GetAllCustomerFiltersRequest;
import com.snapdeal.ums.admin.ext.customerfilter.GetAllCustomerFiltersResponse;
import com.snapdeal.ums.admin.ext.customerfilter.GetCustomerFilterByNameRequest;
import com.snapdeal.ums.admin.ext.customerfilter.GetCustomerFilterByNameResponse;
import com.snapdeal.ums.admin.ext.customerfilter.GetCustomerFiltersByDomainRequest;
import com.snapdeal.ums.admin.ext.customerfilter.GetCustomerFiltersByDomainResponse;
import com.snapdeal.ums.admin.ext.customerfilter.GetFilterTypeRequest;
import com.snapdeal.ums.admin.ext.customerfilter.GetFilterTypeResponse;
import com.snapdeal.ums.admin.ext.customerfilter.UpdateCustomerFilterRequest;
import com.snapdeal.ums.admin.ext.customerfilter.UpdateCustomerFilterResponse;
import com.snapdeal.ums.client.services.IUMSClientService;

@Service("CustomerFilterClientService")
public class CustomerFilterClientServiceImpl implements ICustomerFilterClientService {

    private final static String CLIENT_SERVICE_URL = "/admin/customerfilter";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(CustomerFilterClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/admin/customerfilter/", "customerfilterserver.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    @Deprecated
    public GetAllCustomerFiltersResponse getAllCustomerFilters(GetAllCustomerFiltersRequest request)

    {
        GetAllCustomerFiltersResponse response = new GetAllCustomerFiltersResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllCustomerFilters";
            response = (GetAllCustomerFiltersResponse) transportService.executeRequest(url, request, null, GetAllCustomerFiltersResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetCustomerFiltersByDomainResponse getCustomerFiltersByDomain(GetCustomerFiltersByDomainRequest request)

    {
        GetCustomerFiltersByDomainResponse response = new GetCustomerFiltersByDomainResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getCustomerFiltersByDomain";
            response = (GetCustomerFiltersByDomainResponse) transportService.executeRequest(url, request, null, GetCustomerFiltersByDomainResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetCustomerFilterByNameResponse getCustomerFilterByName(GetCustomerFilterByNameRequest request)

    {
        GetCustomerFilterByNameResponse response = new GetCustomerFilterByNameResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getCustomerFilterByName";
            response = (GetCustomerFilterByNameResponse) transportService.executeRequest(url, request, null, GetCustomerFilterByNameResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetFilterTypeResponse getFilterType(GetFilterTypeRequest request)

    {
        GetFilterTypeResponse response = new GetFilterTypeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getFilterType";
            response = (GetFilterTypeResponse) transportService.executeRequest(url, request, null, GetFilterTypeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public UpdateCustomerFilterResponse updateCustomerFilter(UpdateCustomerFilterRequest request)

    {
        UpdateCustomerFilterResponse response = new UpdateCustomerFilterResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateCustomerFilter";
            response = (UpdateCustomerFilterResponse) transportService.executeRequest(url, request, null, UpdateCustomerFilterResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

}
