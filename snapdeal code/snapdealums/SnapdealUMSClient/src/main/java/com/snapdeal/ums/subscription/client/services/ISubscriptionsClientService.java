
package com.snapdeal.ums.subscription.client.services;

import com.snapdeal.ums.subscription.ext.subscription.AddEmailMobileAssociationRequest;
import com.snapdeal.ums.subscription.ext.subscription.AddEmailMobileAssociationResponse;
import com.snapdeal.ums.subscription.ext.subscription.AddEmailSubscriberRequest4;
import com.snapdeal.ums.subscription.ext.subscription.AddEmailSubscriberResponse4;
import com.snapdeal.ums.subscription.ext.subscription.AddMobileSubscriberRequest4;
import com.snapdeal.ums.subscription.ext.subscription.AddMobileSubscriberResponse4;
import com.snapdeal.ums.subscription.ext.subscription.AddSubscriberRequest2;
import com.snapdeal.ums.subscription.ext.subscription.AddSubscriberResponse2;
import com.snapdeal.ums.subscription.ext.subscription.AffiliateSubscriptionOfferRequest;
import com.snapdeal.ums.subscription.ext.subscription.AffiliateSubscriptionOfferResponse;
import com.snapdeal.ums.subscription.ext.subscription.CreateEmailSubscriberDetailRequest;
import com.snapdeal.ums.subscription.ext.subscription.CreateEmailSubscriberDetailResponse;
import com.snapdeal.ums.subscription.ext.subscription.CreateMobileSubscriberDetailRequest;
import com.snapdeal.ums.subscription.ext.subscription.CreateMobileSubscriberDetailResponse;
import com.snapdeal.ums.subscription.ext.subscription.CreateMobileSubscriberRequest;
import com.snapdeal.ums.subscription.ext.subscription.CreateMobileSubscriberResponse;
import com.snapdeal.ums.subscription.ext.subscription.FindMobileSubscriberByMobileRequest;
import com.snapdeal.ums.subscription.ext.subscription.FindMobileSubscriberByMobileResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetAllMobileSubscribersIncrementalRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetAllMobileSubscribersIncrementalResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailAllSubscribersIncrementalByDateAndIdRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailAllSubscribersIncrementalByDateAndIdResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailAllSubscribersIncrementalRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailAllSubscribersIncrementalResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailAssociationByEmailRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailAssociationByEmailResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailMobileAssociationRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailMobileAssociationResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberByEmailRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberByEmailResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberDetailByEmailRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberDetailByEmailResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberDetailByUIDRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberDetailByUIDResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriberResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriptionsRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetEmailSubscriptionsResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileAssociationByMobileRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileAssociationByMobileResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscriberDetailByMobileRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscriberDetailByMobileResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscriberPinRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscriberPinResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscriberRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscriberResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscribersRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscribersResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscriptionsRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetMobileSubscriptionsResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetSubscribedEmailSubscribersRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetSubscribedEmailSubscribersResponse;
import com.snapdeal.ums.subscription.ext.subscription.GetZonesByEmailRequest;
import com.snapdeal.ums.subscription.ext.subscription.GetZonesByEmailResponse;
import com.snapdeal.ums.subscription.ext.subscription.IsEmailMobileAssociationRequest;
import com.snapdeal.ums.subscription.ext.subscription.IsEmailMobileAssociationResponse;
import com.snapdeal.ums.subscription.ext.subscription.SearchZonesByMobileRequest;
import com.snapdeal.ums.subscription.ext.subscription.SearchZonesByMobileResponse;
import com.snapdeal.ums.subscription.ext.subscription.UnsubscribeEmailRequest;
import com.snapdeal.ums.subscription.ext.subscription.UnsubscribeEmailRequest2;
import com.snapdeal.ums.subscription.ext.subscription.UnsubscribeEmailResponse;
import com.snapdeal.ums.subscription.ext.subscription.UnsubscribeEmailResponse2;
import com.snapdeal.ums.subscription.ext.subscription.UnsububscribeEmailFromZonesRequest;
import com.snapdeal.ums.subscription.ext.subscription.UnsububscribeEmailFromZonesResponse;
import com.snapdeal.ums.subscription.ext.subscription.UnsububscribeMobileFromZonesRequest;
import com.snapdeal.ums.subscription.ext.subscription.UnsububscribeMobileFromZonesResponse;
import com.snapdeal.ums.subscription.ext.subscription.UpdateEmailMobileAssociationRequest;
import com.snapdeal.ums.subscription.ext.subscription.UpdateEmailMobileAssociationResponse;
import com.snapdeal.ums.subscription.ext.subscription.UpdateEmailSubscriberDetailRequest;
import com.snapdeal.ums.subscription.ext.subscription.UpdateEmailSubscriberDetailResponse;
import com.snapdeal.ums.subscription.ext.subscription.UpdateEmailSubscriberVerificationRequest;
import com.snapdeal.ums.subscription.ext.subscription.UpdateEmailSubscriberVerificationResponse;
import com.snapdeal.ums.subscription.ext.subscription.UpdateMobileSubscriberRequest;
import com.snapdeal.ums.subscription.ext.subscription.UpdateMobileSubscriberResponse;

