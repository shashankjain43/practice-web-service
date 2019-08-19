/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *  @version     1.0, Oct 21, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.admin.dao.IEmailTemplateDao;
import com.snapdeal.ums.core.entity.EmailTemplate;

@Repository("emailDao")
public class EmailTemplateDaoImpl implements IEmailTemplateDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public EmailTemplate update(EmailTemplate emailTemplate) {
        return (EmailTemplate) sessionFactory.getCurrentSession().merge(emailTemplate);
    }

    @Override
    public EmailTemplate getEmailTemplateByName(String templateName) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailTemplate where name=:name");
        query.setParameter("name", templateName);
        return (EmailTemplate) query.uniqueResult();
    }
}