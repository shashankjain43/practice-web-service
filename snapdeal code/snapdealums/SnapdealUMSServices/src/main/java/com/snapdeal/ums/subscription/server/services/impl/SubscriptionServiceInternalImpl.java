/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 18, 2010
 *  @author rahul
 */
package com.snapdeal.ums.subscription.server.services.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.memcached.service.IMemcachedService;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.base.utils.ValidatorUtils;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.core.cachable.MobileSubscriberPin;
import com.snapdeal.core.cachable.Subscriber;
import com.snapdeal.core.dto.ZoneDTO;
import com.snapdeal.core.model.subscribercitymigration.SubscriberCityMigration;
import com.snapdeal.core.utils.RequestContext;
import com.snapdeal.ums.cache.ZonesCache;
import com.snapdeal.ums.constants.StringConstants;
import com.snapdeal.ums.core.entity.AffiliateSubscriptionOffer;
import com.snapdeal.ums.core.entity.CustomerFilter;
import com.snapdeal.ums.core.entity.EmailMobileAssociation;
import com.snapdeal.ums.core.entity.EmailSubscriber;
import com.snapdeal.ums.core.entity.EmailSubscriberDetail;
import com.snapdeal.ums.core.entity.MobileSubscriber;
import com.snapdeal.ums.core.entity.MobileSubscriberDetail;
import com.snapdeal.ums.core.entity.SubscriberProfile;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;
import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.ums.dao.subscriptions.IMobileSubscriberDao;
import com.snapdeal.ums.dao.subscriptions.ISubscriptionsDao;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsServiceInternal;

@Transactional
@Service("subscriptionServiceInternal")
public class SubscriptionServiceInternalImpl implements ISubscriptionsServiceInternal {

    private static final Logger         LOG = LoggerFactory.getLogger(SubscriptionServiceInternalImpl.class);

    private ISubscriptionsDao           subscriptionsDao;

    @Autowired
    private IMobileSubscriberDao        mobileSubscriberDao;

    private IMemcachedService           cache;

//    @Autowired
//    private ISubscriberCityMigrationMao subscriberCityMigrationMao;

    @Autowired
    public void setSubscriptionsDao(ISubscriptionsDao subscriptionsDao) {
        this.subscriptionsDao = subscriptionsDao;
    }

    @Autowired
    public void setMemcachedService(IMemcachedService cache) {
        this.cache = cache;
    }

    @Override
    public boolean addSubscriber(final String emailId, String mobile,final int zoneId,final String subscriptionPage,
    		final boolean overrideUnsubscription,final boolean isSubscribed,
            final String affiliateTrackingCode,final String utmTrackingSource) {
      
    	LOG.info(StringConstants.ADD_SUBSCRIBER2,emailId ,mobile);
        
        RequestContext.current().getVisitTrackingParams().put(Constants.TRACKING_PARAMETER_UTM_SOURCE, utmTrackingSource);
        boolean emailSubscribed = false;
        boolean mobileSubscribed = false;
        boolean citySubscribed = false;
        List<Integer> zoneIds = null;
        ZoneSRO currentZone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zoneId);

