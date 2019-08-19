/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 13-Dec-2012
*  @author naveen
*/
package com.snapdeal.ums.dao.common;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.ums.core.entity.Locality;
import com.snapdeal.ums.core.entity.SmsTemplate;
import com.snapdeal.ums.core.entity.UMSProperty;
import com.snapdeal.ums.core.utils.QueryNames;
import com.snapdeal.ums.core.entity.Role;

@Repository("umsStartupDao")
@SuppressWarnings("unchecked")
public class StartupDaoImpl implements IStartupDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory umsSessionFactory) {
        this.sessionFactory = umsSessionFactory;
    }
    
    @Override
    public List<UMSProperty> getUMSProperties() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(QueryNames.GET_ALL_PROPERTIES);
        return query.list();
    }

    @Override
    
    public List<EmailTemplate> getEmailTemplates() {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailTemplate");
        return query.list();
    }

    @Override
    public List<SmsTemplate> getSmsTemplates() {
        Query query = sessionFactory.getCurrentSession().createQuery("from SmsTemplate");
        return query.list();
    }

    @Override
    public List<Locality> getAllLocalities() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Locality where enabled=1");
        return query.list();
    }
    
    @Override
    public List<Role> getAllRoles() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Role order by code");
        return query.list();
    }
}
