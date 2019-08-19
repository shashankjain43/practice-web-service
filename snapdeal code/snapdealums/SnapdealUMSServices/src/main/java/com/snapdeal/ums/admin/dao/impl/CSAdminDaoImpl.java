/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 9, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.admin.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.CsZentrix;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.ZendeskUser;
import com.snapdeal.ums.admin.dao.ICSAdminDao;


@Repository("csUMSDao")
public class CSAdminDaoImpl implements ICSAdminDao{

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> getCSexecutiveUser() {
        Query q = sessionFactory.getCurrentSession().createQuery("select u from User u left join fetch u.userRoles ur where ur.role IN ('ccexecutive','csadmin','ccmanager') group by u.email");
        return q.list();
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllZendeskUser() {
        Query q = sessionFactory.getCurrentSession().createQuery("select u from User u left join fetch u.userRoles ur where ur.role = 'zendeskuser' ");
        return q.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<User> getAllCZentrixUser() {
        Query q = sessionFactory.getCurrentSession().createQuery("select u from User u left join fetch u.userRoles ur where ur.role = 'czentrixuser' ");
        return q.list();
    }
    
    @Override
    public ZendeskUser getZendeskUser(int userId) {
        Query q = sessionFactory.getCurrentSession().createQuery("from ZendeskUser where user.id = :userId");
        q.setParameter("userId", userId);
        return (ZendeskUser) q.uniqueResult();
    }

    @Override
    public CsZentrix getCsZentrixIdByUser(int userId) {
        Query q = sessionFactory.getCurrentSession().createQuery("from CsZentrix where user.id=:userId");
        q.setParameter("userId", userId);
        return (CsZentrix) q.uniqueResult();
    }

    @Override
    public void persistCsZentrixId(CsZentrix cs) {
        sessionFactory.getCurrentSession().persist(cs);
    }

    @Override
    public void updateCsZentrixId(CsZentrix cs) {
        sessionFactory.getCurrentSession().update(cs);
    }

    @Override
    public void persistZendeskUser(ZendeskUser zendeskUser) {
        sessionFactory.getCurrentSession().persist(zendeskUser);

    }

    @Override
    public void updateZendeskUser(ZendeskUser zendeskUser) {
        sessionFactory.getCurrentSession().update(zendeskUser);

    }

}
