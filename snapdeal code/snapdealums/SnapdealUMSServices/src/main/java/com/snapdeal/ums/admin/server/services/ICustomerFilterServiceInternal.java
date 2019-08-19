
package com.snapdeal.ums.admin.server.services;

import java.util.List;

import com.snapdeal.core.dto.CommunicationAdminFilterDTO;
import com.snapdeal.ums.core.entity.CustomerFilter;
import com.snapdeal.ums.core.entity.CustomerFilter.FilterDomain;
import com.snapdeal.ums.core.entity.CustomerFilter.FilterType;

public interface ICustomerFilterServiceInternal {

	public List<CustomerFilter> getAllCustomerFilters();

	public List<CustomerFilter> getCustomerFiltersByDomain(FilterDomain domain);
	
	public CustomerFilter getCustomerFilterByName(String name, FilterDomain domain);
	
	public FilterType getFilterType(CustomerFilter customerFilter);

	public void updateCustomerFilter(CommunicationAdminFilterDTO filterDTO);

}
