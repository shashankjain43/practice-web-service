package com.snapdeal.ums.admin.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
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
import com.snapdeal.ums.admin.server.services.ICustomerFilterService;

@Controller
@RequestMapping("/service/ums/admin/customerfilter/")
public class CustomerFilterServiceController {

    @Autowired
    private ICustomerFilterService customerFilterService;

    @RequestMapping(value = "getAllCustomerFilters", produces = "application/sd-service")
    @ResponseBody
    public GetAllCustomerFiltersResponse getAllCustomerFilters(@RequestBody GetAllCustomerFiltersRequest request) throws TransportException {
        GetAllCustomerFiltersResponse response = customerFilterService.getAllCustomerFilters(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getCustomerFiltersByDomain", produces = "application/sd-service")
    @ResponseBody
    public GetCustomerFiltersByDomainResponse getCustomerFiltersByDomain(@RequestBody GetCustomerFiltersByDomainRequest request) throws TransportException {
        GetCustomerFiltersByDomainResponse response = customerFilterService.getCustomerFiltersByDomain(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getCustomerFilterByName", produces = "application/sd-service")
    @ResponseBody
    public GetCustomerFilterByNameResponse getCustomerFilterByName(@RequestBody GetCustomerFilterByNameRequest request) throws TransportException {
        GetCustomerFilterByNameResponse response = customerFilterService.getCustomerFilterByName(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getFilterType", produces = "application/sd-service")
    @ResponseBody
    public GetFilterTypeResponse getFilterType(@RequestBody GetFilterTypeRequest request) throws TransportException {
        GetFilterTypeResponse response = customerFilterService.getFilterType(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateCustomerFilter", produces = "application/sd-service")
    @ResponseBody
    public UpdateCustomerFilterResponse updateCustomerFilter(@RequestBody UpdateCustomerFilterRequest request) throws TransportException {
        UpdateCustomerFilterResponse response = customerFilterService.updateCustomerFilter(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
