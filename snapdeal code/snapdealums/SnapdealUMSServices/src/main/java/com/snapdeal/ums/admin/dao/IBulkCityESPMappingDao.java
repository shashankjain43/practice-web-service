/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 21, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.dao;

import java.util.List;

import com.snapdeal.ums.core.entity.EmailBulkEspCityMapping;

public interface IBulkCityESPMappingDao {
    public List<EmailBulkEspCityMapping> getAllEmailBulkEspCityMapping();

    public List<EmailBulkEspCityMapping> getBulkEspCityMappingForCity(Integer cityId);

    public EmailBulkEspCityMapping update(EmailBulkEspCityMapping emailBulkEspCityMapping);
}
