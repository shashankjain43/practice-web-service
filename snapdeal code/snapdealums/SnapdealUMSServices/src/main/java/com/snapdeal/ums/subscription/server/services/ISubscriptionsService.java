
package com.snapdeal.ums.subscription.server.services;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.subscription.ext.subscription.*;

public interface ISubscriptionsService {


    public AddEmailSubscriberResponse4 addEmailSubscriber(AddEmailSubscriberRequest4 request);

    public AddMobileSubscriberResponse4 addMobileSubscriber(AddMobileSubscriberRequest4 request);

    public UnsubscribeEmailResponse unsubscribeEmail(UnsubscribeEmailRequest request);

    public UnsubscribeEmailResponse2 unsubscribeEmail(UnsubscribeEmailRequest2 request);

    public UnsubscribeMobileResponse unsubscribeMobile(UnsubscribeMobileRequest request);

    public UnsubscribeMobileResponse2 unsubscribeMobile(UnsubscribeMobileRequest2 request);

    public GetEmailSubscriberDetailByEmailResponse getEmailSubscriberDetailByEmail(GetEmailSubscriberDetailByEmailRequest request);

    public CreateEmailSubscriberDetailResponse createEmailSubscriberDetail(CreateEmailSubscriberDetailRequest request);

    public UpdateEmailSubscriberVerificationResponse updateEmailSubscriberVerification(UpdateEmailSubscriberVerificationRequest request);

    public GetMobileSubscriberDetailByMobileResponse getMobileSubscriberDetailByMobile(GetMobileSubscriberDetailByMobileRequest request);

    public AddEmailMobileAssociationResponse addEmailMobileAssociation(AddEmailMobileAssociationRequest request);

    public GetEmailSubscriptionsResponse getEmailSubscriptions(GetEmailSubscriptionsRequest request);

    public GetMobileSubscriptionsResponse getMobileSubscriptions(GetMobileSubscriptionsRequest request);

    public GetEmailSubscriberByZoneResponse getEmailSubscriberByZone(GetEmailSubscriberByZoneRequest request);

    public GetMobileSubscriberByZoneResponse getMobileSubscriberByZone(GetMobileSubscriberByZoneRequest request);

    public GetMobileSubscriberResponse getMobileSubscriber(GetMobileSubscriberRequest request);

    public AddMobileSubscriberWithPinResponse addMobileSubscriberWithPin(AddMobileSubscriberWithPinRequest request);

    public UpdateEmailSubscriberMemcacheResponse updateEmailSubscriberMemcache(UpdateEmailSubscriberMemcacheRequest request);

    public IsEmailMobileAssociationResponse isEmailMobileAssociation(IsEmailMobileAssociationRequest request);

    public GetEmailAssociationByEmailResponse getEmailAssociationByEmail(GetEmailAssociationByEmailRequest request);

    public GetMobileAssociationByMobileResponse getMobileAssociationByMobile(GetMobileAssociationByMobileRequest request);

    public GetMobileSubscribersResponse getMobileSubscribers(GetMobileSubscribersRequest request);

    public GetEmailSubscriberResponse getEmailSubscriber(GetEmailSubscriberRequest request);

    public GetEmailSubscribersIncrementalResponse getEmailSubscribersIncremental(GetEmailSubscribersIncrementalRequest request);

    public GetEmailAllSubscribersIncrementalResponse getEmailAllSubscribersIncremental(GetEmailAllSubscribersIncrementalRequest request);

    public GetAllMobileSubscribersIncrementalResponse getAllMobileSubscribersIncremental(GetAllMobileSubscribersIncrementalRequest request);

    public UpdateMobileSubscriberVerificationResponse updateMobileSubscriberVerification(UpdateMobileSubscriberVerificationRequest request);

    public SearchZonesByMobileResponse searchZonesByMobile(SearchZonesByMobileRequest request);

    public UnsububscribeMobileFromZonesResponse unsububscribeMobileFromZones(UnsububscribeMobileFromZonesRequest request);

    public UnsububscribeEmailFromZonesResponse unsububscribeEmailFromZones(UnsububscribeEmailFromZonesRequest request);

    public GetSubscribedMobileSubscribersResponse getSubscribedMobileSubscribers(GetSubscribedMobileSubscribersRequest request);

    public CreateMobileSubscriberDetailResponse createMobileSubscriberDetail(CreateMobileSubscriberDetailRequest request);

    public GetEmailSubscriberDetailByUIDResponse getEmailSubscriberDetailByUID(GetEmailSubscriberDetailByUIDRequest request);

    public CreateMobileSubscriberResponse createMobileSubscriber(CreateMobileSubscriberRequest request);

