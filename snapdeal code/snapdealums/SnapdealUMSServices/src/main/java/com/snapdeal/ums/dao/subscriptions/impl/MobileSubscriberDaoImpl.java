/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 20-Jul-2012
 *  @author kuldeep
 */
package com.snapdeal.ums.dao.subscriptions.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.ums.core.entity.MobileSubscriber;
import com.snapdeal.ums.dao.subscriptions.IMobileSubscriberDao;

@Repository("umsMobileSubsciberDao")
@SuppressWarnings("unchecked")
public class MobileSubscriberDaoImpl implements IMobileSubscriberDao {

    private static final Logger LOG = LoggerFactory.getLogger(MobileSubscriberDaoImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
 //TODO:   @Qualifier("replica")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<MobileSubscriber> getMobileSubscribersFromReplica(int cityId, DateRange dateRange, boolean subscribed, boolean dnd, boolean isCustomer, int firstResult,
            int maxResults) {
        
        try {
            Connection conn = sessionFactory.getCurrentSession().connection();
            LOG.info("URL: "+conn.getMetaData().getURL());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        String queryStr = "select {ms.*} from mobile_subscriber ms inner join zone z on z.id = ms.zone_id where ms.subscribed =:subscribed and ms.dnd =:dnd and ms.customer = :customer and z.city_id = :cityId";
        queryStr += (dateRange.getStart() != null) ? " and ms.created > :start" : "";
        queryStr += (dateRange.getEnd() != null) ? " and ms.created <= :end" : "";
        queryStr += " order by ms.mobile";
        SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(queryStr);
        query.setParameter("cityId", cityId);
        query.setParameter("subscribed", subscribed);
        query.setParameter("dnd", dnd);
        query.setParameter("customer", isCustomer);
        query.addEntity("ms", MobileSubscriber.class);
        if (dateRange.getStart() != null) {
            query.setParameter("start", dateRange.getStart());
        }
        if (dateRange.getEnd() != null) {
            query.setParameter("end", dateRange.getEnd());
        }

        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }
}
