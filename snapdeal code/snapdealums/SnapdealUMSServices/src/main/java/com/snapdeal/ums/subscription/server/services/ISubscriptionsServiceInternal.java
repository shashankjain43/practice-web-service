package com.snapdeal.ums.subscription.server.services;

import java.util.Date;
import java.util.List;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.core.dto.ZoneDTO;
import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.ums.core.entity.AffiliateSubscriptionOffer;
import com.snapdeal.ums.core.entity.EmailMobileAssociation;
import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.core.entity.EmailSubscriber;
import com.snapdeal.ums.core.entity.EmailSubscriberDetail;
import com.snapdeal.ums.core.entity.MobileSubscriber;
import com.snapdeal.ums.core.entity.MobileSubscriberDetail;
import com.snapdeal.ums.core.entity.SubscriberProfile;
import com.snapdeal.core.model.subscribercitymigration.SubscriberCityMigration;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO.FilterType;
import com.snapdeal.ums.core.sro.subscription.AffiliateSubscriptionOfferSRO;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;

public interface ISubscriptionsServiceInternal {

    public boolean unsubscribeEmail(String email, int zoneId, String reason);

    public boolean unsubscribeMobile(String mobile, int zoneId, String reason);

    public EmailSubscriberDetail getEmailSubscriberDetailByEmail(String email);

    public EmailSubscriberDetail createEmailSubscriberDetail(String email, String code);

    public void updateEmailSubscriberVerification(EmailSubscriberDetail emailSubscriberVerification);

    public MobileSubscriberDetail getMobileSubscriberDetailByMobile(String mobile);

    public void addEmailMobileAssociation(EmailMobileAssociation association);

    public List<EmailSubscriber> getEmailSubscriptions(String email);

    public List<MobileSubscriber> getMobileSubscriptions(String mobile);

    public List<EmailSubscriber> getEmailSubscriberByZone(int zoneId, Date startTime, Date endTime);

    public List<MobileSubscriber> getMobileSubscriberByZone(int zoneId, Date startTime, Date endTime);

    public MobileSubscriber getMobileSubscriber(String mobile, int zone);

    public boolean addMobileSubscriberWithPin(String mobile, int zoneId, String pin, String channel, String affiliateTrackingCode, String string);

    public void updateEmailSubscriberMemcache();

    boolean isEmailMobileAssociation(String email, String mobile);

    public List<EmailMobileAssociation> getEmailAssociationByEmail(String email);

    public List<EmailMobileAssociation> getMobileAssociationByMobile(String mobile);

    //public List<MobileSubscriber> getMobileSubscribers(List<Integer> zoneIds, FilterType filterType, boolean subscribed, boolean dnd, int firstResult, int maxResults);

    public List<MobileSubscriber> getMobileSubscribers(int cityId, String filterType, boolean subscribed, boolean dnd, int firstResult, int maxResults);

    public boolean unsubscribeEmail(String email, String reason);

    public boolean unsubscribeMobile(String mobile, String reason);

    public EmailSubscriber getEmailSubscriber(String email, int zone);

    List<EmailSubscriber> getEmailSubscribersIncremental(Integer cityId, Integer lastUpdated, int firstResult, int maxResults);

