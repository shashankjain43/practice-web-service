/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 21, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.admin.dao.IBulkCityESPMappingDao;
import com.snapdeal.ums.core.entity.EmailBulkEspCityMapping;

@Repository("umsEmailBulkCityEspMappingDao")
public class BulkCityESPMappingDaoImpl implements IBulkCityESPMappingDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public EmailBulkEspCityMapping update(EmailBulkEspCityMapping emailBulkEspCityMapping) {
        return (EmailBulkEspCityMapping) sessionFactory.getCurrentSession().merge(emailBulkEspCityMapping);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmailBulkEspCityMapping> getAllEmailBulkEspCityMapping() {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailBulkEspCityMapping");
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmailBulkEspCityMapping> getBulkEspCityMappingForCity(Integer cityId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailBulkEspCityMapping where city_id= :cityId");
        query.setParameter("cityId", cityId);
        return query.list();

    }
}
