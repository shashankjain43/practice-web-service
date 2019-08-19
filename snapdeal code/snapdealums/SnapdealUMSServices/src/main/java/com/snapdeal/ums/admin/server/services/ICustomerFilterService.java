
package com.snapdeal.ums.admin.server.services;

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

public interface ICustomerFilterService {


    public GetAllCustomerFiltersResponse getAllCustomerFilters(GetAllCustomerFiltersRequest request);

    public GetCustomerFiltersByDomainResponse getCustomerFiltersByDomain(GetCustomerFiltersByDomainRequest request);

    public GetCustomerFilterByNameResponse getCustomerFilterByName(GetCustomerFilterByNameRequest request);

    public GetFilterTypeResponse getFilterType(GetFilterTypeRequest request);

    public UpdateCustomerFilterResponse updateCustomerFilter(UpdateCustomerFilterRequest request);

}