    List<EmailSubscriber> getEmailAllSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults);

    List<MobileSubscriber> getAllMobileSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults);

    public boolean updateMobileSubscriberVerification(String mobile, String pin);

    public List<ZoneDTO> searchZonesByMobile(String mobile, String pin);

    public boolean unsububscribeMobileFromZones(String mobile, List<Integer> zoneIds, String reason, String suggestion);

    public boolean unsububscribeEmailFromZones(String email, List<Integer> zoneIds, String reason, String suggestion);

    public List<MobileSubscriber> getSubscribedMobileSubscribers(String mobile);

    MobileSubscriberDetail createMobileSubscriberDetail(String mobile);

    public EmailSubscriberDetail getEmailSubscriberDetailByUID(String refCode);

    public boolean createMobileSubscriber(String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription, String pin, String affiliateTrackingCode,
            String utmTrackingSource);

    public String getMobileSubscriberPin(String mobile);

    public List<EmailSubscriberDetail> getNewSubscriberInDateRange(Date startDate, Date endDate, String channel);

    //List<MobileSubscriber> getMobileSubscribers(Integer zoneId, FilterType filterType, boolean subscribed, boolean dnd, int firstResult, int maxResults);

    public List<MobileSubscriberDetail> getNewMobileSubscriberByChannelInDateRange(DateRange range, String channel);

    public List<Object[]> getMobileSubscribersAndDNDStatus(int firstResult, int maxResults);

    public void updateDNDStatus(String mobile, boolean dndStatus);

    void updateEmailMobileAssociation(EmailMobileAssociation association);

    void updateEmailSubscriberDetail(EmailSubscriberDetail esd);

    EmailMobileAssociation getEmailMobileAssociation(String email, String mobile);

    public List<Integer> getZonesByEmail(String email);

    boolean addEmailSubscriber(String email, int zoneId, String subscriptionPage, boolean overrideUnsubscription, boolean isActive, String affiliateTrackingCode,
            String utmTrackingSource);

    boolean addSubscriber(String emailId, String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription, boolean isActive, String affiliateTrackingCode,
            String utmTracking);

    boolean addMobileSubscriber(String mobile, int zoneId, String subscriptionPage, boolean overrideUnsubscription, boolean isSubscribed, String affiliateTrackingCode,
            String utmTrackingSource);

    SubscriberProfile getSubscriberProfile(String email);

    SubscriberProfile createSubscriberProfile(SubscriberProfile profile);

    SubscriberProfile updateSubscriberProfile(SubscriberProfile profile);

    SubscriberProfile createSubscriberProfile(String email, String name, String displayName, String gender, Date birthday, Integer localityId, ZoneSRO zone);

    SubscriberProfile updateSubscriberProfile(SubscriberProfileSRO profile, String name, String displayName, String gender, Date birthday, Integer localityId, ZoneSRO zone);

    public void updateMobileSubscriber(MobileSubscriber mobileSubscriber);

    public void updateEmailSubscriber(EmailSubscriber emailSubscriber);

    List<EmailSubscriber> getEmailSubscriberByEmail(String email);

    SubscriberCityMigration getSubscriberCityMigration(String emailId, String city);

    void saveSubscriberCityMigration(SubscriberCityMigration subscriberCityMigration);

    EmailSubscriber findEmailSubscriberWithPreferenceByEmail(String email);

    List<EmailSubscriber> getNoPreferenceEmailSubscribersIncremental(Integer lastUpdated, int firstResult, int maxResults);

    List<Integer> getZonesByMobile(String mobile);

    public AffiliateSubscriptionOffer getAffiliateSubscriptionOfferByName(String offerName);

    public MobileSubscriber findMobileSubscriberByMobile(String mobile);

    public List<EmailSubscriber> getSubscribedEmailSubscribers(String email);

    public AffiliateSubscriptionOffer getSubscriptionOfferById(Integer id);

    public void mergeAffiliateSubscriptionOffer(AffiliateSubscriptionOffer affiliateSubscriptionOffer);

    public List<AffiliateSubscriptionOffer> getAllAffiliateSubscriptionOffers();

    public List<EmailSubscriber> getEmailSubscribersIncrementalByZone(Integer zoneId, Integer lastUpdated, int firstResult, int maxResults);

    public List<AffiliateSubscriptionOffer> getAllEnabledAffiliateSubscriptionOffers();
    public void deleteSubscribers(String mobile);

    public EmailMobileAssociation getEmailAssociationById(Integer id);

    public List<EmailSubscriber> getEmailSubscribersIncrementalByZoneDateAndId(long id ,Integer zoneId, Date start, int maxResults);

    public List<EmailSubscriber> getEmailAllSubscribersIncrementalByDateAndId(long id,Date start, int maxResults);
}