    public GetMobileSubscriberPinResponse getMobileSubscriberPin(GetMobileSubscriberPinRequest request);

    public GetNewSubscriberInDateRangeResponse getNewSubscriberInDateRange(GetNewSubscriberInDateRangeRequest request);

    public GetNewMobileSubscriberByChannelInDateRangeResponse getNewMobileSubscriberByChannelInDateRange(GetNewMobileSubscriberByChannelInDateRangeRequest request);

    public GetMobileSubscribersAndDNDStatusResponse getMobileSubscribersAndDNDStatus(GetMobileSubscribersAndDNDStatusRequest request);

    public UpdateDNDStatusResponse updateDNDStatus(UpdateDNDStatusRequest request);

    public UpdateEmailMobileAssociationResponse updateEmailMobileAssociation(UpdateEmailMobileAssociationRequest request);

    public UpdateEmailSubscriberDetailResponse updateEmailSubscriberDetail(UpdateEmailSubscriberDetailRequest request);

    public GetEmailMobileAssociationResponse getEmailMobileAssociation(GetEmailMobileAssociationRequest request);

    public GetZonesByEmailResponse getZonesByEmail(GetZonesByEmailRequest request);

    public AddSubscriberResponse2 addSubscriber(AddSubscriberRequest2 request);

    public GetSubscriberProfileResponse getSubscriberProfile(GetSubscriberProfileRequest request);

    public CreateSubscriberProfileResponse createSubscriberProfile(CreateSubscriberProfileRequest request);

    public CreateSubscriberProfileResponse2 createSubscriberProfile(CreateSubscriberProfileRequest2 request) throws TransportException;

    public UpdateSubscriberProfileResponse updateSubscriberProfile(UpdateSubscriberProfileRequest request);

    public UpdateSubscriberProfileResponse2 updateSubscriberProfile(UpdateSubscriberProfileRequest2 request) throws TransportException;

    public UpdateMobileSubscriberResponse updateMobileSubscriber(UpdateMobileSubscriberRequest request);

    public UpdateEmailSubscriberResponse updateEmailSubscriber(UpdateEmailSubscriberRequest request);

    public GetEmailSubscriberByEmailResponse getEmailSubscriberByEmail(GetEmailSubscriberByEmailRequest request);

    public GetSubscriberCityMigrationResponse getSubscriberCityMigration(GetSubscriberCityMigrationRequest request);

    public SaveSubscriberCityMigrationResponse saveSubscriberCityMigration(SaveSubscriberCityMigrationRequest request);

    public FindEmailSubscriberWithPreferenceByEmailResponse findEmailSubscriberWithPreferenceByEmail(FindEmailSubscriberWithPreferenceByEmailRequest request);

    public GetNoPreferenceEmailSubscribersIncrementalResponse getNoPreferenceEmailSubscribersIncremental(GetNoPreferenceEmailSubscribersIncrementalRequest request);

    public GetZonesByMobileResponse getZonesByMobile(GetZonesByMobileRequest request);

    public AffiliateSubscriptionOfferResponse getAffiliateSubscriptionOfferByName(AffiliateSubscriptionOfferRequest request);

    public FindMobileSubscriberByMobileResponse findMobileSubscriberByMobile(FindMobileSubscriberByMobileRequest request);

    public GetSubscribedEmailSubscribersResponse getSubscribedEmailSubscribers(GetSubscribedEmailSubscribersRequest request);

    public AffiliateSubscriptionOfferResponse getSubscriptionOfferById(AffiliateSubscriptionOfferRequest request);

    public AffiliateSubscriptionOfferResponse mergeAffiliateSubscriptionOffer(AffiliateSubscriptionOfferRequest request);

    public AffiliateSubscriptionOfferResponse getAllAffiliateSubscriptionOffers(AffiliateSubscriptionOfferRequest request);

    public GetEmailSubscribersIncrementalByZoneResponse getEmailSubscribersIncrementalByZone(GetEmailSubscribersIncrementalByZoneRequest request);

    public AffiliateSubscriptionOfferResponse getAllEnabledAffiliateSubscriptionOffers(AffiliateSubscriptionOfferRequest request);

    public DeleteMobileSubscriberResponse deleteSubscribers(DeleteMobileSubscriberRequest request);

    public GetEmailSubscribersIncrementalByZoneDateAndIdResponse getEmailSubscribersIncrementalByZoneDateAndId(GetEmailSubscribersIncrementalByZoneDateAndIdRequest request);

    public GetEmailAllSubscribersIncrementalByDateAndIdResponse getEmailAllSubscribersIncrementalByDateAndId(GetEmailAllSubscribersIncrementalByDateAndIdRequest request);
}
