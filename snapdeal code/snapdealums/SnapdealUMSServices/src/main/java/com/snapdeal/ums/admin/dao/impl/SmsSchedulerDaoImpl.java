/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 18, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.admin.dao.ISmsSchedulerDao;
import com.snapdeal.ums.core.entity.SmsScheduler;

@Repository("umsSmsScheduler")
public class SmsSchedulerDaoImpl implements ISmsSchedulerDao {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void persist(SmsScheduler smsScheduler) {
        sessionFactory.getCurrentSession().persist(smsScheduler);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SmsScheduler> getSmsSchedulerList(Date date, String status){
        if("all".equals(status)){
            Query q = sessionFactory.getCurrentSession().createQuery("from SmsScheduler where date(scheduleTime)= :scheduleTime Order By cityIds");
            q.setParameter("scheduleTime", date);
            return q.list();
        } else {
            Query q = sessionFactory.getCurrentSession().createQuery("from SmsScheduler where date(scheduleTime)= :scheduleTime and status=:status Order By cityIds");
            q.setParameter("scheduleTime", date); 
            q.setParameter("status", status); 
            return q.list();
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SmsScheduler> getSmsScheduler(int cityId, Date date, String status){
        if("all".equals(status)){
            Query q = sessionFactory.getCurrentSession().createQuery("from SmsScheduler where cityIds like :city_ids and date(scheduleTime)= :scheduleTime");
            q.setParameter("scheduleTime", date);
            q.setParameter("city_ids", "%" + cityId + "%");
            return q.list();
        } else {
            Query q = sessionFactory.getCurrentSession().createQuery("from SmsScheduler where cityIds like :city_ids and date(scheduleTime)= :scheduleTime and status=:status");
            q.setParameter("scheduleTime", date);
            q.setParameter("status", status); 
            q.setParameter("city_ids", "%" + cityId + "%");
            return q.list();    
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SmsScheduler> getSmsSchedulerList(Date date){
        Query q = sessionFactory.getCurrentSession().createQuery("from SmsScheduler where date(scheduleTime)= :scheduleTime Order By cityIds");
        q.setParameter("scheduleTime", date);
        return q.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<SmsScheduler> getSmsScheduler(int cityId, Date date){
        Query q = sessionFactory.getCurrentSession().createQuery("from SmsScheduler where cityIds like :city_ids and date(scheduleTime)= :scheduleTime");
        q.setParameter("scheduleTime", date);
        q.setParameter("city_ids", "%" + cityId + "%");
        return q.list();
    }
    
    @Override
    public SmsScheduler getSmsSchedulerById(int id){
        Query q = sessionFactory.getCurrentSession().createQuery("from SmsScheduler where id= :id");
        q.setParameter("id", id);
        return (SmsScheduler) q.uniqueResult();
    }
    
    @Override
    public void update(SmsScheduler smsScheduler){
        sessionFactory.getCurrentSession().merge(smsScheduler);
    }
}
