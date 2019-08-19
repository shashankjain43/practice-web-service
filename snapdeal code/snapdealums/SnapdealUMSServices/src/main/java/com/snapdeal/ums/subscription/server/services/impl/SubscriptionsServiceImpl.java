package com.snapdeal.ums.subscription.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.catalog.base.model.GetZoneByIdRequest;
import com.snapdeal.catalog.base.model.GetZoneResponse;
import com.snapdeal.core.dto.ZoneDTO;
import com.snapdeal.core.model.subscribercitymigration.SubscriberCityMigration;
import com.snapdeal.locality.client.service.ILocalityClientService;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.entity.AffiliateSubscriptionOffer;
import com.snapdeal.ums.core.entity.EmailMobileAssociation;
import com.snapdeal.ums.core.entity.EmailSubscriber;
import com.snapdeal.ums.core.entity.EmailSubscriberDetail;
import com.snapdeal.ums.core.entity.MobileSubscriber;
import com.snapdeal.ums.core.entity.MobileSubscriberDetail;
import com.snapdeal.ums.core.entity.SubscriberProfile;
import com.snapdeal.ums.core.sro.subscription.AffiliateSubscriptionOfferSRO;
import com.snapdeal.ums.core.sro.subscription.EmailMobileAssociationSRO;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberDetailSRO;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberDetailSRO;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.services.ValidationService;
import com.snapdeal.ums.subscription.ext.subscription.*;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsService;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsServiceInternal;

@Service("umsSubscriptionService")
public class SubscriptionsServiceImpl implements ISubscriptionsService {

	@Autowired
	private ISubscriptionsServiceInternal subscriptionServiceInternal;
	@Autowired
	private IUMSConvertorService umsConvertorService;
	@Autowired
	private ILocalityClientService localityClient;
	
	 @Autowired
		private ValidationService validationService;