        try {
            if (ValidatorUtils.isEmailValid(emailId)) {
                zoneIds = getZonesByEmail(emailId);
                for (Integer subscriberZoneId : zoneIds) {
                    ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(subscriberZoneId);
                    if (currentZone.getCity().getId() == zone.getCity().getId()) {
                        if (!isSubscribed) {
                            citySubscribed = true;
                            break;
                        } else {
                            EmailSubscriber emailSubscriber = subscriptionsDao.findEmailSubscriber(emailId, subscriberZoneId);
                            if (emailSubscriber.getSubscribed().equals(isSubscribed)) {
                                citySubscribed = true;
                                break;
                            }
                        }
                    }
                }
                if (!citySubscribed) {
                    emailSubscribed = addEmailSubscriber(emailId, zoneId, subscriptionPage, overrideUnsubscription, isSubscribed, affiliateTrackingCode, utmTrackingSource);
                }
            }

            citySubscribed = false;
            mobile = ValidatorUtils.getValidMobileOrNull(mobile);
            if (!StringUtils.isEmpty(mobile)) {
                zoneIds = getZonesByMobile(mobile);
                for (Integer subscriberZoneId : zoneIds) {
                    ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(subscriberZoneId);
                    if (currentZone.getCity().getId() == zone.getCity().getId()) {
                        if (!isSubscribed) {
                            citySubscribed = true;
                            break;
                        } else {
                            MobileSubscriber mobileSubscriber = subscriptionsDao.findMobileSubscriber(mobile, subscriberZoneId);
                            if (mobileSubscriber.getSubscribed().equals(isSubscribed)) {
                                citySubscribed = true;
                                break;
                            }
                        }
                    }
                }
                if (!citySubscribed) {
                    mobileSubscribed = addMobileSubscriber(mobile, zoneId, subscriptionPage, overrideUnsubscription, isSubscribed, affiliateTrackingCode, utmTrackingSource);
                }
            }

            if (!StringUtils.isEmpty(emailId) && !StringUtils.isEmpty(mobile)) {
                EmailMobileAssociation association = new EmailMobileAssociation(emailId, mobile, true,DateUtils.getCurrentTime());
                addEmailMobileAssociation(association);
            }
        } catch (Exception e) {
            // Catch all the exceptions. Subscription should not affect any other application.
            LOG.error("Error subscribing email {}, mobile {} for zone {}", new Object[] { emailId, mobile, zoneId });
            LOG.error("Stack trace: ", e);
        }
        LOG.info("Finished add Subscriber for email "+ emailId + " mobile ="+ mobile);
        
