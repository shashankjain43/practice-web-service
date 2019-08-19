 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 26-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.server.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.core.entity.ESPFilterCityMapping;
import com.snapdeal.ums.core.entity.ESPProfileField;
import com.snapdeal.ums.core.entity.EmailBulkEspCityMapping;
import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.admin.dao.IBulkCityESPMappingDao;
import com.snapdeal.ums.admin.dao.IESPBulkEmailDao;
import com.snapdeal.ums.server.services.IBulkEmailServiceInternal;

@Transactional
@Service("bulkEmailServiceInternal")
public class BulkEmailServiceInternalImpl implements IBulkEmailServiceInternal {

    @Autowired
    private IESPBulkEmailDao       bulkEmailDao;

    @Autowired
    private IBulkCityESPMappingDao bulkCityEspMappingDao;

    @Override
    public void updateFilterCityMapping(ESPFilterCityMapping espFilterCityMapping) {
        bulkEmailDao.updateFilterCityMapping(espFilterCityMapping);
    }

    @Override
    public List<EmailServiceProvider> getAllESPs() {
        return bulkEmailDao.getAllESPs();
   
    }
    
    @Override
    public List<ESPFilterCityMapping> getFiltersForCity(int cityId, int espId) {
        return bulkEmailDao.getFiltersForCity(cityId, espId);
    }

    @Override
    public List<ESPProfileField> getProfileFieldsForESP(int espId) {
        return bulkEmailDao.getProfileFieldsForESP(espId);
    }

    @Override
    public List<EmailBulkEspCityMapping> getAllEmailBulkEspCityMapping() {
        return bulkCityEspMappingDao.getAllEmailBulkEspCityMapping();
    }

    @Override
    public EmailBulkEspCityMapping getBulkEspCityMappingForCity(Integer city) {
        List<EmailBulkEspCityMapping> list = bulkCityEspMappingDao.getBulkEspCityMappingForCity(city);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public EmailBulkEspCityMapping update(EmailBulkEspCityMapping emailBulkEspCityMapping) {
        return bulkCityEspMappingDao.update(emailBulkEspCityMapping);
    }

    @Override
    public List<Object[]> getResultsMau(int start, int number, String city) {
        // TODO Auto-generated method stub
        return bulkEmailDao.getResultsMau(start, number, city);
    }

    @Override
    public List<Object> getResultsBounce(int start, int number, String city) {
        // TODO Auto-generated method stub
        return bulkEmailDao.getResultsBounce(start, number, city);
    }

    @Override
    public ESPFilterCityMapping getESPFilerCityMappingById(int id) {
        return bulkEmailDao.getESPFilerCityMappingById(id);
    }

}

 