/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Nov 17, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.dao.newsletter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.core.entity.Newsletter;
import com.snapdeal.ums.core.entity.NewsletterEspMapping;

@Repository("umsNewsletterDao")
public class NewsletterDaoImpl implements INewsletterDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void persist(Newsletter newsletter) {
        if (newsletter != null) {
            newsletter.setCreated(DateUtils.getCurrentTime());
            newsletter.setEnabled(true);
            newsletter.setUpdated(DateUtils.getCurrentTime());
        }
        sessionFactory.getCurrentSession().persist(newsletter);
    }

    @Override
    public void persist(NewsletterEspMapping newsletterEspMapping) {
        if (newsletterEspMapping != null) {
            sessionFactory.getCurrentSession().persist(newsletterEspMapping);
        }
    }

    @Override
    public Newsletter update(Newsletter newsletter) {
        return (Newsletter) sessionFactory.getCurrentSession().merge(newsletter);
    }
    
    @Override
    public NewsletterEspMapping update(NewsletterEspMapping newsletterEspMapping) {
        return (NewsletterEspMapping) sessionFactory.getCurrentSession().merge(newsletterEspMapping);
    }

    @Override
    public Newsletter getNewsletterDetails(String cityId, Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and cityIds like :city_ids and scheduleDate=:scheduleDate");
        query.setParameter("city_ids", "%" + cityId + "%");
        query.setParameter("scheduleDate", date);
        return (Newsletter) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Newsletter> getNewsletters(String cityId, Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and cityIds like :city_ids and scheduleDate>=:scheduleDate and scheduleDate <:nextDay");
        query.setParameter("city_ids", "%" + cityId + "%");
        query.setParameter("scheduleDate", date);
        query.setParameter("nextDay", DateUtils.addToDate(date, Calendar.DATE, 1));
        return query.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Newsletter> getNewsletters(String cityId, Date date, String state) {
        if("all".equals(state)){
            Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and cityIds like :city_ids and scheduleDate>=:scheduleDate and scheduleDate <:nextDay");
            query.setParameter("city_ids", "%" + cityId + "%");
            query.setParameter("scheduleDate", date);
            query.setParameter("nextDay", DateUtils.addToDate(date, Calendar.DATE, 1));
            return query.list();
        } else {
            Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and cityIds like :city_ids and scheduleDate>=:scheduleDate and scheduleDate <:nextDay and state=:state");
            query.setParameter("city_ids", "%" + cityId + "%");
            query.setParameter("state", state);
            query.setParameter("scheduleDate", date);
            query.setParameter("nextDay", DateUtils.addToDate(date, Calendar.DATE, 1));
            return query.list();    
        }
    }

    @Override
    public Newsletter getNewsletterById(Integer id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and id=:newsletterId");
        query.setParameter("newsletterId", id);
        return (Newsletter) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Newsletter> getNewsletterDetails(Date date) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and scheduleDate>=:scheduleDate and scheduleDate <:nextDay order by cityIds");
        query.setParameter("scheduleDate", date);
        query.setParameter("nextDay", DateUtils.addToDate(date, Calendar.DATE, 1));
        return query.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Newsletter> getNewsletterDetails(Date date, String state) {
        if("all".equals(state)){
            Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and scheduleDate>=:scheduleDate and scheduleDate <:nextDay order by cityIds");
            query.setParameter("scheduleDate", date);
            query.setParameter("nextDay", DateUtils.addToDate(date, Calendar.DATE, 1));
            return query.list();
        } else {
            Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and scheduleDate>=:scheduleDate and scheduleDate <:nextDay and state=:state order by cityIds");
            query.setParameter("state", state);
            query.setParameter("scheduleDate", date);
            query.setParameter("nextDay", DateUtils.addToDate(date, Calendar.DATE, 1));
            return query.list();    
        }
    }

    @Override
    public Newsletter getNewsletterByMsgId(String msgId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Newsletter where enabled=1 and messageId=:messageId");
        query.setParameter("messageId", msgId);
        return (Newsletter) query.uniqueResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<NewsletterEspMapping> getNewsletterEspMapping(int newsletterId, int espId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from NewsletterEspMapping where newsletter.id=:newsletterId and emailServiceProvider.id=:espId");
        query.setParameter("newsletterId", newsletterId);
        query.setParameter("espId", espId);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> getFailedCitiesForNewsletter(int newsletterId) {
        Query query = sessionFactory.getCurrentSession().createQuery("select distinct cityId from NewsletterEspMapping where newsletter.id=:newsletterId and status=:status");
        query.setParameter("newsletterId", newsletterId);
        query.setParameter("status", NewsletterEspMapping.State.failed);
        return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setNewsletterEspMappingFailed(int newsletterId,int cityId){
        Query query = sessionFactory.getCurrentSession().createQuery("from NewsletterEspMapping where newsletter.id=:newsletterId and city_id=:cityId");
        query.setParameter("cityId",cityId);
        query.setParameter("newsletterId", newsletterId);
        for(NewsletterEspMapping nem:(List<NewsletterEspMapping>) query.list()){
            nem.setStatus(NewsletterEspMapping.State.failed);
            update(nem);
        }
    }
    
    @Override
    public NewsletterEspMapping getNewsletterEspMappingForCity(int newsletterId, int espId, int cityId, String filterType) {
        Query query = sessionFactory.getCurrentSession().createQuery("from NewsletterEspMapping where newsletter.id=:newsletterId and emailServiceProvider.id=:espId and cityId = :cityId and filterType = :filterType");
        query.setParameter("newsletterId", newsletterId);
        query.setParameter("espId", espId);
        query.setParameter("cityId", cityId);
        query.setParameter("filterType", filterType);
        return (NewsletterEspMapping) query.uniqueResult();
    }
    
    @Override
    public List<EmailServiceProvider> getAllESPs() {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailServiceProvider where enabled = :enabled");
        query.setParameter("enabled", 1);
        return query.list();
    }
}