	@Override
	public AddEmailSubscriberResponse4 addEmailSubscriber(
			AddEmailSubscriberRequest4 request) {
		AddEmailSubscriberResponse4 response = new AddEmailSubscriberResponse4();

		boolean success = subscriptionServiceInternal.addEmailSubscriber(
				request.getEmail(), request.getZoneId(),
				request.getSubscriptionPage(),
				request.getOverrideUnsubscription(), request.getIsActive(),
				request.getAffiliateTrackingCode(),
				request.getTrackingUtmSource());
		response.setAddEmailSubscriber(success);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public AddMobileSubscriberResponse4 addMobileSubscriber(
			AddMobileSubscriberRequest4 request) {
		AddMobileSubscriberResponse4 response = new AddMobileSubscriberResponse4();
		boolean success = subscriptionServiceInternal.addMobileSubscriber(
				request.getMobile(), request.getZoneId(),
				request.getSubscriptionPage(),
				request.getOverrideUnsubscription(), request.getIsSubscribed(),
				request.getAffiliateTrackingCode(),
				request.getTrackingUtmSource());
		response.setAddMobileSubscriber(success);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public UnsubscribeEmailResponse unsubscribeEmail(
			UnsubscribeEmailRequest request) {
		UnsubscribeEmailResponse response = new UnsubscribeEmailResponse();
		boolean success = subscriptionServiceInternal.unsubscribeEmail(
				request.getEmail(), request.getZoneId(), request.getReason());
		response.setUnsubscribeEmail(success);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public UnsubscribeEmailResponse2 unsubscribeEmail(
			UnsubscribeEmailRequest2 request) {
		UnsubscribeEmailResponse2 response = new UnsubscribeEmailResponse2();
		boolean success = subscriptionServiceInternal.unsubscribeEmail(
				request.getEmail(), request.getReason());
		response.setUnsubscribeEmail(success);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public UnsubscribeMobileResponse unsubscribeMobile(
			UnsubscribeMobileRequest request) {
		UnsubscribeMobileResponse response = new UnsubscribeMobileResponse();
		boolean success = subscriptionServiceInternal.unsubscribeMobile(
				request.getMobile(), request.getZoneId(), request.getReason());
		response.setUnsubscribeMobile(success);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public UnsubscribeMobileResponse2 unsubscribeMobile(
			UnsubscribeMobileRequest2 request) {
		UnsubscribeMobileResponse2 response = new UnsubscribeMobileResponse2();
		boolean success = subscriptionServiceInternal.unsubscribeMobile(
				request.getMobile(), request.getReason());
		response.setUnsubscribeMobile(success);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetEmailSubscriberDetailByEmailResponse getEmailSubscriberDetailByEmail(
			final GetEmailSubscriberDetailByEmailRequest request) {

		final GetEmailSubscriberDetailByEmailResponse response = new GetEmailSubscriberDetailByEmailResponse();
		final EmailSubscriberDetail detail = subscriptionServiceInternal
				.getEmailSubscriberDetailByEmail(request.getEmail());
		final EmailSubscriberDetailSRO detailSRO = umsConvertorService
				.getEmailSubscriberDetailSROfromEntity(detail);

		response.setGetEmailSubscriberDetailByEmail(detailSRO);

		response.setSuccessful(true);
		return response;
	}

	@Override
	public CreateEmailSubscriberDetailResponse createEmailSubscriberDetail(
			CreateEmailSubscriberDetailRequest request) {
		CreateEmailSubscriberDetailResponse response = new CreateEmailSubscriberDetailResponse();
		EmailSubscriberDetail detail = subscriptionServiceInternal
				.createEmailSubscriberDetail(request.getEmail(),
						request.getCode());
		EmailSubscriberDetailSRO detailSRO = umsConvertorService
				.getEmailSubscriberDetailSROfromEntity(detail);
		response.setCreateEmailSubscriberDetail(detailSRO);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public UpdateEmailSubscriberVerificationResponse updateEmailSubscriberVerification(
			UpdateEmailSubscriberVerificationRequest request) {
		UpdateEmailSubscriberVerificationResponse response = new UpdateEmailSubscriberVerificationResponse();
		EmailSubscriberDetailSRO detailSRO = request
				.getEmailSubscriberVerification();
		EmailSubscriberDetail detail = umsConvertorService
				.getEmailSubscriberDetailEntityFromSRO(detailSRO);
		subscriptionServiceInternal.updateEmailSubscriberVerification(detail);

		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetMobileSubscriberDetailByMobileResponse getMobileSubscriberDetailByMobile(
			GetMobileSubscriberDetailByMobileRequest request) {
		GetMobileSubscriberDetailByMobileResponse response = new GetMobileSubscriberDetailByMobileResponse();
		MobileSubscriberDetail detail = subscriptionServiceInternal
				.getMobileSubscriberDetailByMobile(request.getMobile());
		MobileSubscriberDetailSRO detailSRO = umsConvertorService
				.getMobileSubscriberDetailSROFromEntity(detail);
		response.setGetMobileSubscriberDetailByMobile(detailSRO);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public AddEmailMobileAssociationResponse addEmailMobileAssociation(
			AddEmailMobileAssociationRequest request) {
		AddEmailMobileAssociationResponse response = new AddEmailMobileAssociationResponse();
		EmailMobileAssociationSRO sro = request.getAssociation();
		EmailMobileAssociation entity = umsConvertorService
				.getEmailMobileAssociationEntityFromSRO(sro);
		subscriptionServiceInternal.addEmailMobileAssociation(entity);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetEmailSubscriptionsResponse getEmailSubscriptions(
			GetEmailSubscriptionsRequest request) {
		GetEmailSubscriptionsResponse response = new GetEmailSubscriptionsResponse();
		List<EmailSubscriber> subscriptions = subscriptionServiceInternal
				.getEmailSubscriptions(request.getEmail());
		List<EmailSubscriberSRO> subSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber sub : subscriptions)
			subSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(sub));
		response.setEmailSubscriptions(subSROs);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetMobileSubscriptionsResponse getMobileSubscriptions(
			GetMobileSubscriptionsRequest request) {
		GetMobileSubscriptionsResponse response = new GetMobileSubscriptionsResponse();
		List<MobileSubscriber> subscriptions = subscriptionServiceInternal
				.getMobileSubscriptions(request.getMobile());
		List<MobileSubscriberSRO> subSROs = new ArrayList<MobileSubscriberSRO>();
		for (MobileSubscriber sub : subscriptions)
			subSROs.add(umsConvertorService
					.getMobileSubscriberSROFromEntity(sub));
		response.setMobileSubscriptions(subSROs);

		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetEmailSubscriberByZoneResponse getEmailSubscriberByZone(
			GetEmailSubscriberByZoneRequest request) {
		GetEmailSubscriberByZoneResponse response = new GetEmailSubscriberByZoneResponse();
		List<EmailSubscriber> subscribers = subscriptionServiceInternal
				.getEmailSubscriberByZone(request.getZoneId(),
						request.getStartTime(), request.getEndTime());
		List<EmailSubscriberSRO> subSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber sub : subscribers)
			subSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(sub));
		response.setEmailSubscriberByZone(subSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetMobileSubscriberByZoneResponse getMobileSubscriberByZone(
			GetMobileSubscriberByZoneRequest request) {
		GetMobileSubscriberByZoneResponse response = new GetMobileSubscriberByZoneResponse();
		List<MobileSubscriber> mobSubscribers = subscriptionServiceInternal
				.getMobileSubscriberByZone(request.getZoneId(),
						request.getStartTime(), request.getEndTime());
		List<MobileSubscriberSRO> mobSubSROs = new ArrayList<MobileSubscriberSRO>();
		for (MobileSubscriber sub : mobSubscribers)
			mobSubSROs.add(umsConvertorService
					.getMobileSubscriberSROFromEntity(sub));
		response.setMobileSubscriberByZone(mobSubSROs);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetMobileSubscriberResponse getMobileSubscriber(
			GetMobileSubscriberRequest request) {
		GetMobileSubscriberResponse response = new GetMobileSubscriberResponse();
		MobileSubscriber subscriber = subscriptionServiceInternal
				.getMobileSubscriber(request.getMobile(), request.getZone());
		MobileSubscriberSRO subscriberSRO = umsConvertorService
				.getMobileSubscriberSROFromEntity(subscriber);

		response.setMobileSubscriber(subscriberSRO);

		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public AddMobileSubscriberWithPinResponse addMobileSubscriberWithPin(
			AddMobileSubscriberWithPinRequest request) {
		AddMobileSubscriberWithPinResponse response = new AddMobileSubscriberWithPinResponse();
		boolean success = subscriptionServiceInternal
				.addMobileSubscriberWithPin(request.getMobile(),
						request.getZoneId(), request.getPin(),
						request.getChannel(),
						request.getAffiliateTrackingCode(),
						request.getUtmTracking());
		response.setAddMobileSubscriberWithPin(success);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public UpdateEmailSubscriberMemcacheResponse updateEmailSubscriberMemcache(
			UpdateEmailSubscriberMemcacheRequest request) {
		UpdateEmailSubscriberMemcacheResponse response = new UpdateEmailSubscriberMemcacheResponse();
		subscriptionServiceInternal.updateEmailSubscriberMemcache();

		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public IsEmailMobileAssociationResponse isEmailMobileAssociation(
			IsEmailMobileAssociationRequest request) {
		IsEmailMobileAssociationResponse response = new IsEmailMobileAssociationResponse();
		boolean success = subscriptionServiceInternal.isEmailMobileAssociation(
				request.getEmail(), request.getMobile());
		response.setIsEmailMobileAssociation(success);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetEmailAssociationByEmailResponse getEmailAssociationByEmail(
			GetEmailAssociationByEmailRequest request) {

		GetEmailAssociationByEmailResponse response = new GetEmailAssociationByEmailResponse();
		/*
		 * adding request null check and validation error
		 */
		if (request==null||request.getEmail()!=null){
			
			validationService.addValidationError(response, ErrorConstants.INVALID_REQUEST);
			response.setSuccessful(false);
		}
		else{
		if (StringUtils.isNotEmpty(request.getEmail())) {
			final List<EmailMobileAssociation> emailAssociations = subscriptionServiceInternal
					.getEmailAssociationByEmail(request.getEmail());
			List<EmailMobileAssociationSRO> emailAssocSROs = new ArrayList<EmailMobileAssociationSRO>();
			for (EmailMobileAssociation entity : emailAssociations)
				emailAssocSROs.add(umsConvertorService
						.getEmailMobileAssociationSROFromEntity(entity));
			response.setEmailAssociationByEmail(emailAssocSROs);
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
			response.setMessage("email can not be empty");
		}
		}
		return response;
	}

	@Override
	public GetMobileAssociationByMobileResponse getMobileAssociationByMobile(
			GetMobileAssociationByMobileRequest request) {
		GetMobileAssociationByMobileResponse response = new GetMobileAssociationByMobileResponse();
		List<EmailMobileAssociation> emailAssociations = subscriptionServiceInternal
				.getMobileAssociationByMobile(request.getMobile());
		List<EmailMobileAssociationSRO> emailAssocSROs = new ArrayList<EmailMobileAssociationSRO>();
		for (EmailMobileAssociation entity : emailAssociations)
			emailAssocSROs.add(umsConvertorService
					.getEmailMobileAssociationSROFromEntity(entity));
		response.setMobileAssociationByMobile(emailAssocSROs);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetMobileSubscribersResponse getMobileSubscribers(
			GetMobileSubscribersRequest request) {
		GetMobileSubscribersResponse response = new GetMobileSubscribersResponse();
		List<MobileSubscriber> subscribers = subscriptionServiceInternal
				.getMobileSubscribers(request.getCityIds(),
						request.getFilterType(), request.getSubscribed(),
						request.getDnd(), request.getFirstResult(),
						request.getMaxResults());
		List<MobileSubscriberSRO> subscriberSROs = new ArrayList<MobileSubscriberSRO>();
		for (MobileSubscriber entity : subscribers)
			subscriberSROs.add(umsConvertorService
					.getMobileSubscriberSROFromEntity(entity));
		response.setMobileSubscribers(subscriberSROs);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetEmailSubscriberResponse getEmailSubscriber(
			GetEmailSubscriberRequest request) {
		GetEmailSubscriberResponse response = new GetEmailSubscriberResponse();
		EmailSubscriber subscriber = subscriptionServiceInternal
				.getEmailSubscriber(request.getEmail(), request.getZone());
		EmailSubscriberSRO subscriberSRO = umsConvertorService
				.getEmailSubscriberSROFromEntity(subscriber);
		response.setEmailSubscriber(subscriberSRO);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetEmailSubscribersIncrementalResponse getEmailSubscribersIncremental(
			GetEmailSubscribersIncrementalRequest request) {
		GetEmailSubscribersIncrementalResponse response = new GetEmailSubscribersIncrementalResponse();
		List<EmailSubscriber> subscribers = subscriptionServiceInternal
				.getEmailSubscribersIncremental(request.getCityId(),
						request.getLastUpdated(), request.getFirstResult(),
						request.getMaxResults());
		List<EmailSubscriberSRO> subscriberSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber entity : subscribers)
			subscriberSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(entity));
		response.setEmailSubscribersIncremental(subscriberSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetEmailSubscribersIncrementalByZoneDateAndIdResponse getEmailSubscribersIncrementalByZoneDateAndId(
			GetEmailSubscribersIncrementalByZoneDateAndIdRequest request) {
		GetEmailSubscribersIncrementalByZoneDateAndIdResponse response = new GetEmailSubscribersIncrementalByZoneDateAndIdResponse();
		List<EmailSubscriber> subscribers = subscriptionServiceInternal
				.getEmailSubscribersIncrementalByZoneDateAndId(request.getId(),
						request.getZoneId(), request.getStartDate(),
						request.getMaxResults());
		List<EmailSubscriberSRO> subscriberSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber entity : subscribers)
			subscriberSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(entity));
		response.setEmailSubscribersIncremental(subscriberSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetEmailSubscribersIncrementalByZoneResponse getEmailSubscribersIncrementalByZone(
			GetEmailSubscribersIncrementalByZoneRequest request) {
		GetEmailSubscribersIncrementalByZoneResponse response = new GetEmailSubscribersIncrementalByZoneResponse();
		List<EmailSubscriber> subscribers = subscriptionServiceInternal
				.getEmailSubscribersIncrementalByZone(request.getZoneId(),
						request.getLastUpdated(), request.getFirstResult(),
						request.getMaxResults());
		List<EmailSubscriberSRO> subscriberSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber entity : subscribers)
			subscriberSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(entity));
		response.setEmailSubscribersIncremental(subscriberSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetEmailAllSubscribersIncrementalResponse getEmailAllSubscribersIncremental(
			GetEmailAllSubscribersIncrementalRequest request) {
		GetEmailAllSubscribersIncrementalResponse response = new GetEmailAllSubscribersIncrementalResponse();
		List<EmailSubscriber> subscribers = subscriptionServiceInternal
				.getEmailAllSubscribersIncremental(request.getLastUpdated(),
						request.getFirstResult(), request.getMaxResults());
		List<EmailSubscriberSRO> subscriberSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber entity : subscribers)
			subscriberSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(entity));
		response.setEmailAllSubscribersIncremental(subscriberSROs);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetEmailAllSubscribersIncrementalByDateAndIdResponse getEmailAllSubscribersIncrementalByDateAndId(
			GetEmailAllSubscribersIncrementalByDateAndIdRequest request) {
		GetEmailAllSubscribersIncrementalByDateAndIdResponse response = new GetEmailAllSubscribersIncrementalByDateAndIdResponse();
		List<EmailSubscriber> subscribers = subscriptionServiceInternal
				.getEmailAllSubscribersIncrementalByDateAndId(request.getId(),
						request.getStartDate(), request.getMaxResults());
		List<EmailSubscriberSRO> subscriberSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber entity : subscribers)
			subscriberSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(entity));
		response.setEmailSubscribersSRO(subscriberSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetAllMobileSubscribersIncrementalResponse getAllMobileSubscribersIncremental(
			GetAllMobileSubscribersIncrementalRequest request) {
		GetAllMobileSubscribersIncrementalResponse response = new GetAllMobileSubscribersIncrementalResponse();
		List<MobileSubscriber> subscribers = subscriptionServiceInternal
				.getAllMobileSubscribersIncremental(request.getLastUpdated(),
						request.getFirstResult(), request.getMaxResults());
		List<MobileSubscriberSRO> subscriberSROs = new ArrayList<MobileSubscriberSRO>();
		for (MobileSubscriber entity : subscribers)
			subscriberSROs.add(umsConvertorService
					.getMobileSubscriberSROFromEntity(entity));
		response.setAllMobileSubscribersIncremental(subscriberSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public UpdateMobileSubscriberVerificationResponse updateMobileSubscriberVerification(
			UpdateMobileSubscriberVerificationRequest request) {
		UpdateMobileSubscriberVerificationResponse response = new UpdateMobileSubscriberVerificationResponse();
		boolean success = subscriptionServiceInternal
				.updateMobileSubscriberVerification(request.getMobile(),
						request.getPin());
		response.setUpdateMobileSubscriberVerification(success);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public SearchZonesByMobileResponse searchZonesByMobile(
			SearchZonesByMobileRequest request) {
		SearchZonesByMobileResponse response = new SearchZonesByMobileResponse();
		List<ZoneDTO> zones = subscriptionServiceInternal.searchZonesByMobile(
				request.getMobile(), request.getPin());
		response.setSearchZonesByMobile(zones);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public UnsububscribeMobileFromZonesResponse unsububscribeMobileFromZones(
			UnsububscribeMobileFromZonesRequest request) {
		UnsububscribeMobileFromZonesResponse response = new UnsububscribeMobileFromZonesResponse();
		boolean success = subscriptionServiceInternal
				.unsububscribeMobileFromZones(request.getMobile(),
						request.getZoneIds(), request.getReason(),
						request.getSuggestion());
		response.setUnsububscribeMobileFromZones(success);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public UnsububscribeEmailFromZonesResponse unsububscribeEmailFromZones(
			UnsububscribeEmailFromZonesRequest request) {
		UnsububscribeEmailFromZonesResponse response = new UnsububscribeEmailFromZonesResponse();
		boolean success = subscriptionServiceInternal
				.unsububscribeEmailFromZones(request.getEmail(),
						request.getZoneIds(), request.getReason(),
						request.getSuggestion());
		response.setUnsububscribeEmailFromZones(success);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetSubscribedMobileSubscribersResponse getSubscribedMobileSubscribers(
			GetSubscribedMobileSubscribersRequest request) {
		GetSubscribedMobileSubscribersResponse response = new GetSubscribedMobileSubscribersResponse();
		List<MobileSubscriber> subscribers = subscriptionServiceInternal
				.getSubscribedMobileSubscribers(request.getMobile());
		List<MobileSubscriberSRO> subscriberSROs = new ArrayList<MobileSubscriberSRO>();
		for (MobileSubscriber entity : subscribers)
			subscriberSROs.add(umsConvertorService
					.getMobileSubscriberSROFromEntity(entity));
		response.setSubscribedMobileSubscribers(subscriberSROs);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public CreateMobileSubscriberDetailResponse createMobileSubscriberDetail(
			CreateMobileSubscriberDetailRequest request) {
		CreateMobileSubscriberDetailResponse response = new CreateMobileSubscriberDetailResponse();
		MobileSubscriberDetail detail = subscriptionServiceInternal
				.createMobileSubscriberDetail(request.getMobile());

		response.setCreateMobileSubscriberDetail(umsConvertorService
				.getMobileSubscriberDetailSROFromEntity(detail));
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetEmailSubscriberDetailByUIDResponse getEmailSubscriberDetailByUID(
			final GetEmailSubscriberDetailByUIDRequest request) {

		final GetEmailSubscriberDetailByUIDResponse response = new GetEmailSubscriberDetailByUIDResponse();
		final EmailSubscriberDetail detail = subscriptionServiceInternal
				.getEmailSubscriberDetailByUID(request.getRefCode());
		final EmailSubscriberDetailSRO detailSRO = umsConvertorService
				.getEmailSubscriberDetailSROfromEntity(detail);
		response.setEmailSubscriberDetailByUID(detailSRO);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public CreateMobileSubscriberResponse createMobileSubscriber(
			CreateMobileSubscriberRequest request) {
		CreateMobileSubscriberResponse response = new CreateMobileSubscriberResponse();
		boolean success = subscriptionServiceInternal.createMobileSubscriber(
				request.getMobile(), request.getZoneId(),
				request.getSubscriptionPage(),
				request.getOverrideUnsubscription(), request.getPin(),
				request.getAffiliateTrackingCode(), request.getUtmTracking());
		response.setCreateMobileSubscriber(success);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetMobileSubscriberPinResponse getMobileSubscriberPin(
			GetMobileSubscriberPinRequest request) {
		GetMobileSubscriberPinResponse response = new GetMobileSubscriberPinResponse();
		String pin = subscriptionServiceInternal.getMobileSubscriberPin(request
				.getMobile());
		response.setGetMobileSubscriberPin(pin);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetNewSubscriberInDateRangeResponse getNewSubscriberInDateRange(
			GetNewSubscriberInDateRangeRequest request) {
		GetNewSubscriberInDateRangeResponse response = new GetNewSubscriberInDateRangeResponse();
		List<EmailSubscriberDetail> details = subscriptionServiceInternal
				.getNewSubscriberInDateRange(request.getStartDate(),
						request.getEndDate(), request.getChannel());
		List<EmailSubscriberDetailSRO> detailSROs = new ArrayList<EmailSubscriberDetailSRO>();
		for (EmailSubscriberDetail entity : details)
			detailSROs.add(umsConvertorService
					.getEmailSubscriberDetailSROfromEntity(entity));
		response.setNewSubscriberInDateRange(detailSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetNewMobileSubscriberByChannelInDateRangeResponse getNewMobileSubscriberByChannelInDateRange(
			GetNewMobileSubscriberByChannelInDateRangeRequest request) {
		GetNewMobileSubscriberByChannelInDateRangeResponse response = new GetNewMobileSubscriberByChannelInDateRangeResponse();
		List<MobileSubscriberDetail> details = subscriptionServiceInternal
				.getNewMobileSubscriberByChannelInDateRange(request.getRange(),
						request.getChannel());
		List<MobileSubscriberDetailSRO> detailSROs = new ArrayList<MobileSubscriberDetailSRO>();
		for (MobileSubscriberDetail entity : details)
			detailSROs.add(umsConvertorService
					.getMobileSubscriberDetailSROFromEntity(entity));
		response.setNewMobileSubscriberByChannelInDateRange(detailSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetMobileSubscribersAndDNDStatusResponse getMobileSubscribersAndDNDStatus(
			GetMobileSubscribersAndDNDStatusRequest request) {
		GetMobileSubscribersAndDNDStatusResponse response = new GetMobileSubscribersAndDNDStatusResponse();
		List<Object[]> listofObjects = subscriptionServiceInternal
				.getMobileSubscribersAndDNDStatus(request.getFirstResult(),
						request.getMaxResults());
		response.setGetMobileSubscribersAndDNDStatus(listofObjects);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public UpdateDNDStatusResponse updateDNDStatus(
			UpdateDNDStatusRequest request) {
		UpdateDNDStatusResponse response = new UpdateDNDStatusResponse();
		subscriptionServiceInternal.updateDNDStatus(request.getMobile(),
				request.getDndStatus());

		response.setSuccessful(true);
		return response;
	}

	@Override
	public UpdateEmailMobileAssociationResponse updateEmailMobileAssociation(
			UpdateEmailMobileAssociationRequest request) {
		UpdateEmailMobileAssociationResponse response = new UpdateEmailMobileAssociationResponse();
		EmailMobileAssociationSRO assocSRO = request.getAssociation();
		EmailMobileAssociation entity = updateDBVersion(assocSRO);
		subscriptionServiceInternal.updateEmailMobileAssociation(entity);

		response.setSuccessful(true);
		return response;
	}

	private EmailMobileAssociation updateDBVersion(
			EmailMobileAssociationSRO assocSRO) {
		EmailMobileAssociation dbVersion = subscriptionServiceInternal
				.getEmailAssociationById(assocSRO.getId());
		dbVersion.setEmail(assocSRO.getEmail());
		dbVersion.setMobile(assocSRO.getMobile());
		dbVersion.setVerified(assocSRO.isVerified());
		return dbVersion;
	}

	@Override
	public UpdateEmailSubscriberDetailResponse updateEmailSubscriberDetail(
			UpdateEmailSubscriberDetailRequest request) {
		UpdateEmailSubscriberDetailResponse response = new UpdateEmailSubscriberDetailResponse();
		EmailSubscriberDetailSRO sro = request.getEsd();
		EmailSubscriberDetail detail = umsConvertorService
				.getEmailSubscriberDetailEntityFromSRO(sro);
		subscriptionServiceInternal.updateEmailSubscriberDetail(detail);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetEmailMobileAssociationResponse getEmailMobileAssociation(
			GetEmailMobileAssociationRequest request) {
		GetEmailMobileAssociationResponse response = new GetEmailMobileAssociationResponse();
		EmailMobileAssociation associations = subscriptionServiceInternal
				.getEmailMobileAssociation(request.getEmail(),
						request.getMobile());
		EmailMobileAssociationSRO assocSRO = umsConvertorService
				.getEmailMobileAssociationSROFromEntity(associations);
		response.setGetEmailMobileAssociation(assocSRO);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetZonesByEmailResponse getZonesByEmail(
			GetZonesByEmailRequest request) {
		GetZonesByEmailResponse response = new GetZonesByEmailResponse();
		List<Integer> zones = subscriptionServiceInternal
				.getZonesByEmail(request.getEmail());
		response.setGetZonesByEmail(zones);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public AddSubscriberResponse2 addSubscriber(
			final AddSubscriberRequest2 request) {
		final AddSubscriberResponse2 response = new AddSubscriberResponse2();
		boolean success = subscriptionServiceInternal.addSubscriber(
				request.getEmailId(), request.getMobile(), request.getZoneId(),
				request.getSubscriptionPage(),
				request.getOverrideUnsubscription(), request.getIsActive(),
				request.getAffiliateTrackingCode(),
				request.getUtmtrackingSource());
		response.setAddSubscriber(success);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetSubscriberProfileResponse getSubscriberProfile(
			GetSubscriberProfileRequest request) {
		GetSubscriberProfileResponse response = new GetSubscriberProfileResponse();
		SubscriberProfile profile = subscriptionServiceInternal
				.getSubscriberProfile(request.getEmail());
		SubscriberProfileSRO profileSRO = umsConvertorService
				.getSubscriberProfileSROFromEntity(profile);
		response.setSubscriberProfile(profileSRO);

		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public CreateSubscriberProfileResponse createSubscriberProfile(
			CreateSubscriberProfileRequest request) {
		CreateSubscriberProfileResponse response = new CreateSubscriberProfileResponse();
		SubscriberProfileSRO profileSRO = request.getProfile();
		SubscriberProfile entity = umsConvertorService
				.getSubscriberProfileEntityFromSRO(profileSRO);
		SubscriberProfile profile = subscriptionServiceInternal
				.createSubscriberProfile(entity);
		response.setCreateSubscriberProfile(umsConvertorService
				.getSubscriberProfileSROFromEntity(profile));
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public CreateSubscriberProfileResponse2 createSubscriberProfile(
			CreateSubscriberProfileRequest2 request) throws TransportException {
		CreateSubscriberProfileResponse2 response = new CreateSubscriberProfileResponse2();
		GetZoneByIdRequest zoneRequest = new GetZoneByIdRequest(
				request.getZone());
		GetZoneResponse zoneResponse = localityClient
				.getZoneByIdRequest(zoneRequest);
		SubscriberProfile profile = subscriptionServiceInternal
				.createSubscriberProfile(request.getEmail(), request.getName(),
						request.getName(), request.getGender(),
						request.getBirthday(), request.getLocalityId(),
						zoneResponse.getZone());
		SubscriberProfileSRO profileSRO = umsConvertorService
				.getSubscriberProfileSROFromEntity(profile);
		response.setSubscriberProfile(profileSRO);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public UpdateSubscriberProfileResponse updateSubscriberProfile(
			UpdateSubscriberProfileRequest request) {
		UpdateSubscriberProfileResponse response = new UpdateSubscriberProfileResponse();
		SubscriberProfileSRO requestSRO = request.getProfile();
		SubscriberProfile profile = subscriptionServiceInternal
				.updateSubscriberProfile(umsConvertorService
						.getSubscriberProfileEntityFromSRO(requestSRO));
		SubscriberProfileSRO profileSRO = umsConvertorService
				.getSubscriberProfileSROFromEntity(profile);
		response.setUpdatedSubscriberProfile(profileSRO);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public UpdateSubscriberProfileResponse2 updateSubscriberProfile(
			UpdateSubscriberProfileRequest2 request) throws TransportException {
		UpdateSubscriberProfileResponse2 response = new UpdateSubscriberProfileResponse2();
		GetZoneByIdRequest zoneRequest = new GetZoneByIdRequest(
				request.getZone());
		GetZoneResponse zoneResponse = localityClient
				.getZoneByIdRequest(zoneRequest);
		SubscriberProfile profile = subscriptionServiceInternal
				.updateSubscriberProfile(request.getProfile(),
						request.getName(), request.getDisplayName(),
						request.getGender(), request.getBirthday(),
						request.getLocalityId(), zoneResponse.getZone());
		SubscriberProfileSRO profileSRO = umsConvertorService
				.getSubscriberProfileSROFromEntity(profile);
		response.setUpdatedSubscriberProfile(profileSRO);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public UpdateMobileSubscriberResponse updateMobileSubscriber(
			UpdateMobileSubscriberRequest request) {
		UpdateMobileSubscriberResponse response = new UpdateMobileSubscriberResponse();
		MobileSubscriberSRO sro = request.getMobileSubscriber();
		MobileSubscriber entity = umsConvertorService
				.getMobileSubscriberEntityFromSRO(sro);
		subscriptionServiceInternal.updateMobileSubscriber(entity);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public UpdateEmailSubscriberResponse updateEmailSubscriber(
			UpdateEmailSubscriberRequest request) {
		UpdateEmailSubscriberResponse response = new UpdateEmailSubscriberResponse();
		EmailSubscriberSRO sro = request.getEmailSubscriberVerification();
		EmailSubscriber entity = umsConvertorService
				.getEmailSubscriberEntityFromSRO(sro);
		subscriptionServiceInternal.updateEmailSubscriber(entity);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public GetEmailSubscriberByEmailResponse getEmailSubscriberByEmail(
			GetEmailSubscriberByEmailRequest request) {
		GetEmailSubscriberByEmailResponse response = new GetEmailSubscriberByEmailResponse();
		List<EmailSubscriber> subs = subscriptionServiceInternal
				.getEmailSubscriberByEmail(request.getEmail());
		List<EmailSubscriberSRO> subSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber entity : subs)
			subSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(entity));
		response.setEmailSubscriberByEmail(subSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetSubscriberCityMigrationResponse getSubscriberCityMigration(
			GetSubscriberCityMigrationRequest request) {
		GetSubscriberCityMigrationResponse response = new GetSubscriberCityMigrationResponse();
		SubscriberCityMigration scm = subscriptionServiceInternal
				.getSubscriberCityMigration(request.getEmailId(),
						request.getCity());
		response.setGetSubscriberCityMigration(scm);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public SaveSubscriberCityMigrationResponse saveSubscriberCityMigration(
			SaveSubscriberCityMigrationRequest request) {
		SaveSubscriberCityMigrationResponse response = new SaveSubscriberCityMigrationResponse();
		subscriptionServiceInternal.saveSubscriberCityMigration(request
				.getSubscriberCityMigration());

		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public FindEmailSubscriberWithPreferenceByEmailResponse findEmailSubscriberWithPreferenceByEmail(
			FindEmailSubscriberWithPreferenceByEmailRequest request) {
		FindEmailSubscriberWithPreferenceByEmailResponse response = new FindEmailSubscriberWithPreferenceByEmailResponse();
		subscriptionServiceInternal
				.findEmailSubscriberWithPreferenceByEmail(request.getEmail());
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetNoPreferenceEmailSubscribersIncrementalResponse getNoPreferenceEmailSubscribersIncremental(
			GetNoPreferenceEmailSubscribersIncrementalRequest request) {
		GetNoPreferenceEmailSubscribersIncrementalResponse response = new GetNoPreferenceEmailSubscribersIncrementalResponse();
		List<EmailSubscriber> subs = subscriptionServiceInternal
				.getNoPreferenceEmailSubscribersIncremental(
						request.getLastUpdated(), request.getFirstResult(),
						request.getMaxResults());
		List<EmailSubscriberSRO> subSROs = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber entity : subs)
			subSROs.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(entity));
		response.setEmailSubscribersIncremental(subSROs);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public GetZonesByMobileResponse getZonesByMobile(
			GetZonesByMobileRequest request) {
		GetZonesByMobileResponse response = new GetZonesByMobileResponse();
		List<Integer> zones = subscriptionServiceInternal
				.getZonesByMobile(request.getMobile());
		response.setGetZonesByMobile(zones);
		response.setSuccessful(true);
		return response;
	}

	@Deprecated
	@Override
	public AffiliateSubscriptionOfferResponse getAffiliateSubscriptionOfferByName(
			AffiliateSubscriptionOfferRequest request) {
		AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
		AffiliateSubscriptionOffer offer = subscriptionServiceInternal
				.getAffiliateSubscriptionOfferByName(request.getOfferName());
		AffiliateSubscriptionOfferSRO offerSRO = umsConvertorService
				.getAffiliateSubscriptionOfferSROFromEntity(offer);
		response.setAffiliateSubscriptionOfferSRO(offerSRO);
		response.setSuccessful(true);
		return response;
	}

	@Override
	public FindMobileSubscriberByMobileResponse findMobileSubscriberByMobile(
			FindMobileSubscriberByMobileRequest request) {
		FindMobileSubscriberByMobileResponse response = new FindMobileSubscriberByMobileResponse();
		response.setMobileSubscriberSRO(umsConvertorService
				.getMobileSubscriberSROFromEntity(subscriptionServiceInternal
						.findMobileSubscriberByMobile(request.getMobile())));
		return response;
	}

	@Deprecated
	@Override
	public GetSubscribedEmailSubscribersResponse getSubscribedEmailSubscribers(
			GetSubscribedEmailSubscribersRequest request) {
		GetSubscribedEmailSubscribersResponse response = new GetSubscribedEmailSubscribersResponse();
		List<EmailSubscriberSRO> emailSubscriberSRO = new ArrayList<EmailSubscriberSRO>();
		for (EmailSubscriber emailSubsriber : subscriptionServiceInternal
				.getSubscribedEmailSubscribers(request.getEmail())) {
			emailSubscriberSRO.add(umsConvertorService
					.getEmailSubscriberSROFromEntity(emailSubsriber));
		}
		response.setEmailSubscriberSRO(emailSubscriberSRO);
		return response;
	}

	@Deprecated
	@Override
	public AffiliateSubscriptionOfferResponse getSubscriptionOfferById(
			AffiliateSubscriptionOfferRequest request) {

		AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
		if (request.getId() > 0) {
			response.setAffiliateSubscriptionOfferSRO(umsConvertorService
					.getAffiliateSubscriptionOfferSROFromEntity(subscriptionServiceInternal
							.getSubscriptionOfferById(request.getId())));
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
		}
		return response;
	}

	@Deprecated
	@Override
	public AffiliateSubscriptionOfferResponse mergeAffiliateSubscriptionOffer(
			AffiliateSubscriptionOfferRequest request) {
		AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
		if (request.getAffiliateSubscriptionOffer() != null) {
			subscriptionServiceInternal
					.mergeAffiliateSubscriptionOffer(umsConvertorService
							.getAffiliateSubscriptionOfferEntityFromSRO(request
									.getAffiliateSubscriptionOffer()));
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
		}
		return response;
	}

	@Deprecated
	@Override
	public AffiliateSubscriptionOfferResponse getAllAffiliateSubscriptionOffers(
			AffiliateSubscriptionOfferRequest request) {
		AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
		if (request != null) {
			List<AffiliateSubscriptionOfferSRO> affiliateSubscriptionOfferSRO = new ArrayList<AffiliateSubscriptionOfferSRO>();
			for (AffiliateSubscriptionOffer aso : subscriptionServiceInternal
					.getAllAffiliateSubscriptionOffers()) {
				response.getAffiliateSubscriptionOfferSROs()
						.add(umsConvertorService
								.getAffiliateSubscriptionOfferSROFromEntity(aso));
			}
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
		}
		return response;
	}

	@Deprecated
	@Override
	public AffiliateSubscriptionOfferResponse getAllEnabledAffiliateSubscriptionOffers(
			AffiliateSubscriptionOfferRequest request) {
		AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
		if (request != null) {
			List<AffiliateSubscriptionOfferSRO> affiliateSubscriptionOfferSRO = new ArrayList<AffiliateSubscriptionOfferSRO>();
			for (AffiliateSubscriptionOffer aso : subscriptionServiceInternal
					.getAllEnabledAffiliateSubscriptionOffers()) {
				response.getAffiliateSubscriptionOfferSROs()
						.add(umsConvertorService
								.getAffiliateSubscriptionOfferSROFromEntity(aso));
			}
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
		}
		return response;
	}

	@Deprecated
	@Override
	public DeleteMobileSubscriberResponse deleteSubscribers(
			DeleteMobileSubscriberRequest request) {
		DeleteMobileSubscriberResponse response = new DeleteMobileSubscriberResponse();
		if (request != null) {
			subscriptionServiceInternal.deleteSubscribers(request.getMobile());
			response.setSuccessful(true);
		} else {
			response.setSuccessful(false);
		}
		return response;
	}
}
