/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 25-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.admin.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.core.entity.CustomerFilter;
import com.snapdeal.ums.core.entity.CustomerFilter.FilterType;
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
import com.snapdeal.ums.admin.server.services.ICustomerFilterServiceInternal;
import com.snapdeal.ums.core.sro.customerfilter.CommunicationAdminFilterSRO;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO.FilterDomain;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
@Service("umsCustomerFilterService")
public class CustomerFilterServiceImpl implements ICustomerFilterService {

    @Autowired
    private ICustomerFilterServiceInternal customerFilterServiceInternalImpl;
    @Autowired
    private IUMSConvertorService           umsConvertorService;

    @Deprecated
    @Override
    public GetAllCustomerFiltersResponse getAllCustomerFilters(GetAllCustomerFiltersRequest request) {

        GetAllCustomerFiltersResponse response = new GetAllCustomerFiltersResponse();
        List<CustomerFilter> customerFilters = customerFilterServiceInternalImpl.getAllCustomerFilters();
        List<CustomerFilterSRO> sros = new ArrayList<CustomerFilterSRO>();
        for (CustomerFilter customerFilter : customerFilters) {
            sros.add(umsConvertorService.getCustomerFilterSROfromEntity(customerFilter));
        }
        response.setGetAllCustomerFilters(sros);
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public GetCustomerFiltersByDomainResponse getCustomerFiltersByDomain(GetCustomerFiltersByDomainRequest request) {
        GetCustomerFiltersByDomainResponse response = new GetCustomerFiltersByDomainResponse();
        FilterDomain domain = request.getDomain();
        if (domain != null) {
            List<CustomerFilter> customerFilters = customerFilterServiceInternalImpl.getCustomerFiltersByDomain(CustomerFilter.FilterDomain.valueOf(domain.toString()));
            List<CustomerFilterSRO> sros = new ArrayList<CustomerFilterSRO>();
            for (CustomerFilter customerFilter : customerFilters) {
                sros.add(umsConvertorService.getCustomerFilterSROfromEntity(customerFilter));
            }
            response.setGetCustomerFiltersByDomain(sros);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("domain can not be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetCustomerFilterByNameResponse getCustomerFilterByName(GetCustomerFilterByNameRequest request) {
        GetCustomerFilterByNameResponse response = new GetCustomerFilterByNameResponse();
        FilterDomain domain = request.getDomain();
        String name = request.getName();
        if ((domain != null) && (StringUtils.isNotEmpty(name))) {
            CustomerFilter customerFilter = customerFilterServiceInternalImpl.getCustomerFilterByName(name, CustomerFilter.FilterDomain.valueOf(domain.toString()));
            CustomerFilterSRO sro = umsConvertorService.getCustomerFilterSROfromEntity(customerFilter);
            response.setGetCustomerFilterByName(sro);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("domain and name can not be null/empty");
        }

        return response;
    }

    @Deprecated
    @Override
    public GetFilterTypeResponse getFilterType(GetFilterTypeRequest request) {
        GetFilterTypeResponse response = new GetFilterTypeResponse();
        CustomerFilterSRO sro = request.getCustomerFilter();
        if (sro != null) {
            FilterType filterType = customerFilterServiceInternalImpl.getFilterType(umsConvertorService.getCustomerFilterEntityFromSRO(sro));
            response.setGetFilterType(CustomerFilterSRO.FilterType.valueOf(filterType.toString()));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("customerFilter can not be null");
        }
        return response;
    }

    @Deprecated
    @Override
    public UpdateCustomerFilterResponse updateCustomerFilter(UpdateCustomerFilterRequest request) {
        UpdateCustomerFilterResponse response = new UpdateCustomerFilterResponse();
        CommunicationAdminFilterSRO sro = request.getFilterSRO();
        if (sro != null) {
            customerFilterServiceInternalImpl.updateCustomerFilter(umsConvertorService.getCommunicationAdminFilterDTOfromSRO(sro));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("CommunicationAdminFilterSRO can not be null");
        }
        return response;
    }
}
