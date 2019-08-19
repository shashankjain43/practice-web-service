/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 18, 2010
 *  @author rahul
 */
package com.snapdeal.ums.dao.subscriptions;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.ums.core.entity.AffiliateSubscriptionOffer;
import com.snapdeal.ums.core.entity.EmailMobileAssociation;
import com.snapdeal.ums.core.entity.EmailSubscriber;
import com.snapdeal.ums.core.entity.EmailSubscriberDetail;
import com.snapdeal.ums.core.entity.MobileSubscriber;
import com.snapdeal.ums.core.entity.MobileSubscriberDetail;
import com.snapdeal.ums.core.entity.SubscriberProfile;

public interface ISubscriptionsDao {

    public EmailSubscriber persistEmailSubscriber(EmailSubscriber emailSubscriber);

    public MobileSubscriber persistMobileSubscriber(MobileSubscriber mobileSubscriber);

    public EmailSubscriber findEmailSubscriber(String email, int zoneId);

    public MobileSubscriber findMobileSubscriber(String mobile, int zoneId);

    public EmailSubscriberDetail findEmailSubscriberDetailByEmail(String email);

    public EmailSubscriberDetail persistEmailSubscriberDetail(EmailSubscriberDetail emailSubscriberDetail);

    public void updateEmailSubscriberDetail(EmailSubscriberDetail emailSubscriberDetail);

    public void persistEmailMobileAssociation(EmailMobileAssociation association);

    public List<EmailSubscriber> findEmailSubscriptionsByEmail(String email);

    public List<MobileSubscriber> findMobileSubscriptionsByMobile(String mobile);

    public List<MobileSubscriber> findMobileSubscribersByZone(int zoneId, Date startDate, Date endDate);

    public List<EmailSubscriber> findEmailSubscribersByZone(int zoneId, Date startDate, Date endDate);

    public List<EmailSubscriber> getAllEmailSubscribers();

    //public void updateMobileSubscriberVerificationPassword(MobileSubscriber mobileSubscriber);

    public EmailMobileAssociation findEmailMobileAssociation(String email, String mobile);

    //public boolean unsubscribeMobile(String mobile);

    public boolean unsubscribeEmail(String email, String reason);

    boolean unsubscribeMobile(String mobile, List<Integer> zones, String reason);

    boolean unsubscribeEmail(String email, List<Integer> zones, String reason);

    public List<EmailMobileAssociation> getEmailAssociationByEmail(String email);

    public List<EmailMobileAssociation> getMobileAssociationByMobile(String mobile);

    //public List<MobileSubscriber> getMobileSubscribers(List<Integer> zoneIds, DateRange dateRange, boolean subscribed, boolean dnd, boolean customer, int firstResult,
    //        int maxResults);

    public List<MobileSubscriber> getMobileSubscribers(int cityId, DateRange dateRange, boolean subscribed, boolean dnd, boolean customer, int firstResult, int maxResults);

    public List<EmailSubscriber> getEmailSubscribersIncremental(Integer cityId, Integer lastUpdated, int firstResult, int maxResults);

    public List<EmailSubscriber> getEmailAllSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults);

    public boolean updateMobileSubscriberVerification(String mobile, String pin);

    public List<MobileSubscriber> searchZonesByMobile(String searchParam, String pin);

    public boolean unsububscribeMobileFromZones(String mobile, List<Integer> zoneIds, String reason, String suggestion);

    public boolean unsububscribeEmailFromZones(String email, List<Integer> zoneIds, String reason, String suggestion);

    public List<MobileSubscriber> getSubscribedMobileSubscribers(String mobile);

    public MobileSubscriberDetail persistMobileSubscriberDetail(MobileSubscriberDetail mobileSubscriberDetail);

    public MobileSubscriberDetail getMobileSubscriberDetailByMobile(String mobile);

    public EmailSubscriberDetail getEmailSubscriberDetailByUID(String uid);

    public List<EmailSubscriberDetail> getNewSubscriberInDateRange(Date startDate, Date endDate, String channel);

    public List<MobileSubscriberDetail> getNewMobileSubscriberByChannelInDateRange(DateRange range, String channel);

    public boolean unsubscribeMobile(String mobile, String reason);

    public List<Object[]> getMobileSubscribersAndDNDStatus(int firstResult, int maxResults);

    public List<MobileSubscriber> getAllMobileSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults);

    public void updateDNDStatus(String mobile, boolean dndStatus);

    void updateEmailMobileAssociation(EmailMobileAssociation association);

    public List<Integer> getZonesByEmail(String email);

    public SubscriberProfile getSubscriberProfile(String email);

    public SubscriberProfile persistSubscriberProfile(SubscriberProfile profile);

    public SubscriberProfile updateSubscriberProfile(SubscriberProfile profile);

    List<EmailSubscriber> findEmailSubscriberByEmail(String email);

    public EmailSubscriber findEmailSubscriberWithPreferenceByEmail(String email);

    List<EmailSubscriber> getNoPreferenceEmailSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults);

    List<Integer> getZonesByMobile(String mobile);

    public AffiliateSubscriptionOffer getAffiliateSubscriptionOfferByName(String offerName);

    MobileSubscriber findMobileSubscriberByMobile(String mobile);

    List<EmailSubscriber> getSubscribedEmailSubscribers(String email);

	public SubscriberProfile getSubscriberProfileById(Integer id);

    public List<AffiliateSubscriptionOffer> getAllAffiliateSubscriptionOffers();

    public void mergeAffiliateSubscriptionOffer(AffiliateSubscriptionOffer subscriptionOffer);

    public AffiliateSubscriptionOffer getSubscriptionOfferById(int id);

    public List<EmailSubscriber> getEmailSubscribersIncrementalByZone(Integer zoneId, Integer lastUpdated, int firstResult, int maxResults);

    public List<AffiliateSubscriptionOffer> getAllEnabledAffiliateSubscriptionOffers();

    void deleteSubscriber(String mobile);
    
    void deleteSubscriberDetail(String mobile);
    
    void deleteEmailMobileAssociation(String mobile);

    public EmailMobileAssociation getEmailAssociationById(Integer id);

    public List<EmailSubscriber> getEmailSubscribersIncrementalByZoneInDateRange(long id, Integer zoneId, Date start, int maxResults);

    public List<EmailSubscriber> getEmailAllSubscribersIncrementalInDateRange(long id,Date start, int maxResults);

}
