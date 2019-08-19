/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 14, 2010
 *  @author rahul
 */
package com.snapdeal.ums.dao.activity.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.core.entity.Activity;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.dao.activity.IActivityDao;

@Repository("umsActivityDao")
@SuppressWarnings("unchecked")
public class ActivityDaoImpl implements IActivityDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void persistActivity(Activity activity) {
        if (activity != null && activity.getCreated() == null) {
            activity.setCreated(DateUtils.getCurrentTime());
        }
        sessionFactory.getCurrentSession().persist(activity);
    }

    @Override
    public List<Activity> getActivityByUserId(int userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Activity a join fetch a.activityType where a.user.id=:userId");
        query.setParameter("userId", userId);
        return query.list();
    }

    @Override
    public List<Activity> getActivityByUserAndActivityType(User user, Integer activity_type_id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Activity where activityType.id = :activityTypeId and user.id = :userId");
        query.setParameter("activityTypeId", activity_type_id);
        query.setParameter("userId", user.getId());
        return query.list();
    }

    @Override
    public List<Activity> getActivityByAttribute(Activity activity, String attribute) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Activity where id = :activity and attributes like (:attribute)");
        query.setParameter("activity", activity.getId());
        query.setParameter("attribute", "%" + attribute + "%");
        return query.list();
    }

    @Override
    public List<Activity> getSDCashActivities(DateRange range, int firstResult, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Activity where sdCash != 0 and created >= :startDate and created <= :endDate");
        query.setParameter("startDate", range.getStart());
        query.setParameter("endDate", range.getEnd());
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }

    @Override
    public Activity getLastOrderRefundActivityForUserId(Integer userId, Integer activityTypeId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select ac.* from activity ac where ac.sd_cash != 0 and ac.user_id = :userId and ac.activity_type_id = :activityTypeId order by id desc limit 1").addEntity(Activity.class);
        query.setParameter("userId", userId);
        query.setParameter("activityTypeId", activityTypeId);
        return (Activity) query.uniqueResult();
    }
}
