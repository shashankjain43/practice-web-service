/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Nov 15, 2010
 *  @author rahul
 */
package com.snapdeal.ums.admin.dao.esp;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.admin.dao.IESPBulkEmailDao;
import com.snapdeal.ums.core.entity.ESPFilterCityMapping;
import com.snapdeal.ums.core.entity.ESPProfileField;
import com.snapdeal.ums.core.entity.EmailServiceProvider;

@Repository("umsEspBulkEmailDaoImpl")
public class ESPBulkEmailDaoImpl implements IESPBulkEmailDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void updateFilterCityMapping(ESPFilterCityMapping filterCityMapping) {
        sessionFactory.getCurrentSession().merge(filterCityMapping);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ESPFilterCityMapping> getFiltersForCity(int cityId, int espId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ESPFilterCityMapping where cityId=:cityId and emailServiceProvider.id=:espId");
        query.setParameter("cityId", cityId);
        query.setParameter("espId", espId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ESPProfileField> getProfileFieldsForESP(int espId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ESPProfileField where espId=:espId");
        query.setParameter("espId", espId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getResultsMau(int start, int number, String city) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery q = session.createSQLQuery("select * from snapdeal.s_lyris_mau_details where city_custom='" + city + "'" + "and email_type!='bounced'");
        q.addScalar("Email", Hibernate.STRING);
        q.addScalar("Join_Date", Hibernate.DATE);
        q.addScalar("First_Name", Hibernate.STRING);
        q.addScalar("city_custom", Hibernate.STRING);
        q.addScalar("deal_newsletter", Hibernate.STRING);
        q.addScalar("esp_id", Hibernate.INTEGER);
        q.addScalar("is_customer", Hibernate.STRING);
        q.addScalar("preference", Hibernate.STRING);
        q.addScalar("promocode", Hibernate.STRING);
        q.addScalar("subscribed", Hibernate.STRING);
        q.addScalar("val", Hibernate.STRING);
        q.addScalar("zone", Hibernate.LONG);
        q.addScalar("Total_Campaigns_Sent", Hibernate.LONG);
        q.addScalar("Last_Campaign_Sent", Hibernate.STRING);
        q.addScalar("Unique_Opens", Hibernate.INTEGER);
        q.addScalar("Last_Open", Hibernate.STRING);
        q.addScalar("Total_Opens", Hibernate.INTEGER);
        q.addScalar("Total_Messages_with_Clicks", Hibernate.INTEGER);
        q.addScalar("Total_Clicks", Hibernate.INTEGER);
        q.addScalar("file_name", Hibernate.STRING);
        q.addScalar("mailing_list_id", Hibernate.INTEGER);
        q.addScalar("load_date", Hibernate.LONG);
        q.addScalar("email_type", Hibernate.STRING);
        q.addScalar("account_id", Hibernate.INTEGER);
        q.addScalar("unique_sid", Hibernate.INTEGER);

        q.setFirstResult(start);
        q.setMaxResults(number);
        return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getResultsBounce(int start, int number, String city) {
        Session session = sessionFactory.getCurrentSession();
        SQLQuery q = session.createSQLQuery("select Email from snapdeal.s_lyris_mau_details where city_custom='" + city + "'" + "and email_type ='bounced'");
        q.addScalar("Email", Hibernate.STRING);
        q.setFirstResult(start);
        q.setMaxResults(number);
        return q.list();
    }

    @Override
    public List<EmailServiceProvider> getAllESPs() {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailServiceProvider where enabled = :enabled");
        query.setParameter("enabled", 1);
        return query.list();
    }

    @Override
    public ESPFilterCityMapping getESPFilerCityMappingById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from ESPFilterCityMapping where id=:id");
        query.setParameter("id", id);
        return (ESPFilterCityMapping) query.uniqueResult();
    }
}