        return (emailSubscribed || mobileSubscribed);
    }

    @Override
    public boolean addEmailSubscriber(String email, int zoneId, String subscriptionPage, boolean overrideUnsubscription, boolean isSubscribed, String affiliateTrackingCode,
            String utmTrackingSource) {

        RequestContext.current().getVisitTrackingParams().put(Constants.TRACKING_PARAMETER_UTM_SOURCE, utmTrackingSource);
        for (Integer zId : CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zoneId).getCity().getZoneIds()) {
            ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zId);
            ZonesCache cache = CacheManager.getInstance().getCache(ZonesCache.class);

            EmailSubscriber emailSubscriber = subscriptionsDao.findEmailSubscriber(email, zone.getId());
            if ((zone.getPageUrl().equalsIgnoreCase(Constants.DUMMY_CITY)) && (getEmailSubscriptions(email).size() != 0)) {
                return false;
            } else {
                if (emailSubscriber != null) {
                    if ((emailSubscriber.getSubscribed() || !overrideUnsubscription) && zoneId == zone.getId())
                        return false;
                    else {
                        emailSubscriber.setSubscribed(true);
                    }
                } else {
                    ZoneSRO dummyZone = cache.getZoneByPageUrl(Constants.DUMMY_CITY);
                    EmailSubscriber subscriber = subscriptionsDao.findEmailSubscriber(email, dummyZone.getId());

                    if (zoneId == zone.getId()) {
                        if (subscriber != null) {
                            //unsubscribe from dummy city and add new subscription with the new zone selected

                            subscriber.setSubscribed(false);
                            LOG.info("unsubscribing: {} for zone: {}", email, dummyZone.getName());
                            subscriptionsDao.persistEmailSubscriber(subscriber);

                        }
                        emailSubscriber = new EmailSubscriber(email, zone.getId(), isSubscribed);
                        emailSubscriber.setSubscriptionPage(subscriptionPage);
                        emailSubscriber.setAffiliateTrackingCode(affiliateTrackingCode);

                    } else {
                        continue;
                    }
                }
                LOG.info("subscribing: {} for zone: {}", email, CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zone.getId()).getName());
                subscriptionsDao.persistEmailSubscriber(emailSubscriber);
            }

        }

        return true;
    }

    @Override
    public boolean createMobileSubscriber(String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription, String pin, String affiliateTrackingCode,
            String utmTrackingSource) {
        RequestContext.current().getVisitTrackingParams().put(Constants.TRACKING_PARAMETER_UTM_SOURCE, utmTrackingSource);

        for (Integer zId : CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zoneId).getCity().getZoneIds()) {
            ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zId);
            ZonesCache cache = CacheManager.getInstance().getCache(ZonesCache.class);

            MobileSubscriber mobileSubscriber = subscriptionsDao.findMobileSubscriber(mobile, zone.getId());
            if ((zone.getPageUrl().equalsIgnoreCase(Constants.DUMMY_CITY)) && (getMobileSubscriptions(mobile).size() != 0)) {
                return false;
            } else {
                if (mobileSubscriber != null) {
                    if ((mobileSubscriber.getSubscribed() || !overrideUnsubscription) && zoneId == zone.getId())
                        return false;
                    else {
                        mobileSubscriber.setSubscribed(true);
                    }
                } else {
                    ZoneSRO dummyZone = cache.getZoneByPageUrl(Constants.DUMMY_CITY);
                    MobileSubscriber subscriber = subscriptionsDao.findMobileSubscriber(mobile, dummyZone.getId());

                    if (zoneId == zone.getId()) {

                        if (subscriber != null) {
                            //unsubscribe from dummy zone and add new subscription with the new zone selected

                            subscriber.setSubscribed(false);
                            LOG.info("unsubscribing mobile: {} for zone: {} ", mobile, dummyZone.getName());
                            subscriptionsDao.persistMobileSubscriber(subscriber);

                        }
                        mobileSubscriber = new MobileSubscriber(mobile, zone.getId(), true);
                        mobileSubscriber.setSubscriptionPage(subscriptionPage);
                        mobileSubscriber.setAffiliateTrackingCode(affiliateTrackingCode);
                    } else {
                        continue;
                    }

                }
                LOG.info("subscribing mobile: {} for zone: {} ", mobile, CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zone.getId()).getName());
                subscriptionsDao.persistMobileSubscriber(mobileSubscriber);
            }

        }

        if (StringUtils.isNotEmpty(pin)) {
            MobileSubscriberPin mobileSubscriberPin = new MobileSubscriberPin();
            mobileSubscriberPin.setPin(pin);
            cache.put(Constants.MEMCACHE_KEY_PREFIX_SUBSCRIBER_PIN + mobile, mobileSubscriberPin, 86400);
        }

       return true;
    }

    @Override
    public String getMobileSubscriberPin(String mobile) {
        MobileSubscriberPin mobileSubscriberPin = cache.get(Constants.MEMCACHE_KEY_PREFIX_SUBSCRIBER_PIN + mobile, MobileSubscriberPin.class);
        if (mobileSubscriberPin != null) {
            return mobileSubscriberPin.getPin();
        }
        return null;
    }

    @Override
    public boolean addMobileSubscriber(String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription, boolean isSubscribed, String affiliateTrackingCode,
            String utmTrackingSource) {
        RequestContext.current().getVisitTrackingParams().put(Constants.TRACKING_PARAMETER_UTM_SOURCE, utmTrackingSource);
        for (Integer zId : CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zoneId).getCity().getZoneIds()) {
            ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zId);
            ZonesCache cache = CacheManager.getInstance().getCache(ZonesCache.class);

            MobileSubscriber mobileSubscriber = subscriptionsDao.findMobileSubscriber(mobile, zone.getId());

            if ((zone.getPageUrl().equalsIgnoreCase(Constants.DUMMY_CITY)) && (getMobileSubscriptions(mobile).size() != 0)) {
                return false;
            } else {
                if (mobileSubscriber != null) {
                    if ((mobileSubscriber.getSubscribed() || !overrideUnsubscription) && zoneId == zone.getId())
                        return false;
                    else {
                        mobileSubscriber.setSubscribed(true);
                    }
                } else {
                    ZoneSRO dummyZone = cache.getZoneByPageUrl(Constants.DUMMY_CITY);
                    MobileSubscriber subscriber = subscriptionsDao.findMobileSubscriber(mobile, dummyZone.getId());

                    if (zoneId == zone.getId()) {

                        if (subscriber != null) {
                            //unsubscribe from dummy zone and add new subscription with the new zone selected

                            subscriber.setSubscribed(false);
                            LOG.info("unsubscribing mobile: {} for zone: {} ", mobile, dummyZone.getName());
                            subscriptionsDao.persistMobileSubscriber(subscriber);

                        }
                        mobileSubscriber = new MobileSubscriber(mobile, zone.getId(), true);
                        mobileSubscriber.setSubscriptionPage(subscriptionPage);
                        mobileSubscriber.setAffiliateTrackingCode(affiliateTrackingCode);
                    } else {
                        continue;
                    }
                }
                LOG.info("subscribing mobile: {} for zone: {} ", mobile, CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zone.getId()).getName());
                subscriptionsDao.persistMobileSubscriber(mobileSubscriber);
            }

        }
        /* To be used later
        // Adding the subscriber to the memcached
        // TODO: replace the subscriptionsDao.findMobileSubscriber with this check later
        Subscriber subscriber = cache.get(Constants.MEMCACHE_KEY_PREFIX_SUBSCRIBER + mobile, Subscriber.class);
        if(subscriber == null || subscriber.getSubscribed() == Subscriber.SUBSCRIBED_NO) {
            subscriber = new Subscriber(Subscriber.SUBSCRIBED_YES);
            cache.put(Constants.MEMCACHE_KEY_PREFIX_SUBSCRIBER + mobile, subscriber, 0);
        }
        */

        return true;
    }

    @Override
    public MobileSubscriberDetail createMobileSubscriberDetail(String mobile) {
        MobileSubscriberDetail detail = subscriptionsDao.persistMobileSubscriberDetail(new MobileSubscriberDetail(mobile, false));
        return detail;
    }

    @Override
    public boolean unsubscribeEmail(String email, int zoneId, String reason) {
        ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zoneId);
        List<Integer> zones = new ArrayList<Integer>();
        for (ZoneSRO z : CacheManager.getInstance().getCache(ZonesCache.class).getZonesByCityId(zone.getCity().getId())) {
            zones.add(z.getId());
        }
        return subscriptionsDao.unsubscribeEmail(email, zones, reason);
    }

    @Override
    public boolean unsubscribeMobile(String mobile, int zoneId, String reason) {
        ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zoneId);
        List<Integer> zones = new ArrayList<Integer>();
        for (ZoneSRO z : CacheManager.getInstance().getCache(ZonesCache.class).getZonesByCityId(zone.getCity().getId())) {
            zones.add(z.getId());
        }
        return subscriptionsDao.unsubscribeMobile(mobile, zones, reason);
    }

    @Override
    public EmailSubscriberDetail getEmailSubscriberDetailByEmail(String email) {
        return subscriptionsDao.findEmailSubscriberDetailByEmail(email);
    }

    @Override
    public EmailSubscriberDetail createEmailSubscriberDetail(String email, String code) {
        EmailSubscriberDetail emailSubscriberDetail = subscriptionsDao.persistEmailSubscriberDetail(new EmailSubscriberDetail(email, code, false));
        return emailSubscriberDetail;
    }

    @Override
    public void updateEmailSubscriberVerification(EmailSubscriberDetail emailSubscriberVerification) {
        subscriptionsDao.updateEmailSubscriberDetail(emailSubscriberVerification);
    }

    @Override
    public void addEmailMobileAssociation(EmailMobileAssociation association) {
        if (!isEmailMobileAssociation(association.getEmail(), association.getMobile())) {
            subscriptionsDao.persistEmailMobileAssociation(association);
        }
    }

    @Override
    public void updateEmailMobileAssociation(EmailMobileAssociation association) {
        subscriptionsDao.updateEmailMobileAssociation(association);

    }

    @Override
    public boolean isEmailMobileAssociation(String email, String mobile) {
        EmailMobileAssociation emailmobileassociation = subscriptionsDao.findEmailMobileAssociation(email, mobile);
        return emailmobileassociation != null;
    }

    @Override
    public List<EmailSubscriber> getEmailSubscriptions(String email) {
        return subscriptionsDao.findEmailSubscriptionsByEmail(email);
    }

    @Override
    public List<MobileSubscriber> getMobileSubscriptions(String mobile) {
        return subscriptionsDao.findMobileSubscriptionsByMobile(mobile);
    }

    @Override
    public List<MobileSubscriber> getSubscribedMobileSubscribers(String mobile) {
        return subscriptionsDao.getSubscribedMobileSubscribers(mobile);
    }

    @Override
    public List<EmailSubscriber> getEmailSubscriberByZone(int zoneId, Date startTime, Date endTime) {
        return subscriptionsDao.findEmailSubscribersByZone(zoneId, startTime, endTime);
    }

    @Override
    public List<MobileSubscriber> getMobileSubscriberByZone(int zoneId, Date startTime, Date endTime) {
        return subscriptionsDao.findMobileSubscribersByZone(zoneId, startTime, endTime);
    }

    public void updateEmailSubscriberMemcache() {
        List<EmailSubscriber> subscribers = subscriptionsDao.getAllEmailSubscribers();
        for (EmailSubscriber subscriber : subscribers) {
            cache.put(Constants.MEMCACHE_KEY_PREFIX_SUBSCRIBER + subscriber.getEmail(), new Subscriber(Subscriber.SUBSCRIBED_YES), 0);
        }
    }

    @Override
    public MobileSubscriber getMobileSubscriber(String mobile, int zone) {
        return subscriptionsDao.findMobileSubscriber(mobile, zone);
    }

    @Override
    public EmailSubscriber getEmailSubscriber(String email, int zone) {
        return subscriptionsDao.findEmailSubscriber(email, zone);
    }

    @Override
    public boolean addMobileSubscriberWithPin(String mobile, int zoneId, String pin, String channel, String affiliateTrackingCode, String utmTrackingSource) {
        RequestContext.current().getVisitTrackingParams().put(Constants.TRACKING_PARAMETER_UTM_SOURCE, utmTrackingSource);
        for (Integer zId : CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zoneId).getCity().getZoneIds()) {
            ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zId);
            ZonesCache cache = CacheManager.getInstance().getCache(ZonesCache.class);

            MobileSubscriber mobileSubscriber = subscriptionsDao.findMobileSubscriber(mobile, zone.getId());
            if ((zone.getPageUrl().equalsIgnoreCase(Constants.DUMMY_CITY)) && (getMobileSubscriptions(mobile).size() != 0)) {
                return false;
            } else {
                if (mobileSubscriber != null) {
                    if (mobileSubscriber.getSubscribed() && zoneId == zone.getId())
                        return false;
                    else
                        mobileSubscriber.setSubscribed(true);
                } else {
                    if (zoneId == zone.getId()) {
                        ZoneSRO dummyZone = cache.getZoneByPageUrl(Constants.DUMMY_CITY);
                        MobileSubscriber subscriber = subscriptionsDao.findMobileSubscriber(mobile, dummyZone.getId());

                        if (subscriber != null) {
                            //delete dummy city subscription and add new subscription with the new zone selected
                            subscriber.setSubscribed(false);
                            LOG.info("unsubscribing mobile: {} for zone: {} ", mobile, dummyZone.getName());
                            subscriptionsDao.persistMobileSubscriber(subscriber);
                        }
                        mobileSubscriber = new MobileSubscriber(mobile, zone.getId(), true);
                        mobileSubscriber.setCreated(DateUtils.getCurrentTime());
                        mobileSubscriber.setPin(pin);
                        mobileSubscriber.setSubscriptionPage(channel);
                        mobileSubscriber.setAffiliateTrackingCode(affiliateTrackingCode);
                    } else {
                        continue;
                    }
                }
                LOG.info("subscribing mobile: {} for zone: {} ", mobile, CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(zone.getId()).getName());
                subscriptionsDao.persistMobileSubscriber(mobileSubscriber);
            }

        }
         return true;
    }

    @Override
    public List<EmailMobileAssociation> getEmailAssociationByEmail(String email) {
        return subscriptionsDao.getEmailAssociationByEmail(email);
    }

    @Override
    public List<EmailMobileAssociation> getMobileAssociationByMobile(String mobile) {
        return subscriptionsDao.getMobileAssociationByMobile(mobile);
    }

    @Override
    public List<MobileSubscriber> getMobileSubscribers(int cityId, String filterType, boolean subscribed, boolean dnd, int firstResult, int maxResults) {
        boolean isCustomer = false;
        Date today = DateUtils.getCurrentDate();
        Date endDate = today;
        Date startDate = null;
        CustomerFilter.FilterType filterTypeEnum = CustomerFilter.FilterType.valueOf(filterType);
        switch (filterTypeEnum) {
            case SMS_CUSTOMER_ALL:
                isCustomer = true;
                break;
            case SMS_SUBSCRIBER_60_DAY:
                startDate = DateUtils.addToDate(today, Calendar.DATE, -60);
                break;
            case SMS_SUBSCRIBER_120_DAY:
                endDate = DateUtils.addToDate(today, Calendar.DATE, -60);
                startDate = DateUtils.addToDate(today, Calendar.DATE, -120);
                break;
            case SMS_SUBSCRIBER_240_DAY:
                endDate = DateUtils.addToDate(today, Calendar.DATE, -120);
                startDate = DateUtils.addToDate(today, Calendar.DATE, -240);
                break;
            case SMS_SUBSCRIBER_OLD:
                endDate = DateUtils.addToDate(today, Calendar.DATE, -240);
                break;
        }
        DateRange range = new DateRange(startDate, endDate);
        //return subscriptionsDao.getMobileSubscribers(cityId, range, subscribed, dnd, isCustomer, firstResult, maxResults);
        return mobileSubscriberDao.getMobileSubscribersFromReplica(cityId, range, subscribed, dnd, isCustomer, firstResult, maxResults);
    }

    @Override
    public List<EmailSubscriber> getEmailSubscribersIncremental(Integer cityId, Integer lastUpdated, int firstResult, int maxResults) {
        List<EmailSubscriber> emailSubscribers = new ArrayList<EmailSubscriber>();
        for(ZoneSRO zone: CacheManager.getInstance().getCache(ZonesCache.class).getZonesByCityId(cityId)){
            emailSubscribers.addAll(subscriptionsDao.getEmailSubscribersIncrementalByZone(zone.getId(), lastUpdated, firstResult, maxResults));
        }
        
        return emailSubscribers;
    }
    
    @Override
    public List<EmailSubscriber> getEmailSubscribersIncrementalByZoneDateAndId(long id, Integer zoneId, Date start, int maxResults) {
            return subscriptionsDao.getEmailSubscribersIncrementalByZoneInDateRange(id, zoneId,  start, maxResults);
    }
    
    
    @Override
    public List<EmailSubscriber> getEmailSubscribersIncrementalByZone(Integer zoneId, Integer lastUpdated, int firstResult, int maxResults) {
        return subscriptionsDao.getEmailSubscribersIncrementalByZone(zoneId, lastUpdated, firstResult, maxResults);
    }

    @Override
    public List<EmailSubscriber> getEmailAllSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults) {
        return subscriptionsDao.getEmailAllSubscribersIncremental(lastUpdated, firstResult, maxResults);
    }

    @Override
    public List<EmailSubscriber> getEmailAllSubscribersIncrementalByDateAndId(long id, Date start, int maxResults) {
        return subscriptionsDao.getEmailAllSubscribersIncrementalInDateRange(id, start, maxResults);
    }
    @Override
    public boolean unsubscribeEmail(String email, String reason) {
        return subscriptionsDao.unsubscribeEmail(email, reason);
    }

    @Override
    public boolean unsubscribeMobile(String mobile, String reason) {
        return subscriptionsDao.unsubscribeMobile(mobile, reason);
    }

    
    @Override
    public List<MobileSubscriber> getAllMobileSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults) {
        return subscriptionsDao.getAllMobileSubscribersIncremental(lastUpdated, firstResult, maxResults);
    }

    @Override
    public boolean updateMobileSubscriberVerification(String mobile, String pin) {
        return subscriptionsDao.updateMobileSubscriberVerification(mobile, pin);
    }

    @Override
    public List<ZoneDTO> searchZonesByMobile(String searchParam, String pin) {
        List<ZoneDTO> zoneDTOs = new ArrayList<ZoneDTO>();
        List<Integer> cityList = new ArrayList<Integer>();
        for (MobileSubscriber mobileSubscriber : subscriptionsDao.searchZonesByMobile(searchParam, pin)) {
            ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class).getZoneById(mobileSubscriber.getZoneId());
            if (!cityList.contains(zone.getCity().getId())) {

                cityList.add(zone.getCity().getId());
                zoneDTOs.add(new ZoneDTO(zone.getCity().getId(), zone.getName(), zone.getCity().getName(), zone.getCity().getVisible()));
            }
        }
        return zoneDTOs;
    }

    @Override
    public boolean unsububscribeMobileFromZones(String mobile, List<Integer> zoneIds, String reason, String suggestion) {
        return subscriptionsDao.unsububscribeMobileFromZones(mobile, zoneIds, reason, suggestion);
    }

    @Override
    public boolean unsububscribeEmailFromZones(String email, List<Integer> zoneIds, String reason, String suggestion) {
        return subscriptionsDao.unsububscribeEmailFromZones(email, zoneIds, reason, suggestion);
    }

    @Override
    public MobileSubscriberDetail getMobileSubscriberDetailByMobile(String mobile) {
        return subscriptionsDao.getMobileSubscriberDetailByMobile(mobile);
    }

    @Override
    public EmailSubscriberDetail getEmailSubscriberDetailByUID(String uid) {
        return subscriptionsDao.getEmailSubscriberDetailByUID(uid);
    }

    @Override
    public List<EmailSubscriberDetail> getNewSubscriberInDateRange(Date startDate, Date endDate, String channel) {
        return subscriptionsDao.getNewSubscriberInDateRange(startDate, endDate, channel);
    }

    @Override
    public List<MobileSubscriberDetail> getNewMobileSubscriberByChannelInDateRange(DateRange range, String channel) {
        return subscriptionsDao.getNewMobileSubscriberByChannelInDateRange(range, channel);
    }

    @Override
    public List<Object[]> getMobileSubscribersAndDNDStatus(int firstResult, int maxResults) {
        return subscriptionsDao.getMobileSubscribersAndDNDStatus(firstResult, maxResults);
    }

    @Override
    public void updateDNDStatus(String mobile, boolean dndStatus) {
        subscriptionsDao.updateDNDStatus(mobile, dndStatus);
    }

    @Override
    public EmailMobileAssociation getEmailMobileAssociation(String email, String mobile) {
        EmailMobileAssociation emailmobileassociation = subscriptionsDao.findEmailMobileAssociation(email, mobile);
        return emailmobileassociation;
    }

    @Override
    public List<Integer> getZonesByEmail(String email) {
        return subscriptionsDao.getZonesByEmail(email);
    }

    @Override
    public List<Integer> getZonesByMobile(String mobile) {
        return subscriptionsDao.getZonesByMobile(mobile);
    }

    @Override
    public void updateEmailSubscriberDetail(EmailSubscriberDetail esd) {
        subscriptionsDao.updateEmailSubscriberDetail(esd);
    }

    @Override
    public SubscriberProfile getSubscriberProfile(String email) {
        return subscriptionsDao.getSubscriberProfile(email);
    }

    @Override
    public SubscriberProfile createSubscriberProfile(SubscriberProfile profile) {
        return subscriptionsDao.persistSubscriberProfile(profile);
    }

    @Override
    public SubscriberProfile createSubscriberProfile(String email, String name, String displayName, String gender, Date birthday, Integer localityId, ZoneSRO zone) {
        SubscriberProfile profile = null;
        profile = getSubscriberProfile(email);
        if (profile != null)
            return profile;

        profile = new SubscriberProfile();
        profile.setEmail(email);
        if (localityId != null) {
            profile.setLocalityId(localityId);
        }
        if (gender != null) {
            profile.setGender(gender);
        }
        if (!StringUtils.isEmpty(name)) {
            profile.setName(name);
        }
        if (!StringUtils.isEmpty(displayName)) {
            profile.setDisplayName(displayName);
        }
        if (birthday != null) {
            profile.setDob(birthday);
        }
        if (zone != null) {
            profile.setZoneId(zone.getId());
        }
        return subscriptionsDao.persistSubscriberProfile(profile);
    }

    @Override
    public SubscriberProfile updateSubscriberProfile(SubscriberProfile profile) {
        return subscriptionsDao.updateSubscriberProfile(profile);
    }

    @Override
    public SubscriberProfile updateSubscriberProfile(SubscriberProfileSRO profileSro, String name, String displayName, String gender, Date birthday, Integer localityId, ZoneSRO zone) {
    	SubscriberProfile profile=subscriptionsDao.getSubscriberProfileById(profileSro.getId());
    	if (localityId != null) {
            profile.setLocalityId(localityId);
        }
        if (gender != null) {
            profile.setGender(gender);
        }
        if (!StringUtils.isEmpty(name)) {
            profile.setName(name);
        }
        if (!StringUtils.isEmpty(displayName)) {
            profile.setDisplayName(displayName);
        }
        if (birthday != null) {
            profile.setDob(birthday);
        }
        if (zone != null) {
            profile.setZoneId(zone.getId());
        }
        return subscriptionsDao.updateSubscriberProfile(profile);
    }

    @Override
    public void updateMobileSubscriber(MobileSubscriber mobileSubscriber) {
        subscriptionsDao.persistMobileSubscriber(mobileSubscriber);
    }

    @Override
    public void updateEmailSubscriber(EmailSubscriber emailSubscriber) {
        subscriptionsDao.persistEmailSubscriber(emailSubscriber);
    }

    @Override
    public List<EmailSubscriber> getEmailSubscriberByEmail(String email) {
        return subscriptionsDao.findEmailSubscriberByEmail(email);
    }

    @Override
    public SubscriberCityMigration getSubscriberCityMigration(String emailId, String city) {
//        return subscriberCityMigrationMao.getSubscriberCityMigration(emailId, city);
    	return null;
    }

    @Override
    public void saveSubscriberCityMigration(SubscriberCityMigration subscriberCityMigration) {
//        subscriberCityMigrationMao.saveSubscriberCityMigration(subscriberCityMigration);
    }

    public EmailSubscriber findEmailSubscriberWithPreferenceByEmail(String email) {
//        return subscriptionsDao.findEmailSubscriberWithPreferenceByEmail(email);

    	return null;
    }

    @Override
    public List<EmailSubscriber> getNoPreferenceEmailSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults) {
        return subscriptionsDao.getNoPreferenceEmailSubscribersIncremental(lastUpdated, firstResult, maxResults);
    }

    @Override
    public AffiliateSubscriptionOffer getAffiliateSubscriptionOfferByName(String offerName) {
        return subscriptionsDao.getAffiliateSubscriptionOfferByName(offerName);
    }

    @Override
    public MobileSubscriber findMobileSubscriberByMobile(String mobile) {
        return subscriptionsDao.findMobileSubscriberByMobile(mobile);
    }

    @Override
    public List<EmailSubscriber> getSubscribedEmailSubscribers(String email) {
        return subscriptionsDao.getSubscribedEmailSubscribers(email);
    }

    @Override
    public AffiliateSubscriptionOffer getSubscriptionOfferById(Integer id) {
        return subscriptionsDao.getSubscriptionOfferById(id);
    }

    @Override
    public void mergeAffiliateSubscriptionOffer(AffiliateSubscriptionOffer affiliateSubscriptionOffer) {
        subscriptionsDao.mergeAffiliateSubscriptionOffer(affiliateSubscriptionOffer);
        
    }

    @Override
    public List<AffiliateSubscriptionOffer> getAllAffiliateSubscriptionOffers() {
        return subscriptionsDao.getAllAffiliateSubscriptionOffers();
    }
    
    @Override
    public List<AffiliateSubscriptionOffer> getAllEnabledAffiliateSubscriptionOffers() {
        return subscriptionsDao.getAllEnabledAffiliateSubscriptionOffers();
    }

    @Override
    public void deleteSubscribers(String mobile) {
        subscriptionsDao.deleteSubscriber(mobile);
        subscriptionsDao.deleteSubscriberDetail(mobile);
        subscriptionsDao.deleteEmailMobileAssociation(mobile);
        
    }

    @Override
    public EmailMobileAssociation getEmailAssociationById(Integer id) {
            return subscriptionsDao.getEmailAssociationById(id);
    }

}