public interface ISubscriptionsClientService {


    public AddEmailSubscriberResponse4 addEmailSubscriber(AddEmailSubscriberRequest4 request);

    public AddMobileSubscriberResponse4 addMobileSubscriber(AddMobileSubscriberRequest4 request);

    public UnsubscribeEmailResponse unsubscribeEmail(UnsubscribeEmailRequest request);

    public UnsubscribeEmailResponse2 unsubscribeEmail(UnsubscribeEmailRequest2 request);

    public GetEmailSubscriberDetailByEmailResponse getEmailSubscriberDetailByEmail(GetEmailSubscriberDetailByEmailRequest request);

    public CreateEmailSubscriberDetailResponse createEmailSubscriberDetail(CreateEmailSubscriberDetailRequest request);

    public UpdateEmailSubscriberVerificationResponse updateEmailSubscriberVerification(UpdateEmailSubscriberVerificationRequest request);

    public GetMobileSubscriberDetailByMobileResponse getMobileSubscriberDetailByMobile(GetMobileSubscriberDetailByMobileRequest request);

    public AddEmailMobileAssociationResponse addEmailMobileAssociation(AddEmailMobileAssociationRequest request);

    public GetEmailSubscriptionsResponse getEmailSubscriptions(GetEmailSubscriptionsRequest request);

    public GetMobileSubscriptionsResponse getMobileSubscriptions(GetMobileSubscriptionsRequest request);

    public GetMobileSubscriberResponse getMobileSubscriber(GetMobileSubscriberRequest request);

    public GetEmailAssociationByEmailResponse getEmailAssociationByEmail(GetEmailAssociationByEmailRequest request);

    public GetMobileAssociationByMobileResponse getMobileAssociationByMobile(GetMobileAssociationByMobileRequest request);

    public GetMobileSubscribersResponse getMobileSubscribers(GetMobileSubscribersRequest request);

    public GetEmailSubscriberResponse getEmailSubscriber(GetEmailSubscriberRequest request);

    public GetEmailAllSubscribersIncrementalResponse getEmailAllSubscribersIncremental(GetEmailAllSubscribersIncrementalRequest request);

    public GetEmailAllSubscribersIncrementalByDateAndIdResponse getEmailAllSubscribersIncrementalByDateAndId(GetEmailAllSubscribersIncrementalByDateAndIdRequest request);
    
    public GetAllMobileSubscribersIncrementalResponse getAllMobileSubscribersIncremental(GetAllMobileSubscribersIncrementalRequest request);

    public SearchZonesByMobileResponse searchZonesByMobile(SearchZonesByMobileRequest request);

    public UnsububscribeMobileFromZonesResponse unsububscribeMobileFromZones(UnsububscribeMobileFromZonesRequest request);

    public UnsububscribeEmailFromZonesResponse unsububscribeEmailFromZones(UnsububscribeEmailFromZonesRequest request);

    public CreateMobileSubscriberDetailResponse createMobileSubscriberDetail(CreateMobileSubscriberDetailRequest request);

    public GetEmailSubscriberDetailByUIDResponse getEmailSubscriberDetailByUID(GetEmailSubscriberDetailByUIDRequest request);

    public CreateMobileSubscriberResponse createMobileSubscriber(CreateMobileSubscriberRequest request);

    public GetMobileSubscriberPinResponse getMobileSubscriberPin(GetMobileSubscriberPinRequest request);

    public UpdateEmailMobileAssociationResponse updateEmailMobileAssociation(UpdateEmailMobileAssociationRequest request);

    public UpdateEmailSubscriberDetailResponse updateEmailSubscriberDetail(UpdateEmailSubscriberDetailRequest request);

    public GetEmailMobileAssociationResponse getEmailMobileAssociation(GetEmailMobileAssociationRequest request);

    public GetZonesByEmailResponse getZonesByEmail(GetZonesByEmailRequest request);

    public AddSubscriberResponse2 addSubscriber(AddSubscriberRequest2 request);

    public UpdateMobileSubscriberResponse updateMobileSubscriber(UpdateMobileSubscriberRequest request);

    public GetEmailSubscriberByEmailResponse getEmailSubscriberByEmail(GetEmailSubscriberByEmailRequest request);

    public FindMobileSubscriberByMobileResponse findMobileSubscriberByMobile(FindMobileSubscriberByMobileRequest request);

    public GetSubscribedEmailSubscribersResponse getSubscribedEmailSubscribers(GetSubscribedEmailSubscribersRequest request);
    
    public AffiliateSubscriptionOfferResponse getAllEnabledAffiliateSubscriptionOffers(AffiliateSubscriptionOfferRequest request);
    
    public IsEmailMobileAssociationResponse isEmailMobileAssociation(IsEmailMobileAssociationRequest request);
}
