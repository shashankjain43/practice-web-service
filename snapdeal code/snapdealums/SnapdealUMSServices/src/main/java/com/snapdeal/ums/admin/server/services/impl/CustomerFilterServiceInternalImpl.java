/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 25-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.admin.server.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.core.dto.CommunicationAdminFilterDTO;
import com.snapdeal.ums.core.entity.CustomerFilter;
import com.snapdeal.ums.core.entity.CustomerFilter.FilterDomain;
import com.snapdeal.ums.core.entity.CustomerFilter.FilterType;
import com.snapdeal.ums.admin.dao.customerFilter.ICustomerFilterDao;
import com.snapdeal.ums.admin.server.services.ICustomerFilterServiceInternal;

@Transactional
@Service("umsCustomerFilterServiceInternal")
public class CustomerFilterServiceInternalImpl implements ICustomerFilterServiceInternal {
    @Autowired
    private ICustomerFilterDao customerFilterDao;
    
    @Override
    public List<CustomerFilter> getAllCustomerFilters() {
        return customerFilterDao.getAllCustomerFilters();
        
    }
    
    @Override
    public List<CustomerFilter> getCustomerFiltersByDomain(CustomerFilter.FilterDomain domain) {
        return customerFilterDao.getCustomerFiltersByDomain(domain);
        
    }
    
    @Override
    public CustomerFilter getCustomerFilterByName(String name, FilterDomain domain) {
        return customerFilterDao.getCustomerFilterByName(name, domain);
    }
    
    @Override
    public FilterType getFilterType(CustomerFilter customerFilter) {
        for (FilterType filterType : FilterType.values()) {
            if(filterType.equals(customerFilter)){
                return filterType;
            }
        }
        return null;
    }
    
    @Override
    public void updateCustomerFilter(CommunicationAdminFilterDTO filterDTO) {
        CustomerFilter customerFilter = customerFilterDao.getCustomerFilterById(filterDTO.getId());
        customerFilter.setIntervalOffset((filterDTO.getOffsetHours()*60)+(filterDTO.getOffsetMins()*5));
        customerFilter.setSelected(filterDTO.isDefaultSelected());
        customerFilterDao.persist(customerFilter);
    }
}
