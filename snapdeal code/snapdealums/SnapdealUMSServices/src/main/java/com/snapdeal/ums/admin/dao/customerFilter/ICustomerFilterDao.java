package com.snapdeal.ums.admin.dao.customerFilter;

import java.util.List;

import com.snapdeal.ums.core.entity.CustomerFilter;
import com.snapdeal.ums.core.entity.CustomerFilter.FilterDomain;

public interface ICustomerFilterDao {

	public CustomerFilter getCustomerFilterById(int id);
	
	public List<CustomerFilter> getAllCustomerFilters();
	
	public List<CustomerFilter> getCustomerFiltersByDomain(CustomerFilter.FilterDomain domain);
	
	public CustomerFilter getCustomerFilterByName(String name, FilterDomain domain);
	
	public void persist(CustomerFilter customerFilter);
}
