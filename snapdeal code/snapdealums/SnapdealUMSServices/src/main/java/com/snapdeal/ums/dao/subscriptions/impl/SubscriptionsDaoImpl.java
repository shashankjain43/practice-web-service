/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 18, 2010
 *  @author rahul
 */
package com.snapdeal.ums.dao.subscriptions.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.core.utils.RequestContext;
import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.ums.core.entity.AffiliateSubscriptionOffer;
import com.snapdeal.ums.core.entity.EmailMobileAssociation;
import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.core.entity.EmailServiceProvider.ESP;
import com.snapdeal.ums.core.entity.EmailSubscriber;
import com.snapdeal.ums.core.entity.EmailSubscriberDetail;
import com.snapdeal.ums.core.entity.MobileSubscriber;
import com.snapdeal.ums.core.entity.MobileSubscriberDetail;
import com.snapdeal.ums.core.entity.SubscriberProfile;
import com.snapdeal.ums.core.utils.QueryNames;
import com.snapdeal.ums.dao.subscriptions.ISubscriptionsDao;

@Repository("umsSubscriptionsDao")
@SuppressWarnings("unchecked")
public class SubscriptionsDaoImpl implements ISubscriptionsDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override   
    public MobileSubscriber findMobileSubscriberByMobile(String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("from MobileSubscriber where mobile =:mobile");
        query.setParameter("mobile", mobile);
        return (MobileSubscriber)query.uniqueResult();
    }
    
    @Override
    public List<EmailSubscriber> getSubscribedEmailSubscribers(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailSubscriber where subscribed = 1 and email =:email");
        query.setParameter("email", email);
        return query.list();
    }
    
    @Override
    public EmailSubscriber persistEmailSubscriber(EmailSubscriber emailSubscriber) {
        if (emailSubscriber != null) {
            if (emailSubscriber.getCreated() == null) {
                emailSubscriber.setCreated(DateUtils.getCurrentTime());
            }

            if (emailSubscriber.getEmailServiceProvider() == null) {
                EmailServiceProvider provider = new EmailServiceProvider(CacheManager.getInstance().getCache(UMSPropertiesCache.class).getDefaultESP(ESP.LYRIS.getId()));
                emailSubscriber.setEmailServiceProvider(provider);
            }

            if (emailSubscriber.getId() == null) {
                emailSubscriber.setChannelCode(RequestContext.current().getVisitTrackingParams().get(Constants.TRACKING_PARAMETER_UTM_SOURCE));
            }

            emailSubscriber.setUpdated(DateUtils.getCurrentTime());
        }
        return (EmailSubscriber) sessionFactory.getCurrentSession().merge(emailSubscriber);
    }

    @Override
    public MobileSubscriber persistMobileSubscriber(MobileSubscriber mobileSubscriber) {
        if (mobileSubscriber != null) {
            if (mobileSubscriber.getCreated() == null) {
                mobileSubscriber.setCreated(new Date());
            }
            if (mobileSubscriber.getId() == null) {
                mobileSubscriber.setChannelCode(RequestContext.current().getVisitTrackingParams().get(Constants.TRACKING_PARAMETER_UTM_SOURCE));
            }
        }
        return (MobileSubscriber) sessionFactory.getCurrentSession().merge(mobileSubscriber);
    }

    @Override
    public EmailSubscriber findEmailSubscriber(String email, int zoneId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(QueryNames.GET_EMAIL_SUBSCRIBER_FROM_EMAIL);
        query.setParameter(QueryNames.QueryParameters.EMAIL, StringUtils.normalizeEmail(email));
        query.setParameter(QueryNames.QueryParameters.ZONE_ID, zoneId);
        return (EmailSubscriber) query.uniqueResult();
    }

    @Override
    public MobileSubscriber findMobileSubscriber(String mobile, int zoneId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(QueryNames.GET_MOBILE_SUBSCRIBER_FROM_MOBILE);
        query.setParameter(QueryNames.QueryParameters.MOBILE, mobile);
        query.setParameter(QueryNames.QueryParameters.ZONE_ID, zoneId);
        return (MobileSubscriber) query.uniqueResult();
    }

    @Override
    public EmailSubscriberDetail findEmailSubscriberDetailByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(QueryNames.GET_EMAIL_SUBSCRIBER_DETAIL_FROM_EMAIL);
        query.setParameter(QueryNames.QueryParameters.EMAIL, email);
        return (EmailSubscriberDetail) query.uniqueResult();
    }

    @Override
    public EmailSubscriberDetail persistEmailSubscriberDetail(EmailSubscriberDetail emailSubscriberDetail) {
        if (emailSubscriberDetail != null) {
            if (emailSubscriberDetail.getCreated() == null) {
                emailSubscriberDetail.setCreated(DateUtils.getCurrentTime());
            }

            emailSubscriberDetail.setUpdated(DateUtils.getCurrentTime());

            if (emailSubscriberDetail.getId() == null) {
                sessionFactory.getCurrentSession().persist(emailSubscriberDetail);
                sessionFactory.getCurrentSession().flush();
                emailSubscriberDetail.setUid(emailSubscriberDetail.getUIDCode());
            }

        }
        return (EmailSubscriberDetail) sessionFactory.getCurrentSession().merge(emailSubscriberDetail);

    }

    @Override
    public MobileSubscriberDetail persistMobileSubscriberDetail(MobileSubscriberDetail mobileSubscriberDetail) {
        if (mobileSubscriberDetail != null) {
            if (mobileSubscriberDetail.getCreated() == null) {
                mobileSubscriberDetail.setCreated(DateUtils.getCurrentTime());
            }

            mobileSubscriberDetail.setUpdated(DateUtils.getCurrentTime());

            if (mobileSubscriberDetail.getId() == null) {
                sessionFactory.getCurrentSession().persist(mobileSubscriberDetail);
                sessionFactory.getCurrentSession().flush();
                mobileSubscriberDetail.setUid(mobileSubscriberDetail.getUIDCode());
            }

        }
        return (MobileSubscriberDetail) sessionFactory.getCurrentSession().merge(mobileSubscriberDetail);
    }

    @Override
    public void updateEmailSubscriberDetail(EmailSubscriberDetail emailSubscriberDetail) {
        sessionFactory.getCurrentSession().merge(emailSubscriberDetail);
    }

    @Override
    public void persistEmailMobileAssociation(EmailMobileAssociation association) {
        sessionFactory.getCurrentSession().persist(association);
    }

    @Override
    public void updateEmailMobileAssociation(EmailMobileAssociation association) {
        sessionFactory.getCurrentSession().merge(association);
    }

    @Override
    public EmailMobileAssociation findEmailMobileAssociation(String email, String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailMobileAssociation where email = :email and mobile=:mobile");
        query.setParameter("mobile", mobile);
        query.setParameter("email", email);
        return (EmailMobileAssociation) query.uniqueResult();
    }

    @Override
    public List<EmailSubscriber> findEmailSubscriptionsByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailSubscriber where normalizedEmail = :email");
        query.setParameter("email", StringUtils.normalizeEmail(email));
        return query.list();
    }

    @Override
    public List<EmailSubscriber> findEmailSubscriberByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailSubscriber where email = :email");
        query.setParameter("email", email);
        return query.list();
    }

    @Override
    public List<MobileSubscriber> findMobileSubscriptionsByMobile(String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("from MobileSubscriber where mobile =:mobile");
        query.setParameter("mobile", mobile);
        return query.list();
    }

    @Override
    public List<MobileSubscriber> getSubscribedMobileSubscribers(String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("from MobileSubscriber where subscribed = 1 and mobile =:mobile");
        query.setParameter("mobile", mobile);
        return query.list();
    }

    @Override
    public List<EmailSubscriber> findEmailSubscribersByZone(int zoneId, Date startDate, Date endDate) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(QueryNames.GET_EMAIL_SUBSCRIBERS_FROM_ZONE);
        query.setParameter(QueryNames.QueryParameters.ZONE_ID, zoneId);
        query.setParameter(QueryNames.QueryParameters.STARTTIME, startDate);
        query.setParameter(QueryNames.QueryParameters.ENDTIME, endDate);
        return query.list();
    }

    @Override
    public List<MobileSubscriber> findMobileSubscribersByZone(int zoneId, Date startDate, Date endDate) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(QueryNames.GET_MOBILE_SUBSCRIBERS_FROM_ZONE);
        query.setParameter(QueryNames.QueryParameters.ZONE_ID, zoneId);
        query.setParameter(QueryNames.QueryParameters.STARTTIME, startDate);
        query.setParameter(QueryNames.QueryParameters.ENDTIME, endDate);
        return query.list();
    }

    @Override
    public List<EmailSubscriber> getAllEmailSubscribers() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(QueryNames.GET_ALL_EMAIL_SUBSCRIBERS);
        return query.list();
    }

    @Override
    public boolean unsubscribeMobile(String mobile, List<Integer> zones, String reason) {
        Query q = sessionFactory.getCurrentSession().createQuery(
                "update MobileSubscriber set subscribed = 0, reasonUnsubscription=:reason where mobile = :mobile and zoneId in (:zones)");
        q.setParameter("mobile", mobile);
        q.setParameterList("zones", zones);
        q.setParameter("reason", reason);
        return q.executeUpdate() != 0;
    }

    @Override
    public boolean unsubscribeEmail(String email, List<Integer> zones, String reason) {
        Query q = sessionFactory.getCurrentSession().createQuery(
                "update EmailSubscriber set subscribed = 0, reasonUnsubscription=:reason where email = :email and zoneId in (:zones)");
        q.setParameter("email", email);
        q.setParameterList("zones", zones);
        q.setParameter("reason", reason);
        return q.executeUpdate() != 0;
    }

    @Override
    public List<EmailMobileAssociation> getEmailAssociationByEmail(String email) {
        Query q = sessionFactory.getCurrentSession().createQuery("from EmailMobileAssociation where email=:email");
        q.setParameter("email", email);
        return q.list();
    }

    @Override
    public List<EmailMobileAssociation> getMobileAssociationByMobile(String mobile) {
        Query q = sessionFactory.getCurrentSession().createQuery("from EmailMobileAssociation where mobile=:mobile");
        q.setParameter("mobile", mobile);
        return q.list();
    }

    @Override
    public List<MobileSubscriber> getMobileSubscribers(int cityId, DateRange dateRange, boolean subscribed, boolean dnd, boolean isCustomer, int firstResult, int maxResults) {
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

    /*public List<MobileSubscriber> getMobileSubscribers(List<Integer> zoneIds, DateRange dateRange, boolean subscribed, boolean dnd, boolean isCustomer, int firstResult,
            int maxResults) {
        String queryStr = "from MobileSubscriber where subscribed =:subscribed and dnd =:dnd and customer = :customer and zoneId in (:zoneIds)";
        queryStr += (dateRange.getStart() != null) ? " and created > :start" : "";
        queryStr += (dateRange.getEnd() != null) ? " and created <= :end" : "";
        queryStr += " order by mobile";
        Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
        query.setParameterList("zoneIds", zoneIds);
        query.setParameter("subscribed", subscribed);
        query.setParameter("dnd", dnd);
        query.setParameter("customer", isCustomer);
        if (dateRange.getStart() != null) {
            query.setParameter("start", dateRange.getStart());
        }
        if (dateRange.getEnd() != null) {
            query.setParameter("end", dateRange.getEnd());
        }

        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }*/

    @Override
    public boolean unsubscribeEmail(String email, String reason) {
        Query q = sessionFactory.getCurrentSession().createQuery("update EmailSubscriber set subscribed = 0, reasonUnsubscription=:reason where email = :email");
        q.setParameter("email", email);
        q.setParameter("reason", reason);
        return q.executeUpdate() != 0;
    }

    @Override
    public boolean unsubscribeMobile(String mobile, String reason) {
        Query q = sessionFactory.getCurrentSession().createQuery("update MobileSubscriber set subscribed = 0, reasonUnsubscription=:reason where mobile = :mobile");
        q.setParameter("mobile", mobile);
        q.setParameter("reason", reason);
        return q.executeUpdate() != 0;
    }

    @Override
    public List<EmailSubscriber> getEmailSubscribersIncremental(Integer cityId, Integer lastUpdated, int firstResult, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select es.* from email_subscriber es,zone z where es.zone_id = z.id and z.city_id = :cityId and updated > :updated").addEntity(EmailSubscriber.class);
        query.setParameter("cityId", cityId);
        if (lastUpdated == null) {
            // Fetch the last 7 days subscribers by default.
            lastUpdated = 7;
        }
        query.setParameter("updated", DateUtils.addToDate(DateUtils.getCurrentTime(), Calendar.DATE, -(lastUpdated)));
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }
    
    @Override
    public List<EmailSubscriber> getEmailSubscribersIncrementalByZone(Integer zoneId, Integer lastUpdated, int firstResult, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select es.* from email_subscriber es where es.zone_id = :zoneId and updated > :updated").addEntity(EmailSubscriber.class);
        query.setParameter("zoneId", zoneId);
        if (lastUpdated == null) {
            // Fetch the last 7 days subscribers by default.
            lastUpdated = 7;
        }
        query.setParameter("updated", DateUtils.addToDate(DateUtils.getCurrentTime(), Calendar.DATE, -(lastUpdated)));
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }

    @Override
    public List<EmailSubscriber> getEmailAllSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailSubscriber where updated > :updated ");
        if (lastUpdated == null) {
            // Fetch the last 7 days subscribers by default.
            lastUpdated = 7;
        }
        query.setParameter("updated", DateUtils.addToDate(DateUtils.getCurrentTime(), Calendar.DATE, -(lastUpdated)));
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }

    @Override
    public List<EmailSubscriber> getNoPreferenceEmailSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailSubscriber where updated > :updated and preference is null");
        if (lastUpdated == null) {
            // Fetch the last 7 days subscribers by default.
            lastUpdated = 7;
        }
        query.setParameter("updated", DateUtils.addToDate(DateUtils.getCurrentTime(), Calendar.DATE, -(lastUpdated)));
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }

    @Override
    public List<MobileSubscriber> getAllMobileSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createQuery("from MobileSubscriber where updated > :updated");
        if (lastUpdated == null) {
            // Fetch the last 7 days subscribers by default.
            lastUpdated = 7;
        }
        query.setParameter("updated", DateUtils.addToDate(DateUtils.getCurrentTime(), Calendar.DATE, -(lastUpdated)));
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }

    @Override
    public boolean updateMobileSubscriberVerification(String mobile, String pin) {
        Query q = sessionFactory.getCurrentSession().createQuery("update MobileSubscriber set pin = :pin where mobile = :mobile");
        q.setParameter("mobile", mobile);
        q.setParameter("pin", pin);
        return q.executeUpdate() != 0;
    }

    @Override
    public List<MobileSubscriber> searchZonesByMobile(String searchParam, String pin) {
        Query query = sessionFactory.getCurrentSession().createQuery("from MobileSubscriber where subscribed = 1 and mobile = :searchParam and pin=:pin");
        query.setParameter("searchParam", searchParam);
        query.setParameter("pin", pin);
        return query.list();
    }

    @Override
    public boolean unsububscribeMobileFromZones(String mobile, List<Integer> zoneIds, String reason, String suggestion) {
        Query q = sessionFactory.getCurrentSession().createQuery(
                "update MobileSubscriber set subscribed = 0 ,reasonUnsubscription=:reason ,suggestionUnsubscription=:suggestion  where mobile = :mobile and zoneId in (:zoneIds)");
        q.setParameter("mobile", mobile);
        q.setParameterList("zoneIds", zoneIds);
        q.setParameter("reason", reason);
        q.setParameter("suggestion", suggestion);
        return q.executeUpdate() != 0;
    }

    @Override
    public boolean unsububscribeEmailFromZones(String email, List<Integer> zoneIds, String reason, String suggestion) {
        Query q = sessionFactory.getCurrentSession().createQuery(
                "update EmailSubscriber set subscribed = 0 ,reasonUnsubscription=:reason,suggestionUnsubscription=:suggestion ,updated = :updated where email = :email and zoneId in (:zoneIds)");
        q.setParameter("email", email);
        q.setParameter("updated", DateUtils.getCurrentTime());
        q.setParameterList("zoneIds", zoneIds);
        q.setParameter("reason", reason);
        q.setParameter("suggestion", suggestion);
        return q.executeUpdate() != 0;
    }

    @Override
    public MobileSubscriberDetail getMobileSubscriberDetailByMobile(String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("from MobileSubscriberDetail where mobile =:mobile");
        query.setParameter("mobile", mobile);
        return (MobileSubscriberDetail) query.uniqueResult();
    }

    @Override
    public EmailSubscriberDetail getEmailSubscriberDetailByUID(String uid) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailSubscriberDetail where uid =:uid");
        query.setParameter("uid", uid);
        return (EmailSubscriberDetail) query.uniqueResult();
    }

    @Override
    public List<EmailSubscriberDetail> getNewSubscriberInDateRange(Date startDate, Date endDate, String channel) {
        Query query = null;
        if (channel != null) {
            query = sessionFactory.getCurrentSession().createQuery(
                    "select distinct esd from EmailSubscriberDetail esd, EmailSubscriber es where esd.created >= :startTime and esd.created < :endTime and esd.email = es.email and es.channelCode = :channel and es.subscribed = true");
            query.setParameter("channel", channel);
        } else {
            query = sessionFactory.getCurrentSession().createQuery(
                    "select distinct esd from EmailSubscriberDetail esd, EmailSubscriber es where esd.created >= :startTime and esd.created < :endTime and esd.email = es.email and es.subscribed = true");
        }

        query.setParameter("endTime", endDate);
        query.setParameter("startTime", startDate);
        return query.list();
    }

    @Override
    public List<MobileSubscriberDetail> getNewMobileSubscriberByChannelInDateRange(DateRange range, String channel) {
        Query query = null;
        query = sessionFactory.getCurrentSession().createQuery(
                "select distinct msd from MobileSubscriberDetail msd, MobileSubscriber ms where msd.created >= :startTime and msd.created < :endTime and msd.mobile = ms.mobile and ms.channelCode = :channel ");
        query.setParameter("channel", channel);
        query.setParameter("startTime", range.getStart());
        query.setParameter("endTime", range.getEnd());
        return query.list();
    }

    @Override
    public List<Object[]> getMobileSubscribersAndDNDStatus(int firstResult, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("select mobile, dnd from mobile_subscriber order by id");
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        return query.list();
    }

    @Override
    public void updateDNDStatus(String mobile, boolean dndStatus) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery("update mobile_subscriber set dnd=:status where mobile=:mobile");
        query.setParameter("status", dndStatus ? 1 : 0);
        query.setParameter("mobile", mobile);
        query.executeUpdate();
    }

    @Override
    public List<Integer> getZonesByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("select zoneId from EmailSubscriber where normalizedEmail=:email");
        query.setParameter("email", StringUtils.normalizeEmail(email));
        return query.list();
    }

    @Override
    public List<Integer> getZonesByMobile(String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("select zoneId from MobileSubscriber where mobile=:mobile");
        query.setParameter("mobile", mobile);
        return query.list();
    }

    @Override
    public SubscriberProfile persistSubscriberProfile(SubscriberProfile profile) {
        sessionFactory.getCurrentSession().persist(profile);
        return profile;
    }

    @Override
    public SubscriberProfile updateSubscriberProfile(SubscriberProfile profile) {
        return (SubscriberProfile) sessionFactory.getCurrentSession().merge(profile);
    }

    @Override
    public SubscriberProfile getSubscriberProfileById(Integer id) {
    	 Query query = sessionFactory.getCurrentSession().createQuery("from SubscriberProfile where id = :id");
         query.setParameter("id", id);
         return (SubscriberProfile) query.uniqueResult();
    }
    
    @Override
    public SubscriberProfile getSubscriberProfile(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from SubscriberProfile where email = :email");
        query.setParameter("email", email);
        return (SubscriberProfile) query.uniqueResult();
    }

    public static void main(String[] args) {
        System.out.println(StringUtils.normalizeEmail("anujupad.h.y.ay91@gmail.com"));
    }

    @Override
    public EmailSubscriber findEmailSubscriberWithPreferenceByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery("from EmailSubscriber where email = :email and preference is not null and preference !=''");
        query.setParameter("email", email);
        query.setMaxResults(1);
        return (EmailSubscriber) query.uniqueResult();
    }

    @Override
    public AffiliateSubscriptionOffer getAffiliateSubscriptionOfferByName(String offerName) {
        Query query = sessionFactory.getCurrentSession().createQuery("from AffiliateSubscriptionOffer where offerName=:name");
        query.setParameter("name", offerName);
        return (AffiliateSubscriptionOffer) query.uniqueResult();
    }
    
    @Override
    public List<AffiliateSubscriptionOffer> getAllAffiliateSubscriptionOffers() {
        Query query = sessionFactory.getCurrentSession().createQuery("from AffiliateSubscriptionOffer");
        return query.list();
    }

    @Override
    public List<AffiliateSubscriptionOffer> getAllEnabledAffiliateSubscriptionOffers() {
        Query query = sessionFactory.getCurrentSession().createQuery("from AffiliateSubscriptionOffer where enabled=1 order by updated desc");
        return query.list();
    }
    
    @Override
    public void mergeAffiliateSubscriptionOffer(AffiliateSubscriptionOffer subscriptionOffer) {
        sessionFactory.getCurrentSession().merge(subscriptionOffer);
    }

    @Override
    public AffiliateSubscriptionOffer getSubscriptionOfferById(int id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from AffiliateSubscriptionOffer where id=:id");
        query.setParameter("id", id);
        return (AffiliateSubscriptionOffer) query.uniqueResult();
    }

    @Override
    public void deleteSubscriber(String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from MobileSubscriber where mobile = :mobile");
        query.setParameter("mobile", mobile);
        query.executeUpdate();
    }

    @Override
    public void deleteSubscriberDetail(String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from MobileSubscriberDetail where mobile = :mobile");
        query.setParameter("mobile", mobile);
        query.executeUpdate();
    }

    @Override
    public void deleteEmailMobileAssociation(String mobile) {
        Query query = sessionFactory.getCurrentSession().createQuery("delete from EmailMobileAssociation where mobile = :mobile");
        query.setParameter("mobile", mobile);
        query.executeUpdate();
    }

    @Override
    public EmailMobileAssociation getEmailAssociationById(Integer id) {
        Query q = sessionFactory.getCurrentSession().createQuery("from EmailMobileAssociation where id=:id");
        q.setParameter("id", id);
        return (EmailMobileAssociation) q.uniqueResult();
    }

    @Override
    public List<EmailSubscriber> getEmailSubscribersIncrementalByZoneInDateRange(long id, Integer zoneId, Date start, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select es.* from email_subscriber es where es.zone_id = :zoneId and updated >= :start and id > :id order by id asc").addEntity(EmailSubscriber.class);
        query.setParameter("zoneId", zoneId);
        query.setParameter("start", start);
        query.setParameter("id", id);
        query.setMaxResults(maxResults);
        return query.list();
    }
    
    @Override
    public List<EmailSubscriber> getEmailAllSubscribersIncrementalInDateRange(long id, Date start, int maxResults) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(
                "select * from email_subscriber where updated >= :start and id > :id order by id asc").addEntity(EmailSubscriber.class);
        query.setParameter("start", start);
        query.setParameter("id", id);
        query.setMaxResults(maxResults);
        return query.list();
    }   
}
