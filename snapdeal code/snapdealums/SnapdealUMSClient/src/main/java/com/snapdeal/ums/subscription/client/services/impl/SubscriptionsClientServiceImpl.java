package com.snapdeal.ums.subscription.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.subscription.client.services.ISubscriptionsClientService;
import com.snapdeal.ums.subscription.ext.subscription.*;

@Service("SubscriptionsClientService")
public class SubscriptionsClientServiceImpl implements ISubscriptionsClientService {

    private final static String CLIENT_SERVICE_URL = "/subscription";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(SubscriptionsClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/subscription/", "subscriptionserver.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    @Override
    public FindMobileSubscriberByMobileResponse findMobileSubscriberByMobile(FindMobileSubscriberByMobileRequest request) {
        FindMobileSubscriberByMobileResponse response = new FindMobileSubscriberByMobileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/findMobileSubscriberByMobile";
            response = (FindMobileSubscriberByMobileResponse) transportService.executeRequest(url, request, null, FindMobileSubscriberByMobileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetSubscribedEmailSubscribersResponse getSubscribedEmailSubscribers(GetSubscribedEmailSubscribersRequest request) {
        GetSubscribedEmailSubscribersResponse response = new GetSubscribedEmailSubscribersResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSubscribedEmailSubscribers";
            response = (GetSubscribedEmailSubscribersResponse) transportService.executeRequest(url, request, null, GetSubscribedEmailSubscribersResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public AddEmailSubscriberResponse4 addEmailSubscriber(AddEmailSubscriberRequest4 request) {
        AddEmailSubscriberResponse4 response = new AddEmailSubscriberResponse4();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addEmailSubscriber4";
            response = (AddEmailSubscriberResponse4) transportService.executeRequest(url, request, null, AddEmailSubscriberResponse4.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public AddMobileSubscriberResponse4 addMobileSubscriber(AddMobileSubscriberRequest4 request) {
        AddMobileSubscriberResponse4 response = new AddMobileSubscriberResponse4();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addMobileSubscriber4";
            response = (AddMobileSubscriberResponse4) transportService.executeRequest(url, request, null, AddMobileSubscriberResponse4.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public UnsubscribeEmailResponse unsubscribeEmail(UnsubscribeEmailRequest request) {
        UnsubscribeEmailResponse response = new UnsubscribeEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/unsubscribeEmail";
            response = (UnsubscribeEmailResponse) transportService.executeRequest(url, request, null, UnsubscribeEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public UnsubscribeEmailResponse2 unsubscribeEmail(UnsubscribeEmailRequest2 request) {
        UnsubscribeEmailResponse2 response = new UnsubscribeEmailResponse2();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/unsubscribeEmail2";
            response = (UnsubscribeEmailResponse2) transportService.executeRequest(url, request, null, UnsubscribeEmailResponse2.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UnsubscribeMobileResponse unsubscribeMobile(UnsubscribeMobileRequest request) {
        UnsubscribeMobileResponse response = new UnsubscribeMobileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/unsubscribeMobile";
            response = (UnsubscribeMobileResponse) transportService.executeRequest(url, request, null, UnsubscribeMobileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UnsubscribeMobileResponse2 unsubscribeMobile(UnsubscribeMobileRequest2 request) {
        UnsubscribeMobileResponse2 response = new UnsubscribeMobileResponse2();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/unsubscribeMobile2";
            response = (UnsubscribeMobileResponse2) transportService.executeRequest(url, request, null, UnsubscribeMobileResponse2.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetEmailSubscriberDetailByEmailResponse getEmailSubscriberDetailByEmail(GetEmailSubscriberDetailByEmailRequest request) {
        GetEmailSubscriberDetailByEmailResponse response = new GetEmailSubscriberDetailByEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscriberDetailByEmail";
            response = (GetEmailSubscriberDetailByEmailResponse) transportService.executeRequest(url, request, null, GetEmailSubscriberDetailByEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public CreateEmailSubscriberDetailResponse createEmailSubscriberDetail(CreateEmailSubscriberDetailRequest request) {
        CreateEmailSubscriberDetailResponse response = new CreateEmailSubscriberDetailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createEmailSubscriberDetail";
            response = (CreateEmailSubscriberDetailResponse) transportService.executeRequest(url, request, null, CreateEmailSubscriberDetailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public UpdateEmailSubscriberVerificationResponse updateEmailSubscriberVerification(UpdateEmailSubscriberVerificationRequest request) {
        UpdateEmailSubscriberVerificationResponse response = new UpdateEmailSubscriberVerificationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateEmailSubscriberVerification";
            response = (UpdateEmailSubscriberVerificationResponse) transportService.executeRequest(url, request, null, UpdateEmailSubscriberVerificationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetMobileSubscriberDetailByMobileResponse getMobileSubscriberDetailByMobile(GetMobileSubscriberDetailByMobileRequest request) {
        GetMobileSubscriberDetailByMobileResponse response = new GetMobileSubscriberDetailByMobileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getMobileSubscriberDetailByMobile";
            response = (GetMobileSubscriberDetailByMobileResponse) transportService.executeRequest(url, request, null, GetMobileSubscriberDetailByMobileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public AddEmailMobileAssociationResponse addEmailMobileAssociation(AddEmailMobileAssociationRequest request) {
        AddEmailMobileAssociationResponse response = new AddEmailMobileAssociationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addEmailMobileAssociation";
            response = (AddEmailMobileAssociationResponse) transportService.executeRequest(url, request, null, AddEmailMobileAssociationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetEmailSubscriptionsResponse getEmailSubscriptions(GetEmailSubscriptionsRequest request) {
        GetEmailSubscriptionsResponse response = new GetEmailSubscriptionsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscriptions";
            response = (GetEmailSubscriptionsResponse) transportService.executeRequest(url, request, null, GetEmailSubscriptionsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetMobileSubscriptionsResponse getMobileSubscriptions(GetMobileSubscriptionsRequest request) {
        GetMobileSubscriptionsResponse response = new GetMobileSubscriptionsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getMobileSubscriptions";
            response = (GetMobileSubscriptionsResponse) transportService.executeRequest(url, request, null, GetMobileSubscriptionsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetEmailSubscriberByZoneResponse getEmailSubscriberByZone(GetEmailSubscriberByZoneRequest request) {
        GetEmailSubscriberByZoneResponse response = new GetEmailSubscriberByZoneResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscriberByZone";
            response = (GetEmailSubscriberByZoneResponse) transportService.executeRequest(url, request, null, GetEmailSubscriberByZoneResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetMobileSubscriberByZoneResponse getMobileSubscriberByZone(GetMobileSubscriberByZoneRequest request) {
        GetMobileSubscriberByZoneResponse response = new GetMobileSubscriberByZoneResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getMobileSubscriberByZone";
            response = (GetMobileSubscriberByZoneResponse) transportService.executeRequest(url, request, null, GetMobileSubscriberByZoneResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetMobileSubscriberResponse getMobileSubscriber(GetMobileSubscriberRequest request) {
        GetMobileSubscriberResponse response = new GetMobileSubscriberResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getMobileSubscriber";
            response = (GetMobileSubscriberResponse) transportService.executeRequest(url, request, null, GetMobileSubscriberResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public AddMobileSubscriberWithPinResponse addMobileSubscriberWithPin(AddMobileSubscriberWithPinRequest request) {
        AddMobileSubscriberWithPinResponse response = new AddMobileSubscriberWithPinResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addMobileSubscriberWithPin";
            response = (AddMobileSubscriberWithPinResponse) transportService.executeRequest(url, request, null, AddMobileSubscriberWithPinResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UpdateEmailSubscriberMemcacheResponse updateEmailSubscriberMemcache(UpdateEmailSubscriberMemcacheRequest request) {
        UpdateEmailSubscriberMemcacheResponse response = new UpdateEmailSubscriberMemcacheResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateEmailSubscriberMemcache";
            response = (UpdateEmailSubscriberMemcacheResponse) transportService.executeRequest(url, request, null, UpdateEmailSubscriberMemcacheResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public IsEmailMobileAssociationResponse isEmailMobileAssociation(IsEmailMobileAssociationRequest request) {
        IsEmailMobileAssociationResponse response = new IsEmailMobileAssociationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/isEmailMobileAssociation";
            response = (IsEmailMobileAssociationResponse) transportService.executeRequest(url, request, null, IsEmailMobileAssociationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetEmailAssociationByEmailResponse getEmailAssociationByEmail(GetEmailAssociationByEmailRequest request) {
        GetEmailAssociationByEmailResponse response = new GetEmailAssociationByEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailAssociationByEmail";
            response = (GetEmailAssociationByEmailResponse) transportService.executeRequest(url, request, null, GetEmailAssociationByEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetMobileAssociationByMobileResponse getMobileAssociationByMobile(GetMobileAssociationByMobileRequest request) {
        GetMobileAssociationByMobileResponse response = new GetMobileAssociationByMobileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getMobileAssociationByMobile";
            response = (GetMobileAssociationByMobileResponse) transportService.executeRequest(url, request, null, GetMobileAssociationByMobileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetMobileSubscribersResponse getMobileSubscribers(GetMobileSubscribersRequest request) {
        GetMobileSubscribersResponse response = new GetMobileSubscribersResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getMobileSubscribers";
            response = (GetMobileSubscribersResponse) transportService.executeRequest(url, request, null, GetMobileSubscribersResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetEmailSubscriberResponse getEmailSubscriber(GetEmailSubscriberRequest request) {
        GetEmailSubscriberResponse response = new GetEmailSubscriberResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscriber";
            response = (GetEmailSubscriberResponse) transportService.executeRequest(url, request, null, GetEmailSubscriberResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetEmailSubscribersIncrementalResponse getEmailSubscribersIncremental(GetEmailSubscribersIncrementalRequest request) {
        GetEmailSubscribersIncrementalResponse response = new GetEmailSubscribersIncrementalResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscribersIncremental";
            response = (GetEmailSubscribersIncrementalResponse) transportService.executeRequest(url, request, null, GetEmailSubscribersIncrementalResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetEmailSubscribersIncrementalByZoneDateAndIdResponse getEmailSubscribersIncrementalByZoneDateAndId(GetEmailSubscribersIncrementalByZoneDateAndIdRequest request) {
        GetEmailSubscribersIncrementalByZoneDateAndIdResponse response = new GetEmailSubscribersIncrementalByZoneDateAndIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscribersIncrementalByZoneDateAndId";
            response = (GetEmailSubscribersIncrementalByZoneDateAndIdResponse) transportService.executeRequest(url, request, null,
                    GetEmailSubscribersIncrementalByZoneDateAndIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetEmailSubscribersIncrementalByZoneResponse getEmailSubscribersIncrementalByZone(GetEmailSubscribersIncrementalByZoneRequest request) {
        GetEmailSubscribersIncrementalByZoneResponse response = new GetEmailSubscribersIncrementalByZoneResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscribersIncrementalByZone";
            response = (GetEmailSubscribersIncrementalByZoneResponse) transportService.executeRequest(url, request, null, GetEmailSubscribersIncrementalByZoneResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetEmailAllSubscribersIncrementalResponse getEmailAllSubscribersIncremental(GetEmailAllSubscribersIncrementalRequest request) {
        GetEmailAllSubscribersIncrementalResponse response = new GetEmailAllSubscribersIncrementalResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailAllSubscribersIncremental";
            response = (GetEmailAllSubscribersIncrementalResponse) transportService.executeRequest(url, request, null, GetEmailAllSubscribersIncrementalResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetEmailAllSubscribersIncrementalByDateAndIdResponse getEmailAllSubscribersIncrementalByDateAndId(GetEmailAllSubscribersIncrementalByDateAndIdRequest request) {
        GetEmailAllSubscribersIncrementalByDateAndIdResponse response = new GetEmailAllSubscribersIncrementalByDateAndIdResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailAllSubscribersIncrementalByDateAndId";
            response = (GetEmailAllSubscribersIncrementalByDateAndIdResponse) transportService.executeRequest(url, request, null,
                    GetEmailAllSubscribersIncrementalByDateAndIdResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetAllMobileSubscribersIncrementalResponse getAllMobileSubscribersIncremental(GetAllMobileSubscribersIncrementalRequest request) {
        GetAllMobileSubscribersIncrementalResponse response = new GetAllMobileSubscribersIncrementalResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllMobileSubscribersIncremental";
            response = (GetAllMobileSubscribersIncrementalResponse) transportService.executeRequest(url, request, null, GetAllMobileSubscribersIncrementalResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UpdateMobileSubscriberVerificationResponse updateMobileSubscriberVerification(UpdateMobileSubscriberVerificationRequest request) {
        UpdateMobileSubscriberVerificationResponse response = new UpdateMobileSubscriberVerificationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateMobileSubscriberVerification";
            response = (UpdateMobileSubscriberVerificationResponse) transportService.executeRequest(url, request, null, UpdateMobileSubscriberVerificationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public SearchZonesByMobileResponse searchZonesByMobile(SearchZonesByMobileRequest request) {
        SearchZonesByMobileResponse response = new SearchZonesByMobileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/searchZonesByMobile";
            response = (SearchZonesByMobileResponse) transportService.executeRequest(url, request, null, SearchZonesByMobileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public UnsububscribeMobileFromZonesResponse unsububscribeMobileFromZones(UnsububscribeMobileFromZonesRequest request) {
        UnsububscribeMobileFromZonesResponse response = new UnsububscribeMobileFromZonesResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/unsububscribeMobileFromZones";
            response = (UnsububscribeMobileFromZonesResponse) transportService.executeRequest(url, request, null, UnsububscribeMobileFromZonesResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public UnsububscribeEmailFromZonesResponse unsububscribeEmailFromZones(UnsububscribeEmailFromZonesRequest request) {
        UnsububscribeEmailFromZonesResponse response = new UnsububscribeEmailFromZonesResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/unsububscribeEmailFromZones";
            response = (UnsububscribeEmailFromZonesResponse) transportService.executeRequest(url, request, null, UnsububscribeEmailFromZonesResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetSubscribedMobileSubscribersResponse getSubscribedMobileSubscribers(GetSubscribedMobileSubscribersRequest request) {
        GetSubscribedMobileSubscribersResponse response = new GetSubscribedMobileSubscribersResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSubscribedMobileSubscribers";
            response = (GetSubscribedMobileSubscribersResponse) transportService.executeRequest(url, request, null, GetSubscribedMobileSubscribersResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public CreateMobileSubscriberDetailResponse createMobileSubscriberDetail(CreateMobileSubscriberDetailRequest request) {
        CreateMobileSubscriberDetailResponse response = new CreateMobileSubscriberDetailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createMobileSubscriberDetail";
            response = (CreateMobileSubscriberDetailResponse) transportService.executeRequest(url, request, null, CreateMobileSubscriberDetailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetEmailSubscriberDetailByUIDResponse getEmailSubscriberDetailByUID(GetEmailSubscriberDetailByUIDRequest request) {
        GetEmailSubscriberDetailByUIDResponse response = new GetEmailSubscriberDetailByUIDResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscriberDetailByUID";
            response = (GetEmailSubscriberDetailByUIDResponse) transportService.executeRequest(url, request, null, GetEmailSubscriberDetailByUIDResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public CreateMobileSubscriberResponse createMobileSubscriber(CreateMobileSubscriberRequest request) {
        CreateMobileSubscriberResponse response = new CreateMobileSubscriberResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createMobileSubscriber";
            response = (CreateMobileSubscriberResponse) transportService.executeRequest(url, request, null, CreateMobileSubscriberResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetMobileSubscriberPinResponse getMobileSubscriberPin(GetMobileSubscriberPinRequest request) {
        GetMobileSubscriberPinResponse response = new GetMobileSubscriberPinResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getMobileSubscriberPin";
            response = (GetMobileSubscriberPinResponse) transportService.executeRequest(url, request, null, GetMobileSubscriberPinResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetNewSubscriberInDateRangeResponse getNewSubscriberInDateRange(GetNewSubscriberInDateRangeRequest request) {
        GetNewSubscriberInDateRangeResponse response = new GetNewSubscriberInDateRangeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewSubscriberInDateRange";
            response = (GetNewSubscriberInDateRangeResponse) transportService.executeRequest(url, request, null, GetNewSubscriberInDateRangeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetNewMobileSubscriberByChannelInDateRangeResponse getNewMobileSubscriberByChannelInDateRange(GetNewMobileSubscriberByChannelInDateRangeRequest request) {
        GetNewMobileSubscriberByChannelInDateRangeResponse response = new GetNewMobileSubscriberByChannelInDateRangeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNewMobileSubscriberByChannelInDateRange";
            response = (GetNewMobileSubscriberByChannelInDateRangeResponse) transportService.executeRequest(url, request, null,
                    GetNewMobileSubscriberByChannelInDateRangeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetMobileSubscribersAndDNDStatusResponse getMobileSubscribersAndDNDStatus(GetMobileSubscribersAndDNDStatusRequest request) {
        GetMobileSubscribersAndDNDStatusResponse response = new GetMobileSubscribersAndDNDStatusResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getMobileSubscribersAndDNDStatus";
            response = (GetMobileSubscribersAndDNDStatusResponse) transportService.executeRequest(url, request, null, GetMobileSubscribersAndDNDStatusResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UpdateDNDStatusResponse updateDNDStatus(UpdateDNDStatusRequest request) {
        UpdateDNDStatusResponse response = new UpdateDNDStatusResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateDNDStatus";
            response = (UpdateDNDStatusResponse) transportService.executeRequest(url, request, null, UpdateDNDStatusResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public UpdateEmailMobileAssociationResponse updateEmailMobileAssociation(UpdateEmailMobileAssociationRequest request) {
        UpdateEmailMobileAssociationResponse response = new UpdateEmailMobileAssociationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateEmailMobileAssociation";
            response = (UpdateEmailMobileAssociationResponse) transportService.executeRequest(url, request, null, UpdateEmailMobileAssociationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public UpdateEmailSubscriberDetailResponse updateEmailSubscriberDetail(UpdateEmailSubscriberDetailRequest request) {
        UpdateEmailSubscriberDetailResponse response = new UpdateEmailSubscriberDetailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateEmailSubscriberDetail";
            response = (UpdateEmailSubscriberDetailResponse) transportService.executeRequest(url, request, null, UpdateEmailSubscriberDetailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetEmailMobileAssociationResponse getEmailMobileAssociation(GetEmailMobileAssociationRequest request) {
        GetEmailMobileAssociationResponse response = new GetEmailMobileAssociationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailMobileAssociation";
            response = (GetEmailMobileAssociationResponse) transportService.executeRequest(url, request, null, GetEmailMobileAssociationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetZonesByEmailResponse getZonesByEmail(GetZonesByEmailRequest request) {
        GetZonesByEmailResponse response = new GetZonesByEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getZonesByEmail";
            response = (GetZonesByEmailResponse) transportService.executeRequest(url, request, null, GetZonesByEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public AddSubscriberResponse2 addSubscriber(AddSubscriberRequest2 request) {
        AddSubscriberResponse2 response = new AddSubscriberResponse2();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/addSubscriber2";
            response = (AddSubscriberResponse2) transportService.executeRequest(url, request, null, AddSubscriberResponse2.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetSubscriberProfileResponse getSubscriberProfile(GetSubscriberProfileRequest request) {
        GetSubscriberProfileResponse response = new GetSubscriberProfileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSubscriberProfile";
            response = (GetSubscriberProfileResponse) transportService.executeRequest(url, request, null, GetSubscriberProfileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }
    @Deprecated
    public CreateSubscriberProfileResponse createSubscriberProfile(CreateSubscriberProfileRequest request) {
        CreateSubscriberProfileResponse response = new CreateSubscriberProfileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createSubscriberProfile";
            response = (CreateSubscriberProfileResponse) transportService.executeRequest(url, request, null, CreateSubscriberProfileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public CreateSubscriberProfileResponse2 createSubscriberProfile(CreateSubscriberProfileRequest2 request) {
        CreateSubscriberProfileResponse2 response = new CreateSubscriberProfileResponse2();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/createSubscriberProfile2";
            response = (CreateSubscriberProfileResponse2) transportService.executeRequest(url, request, null, CreateSubscriberProfileResponse2.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UpdateSubscriberProfileResponse updateSubscriberProfile(UpdateSubscriberProfileRequest request) {
        UpdateSubscriberProfileResponse response = new UpdateSubscriberProfileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateSubscriberProfile";
            response = (UpdateSubscriberProfileResponse) transportService.executeRequest(url, request, null, UpdateSubscriberProfileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UpdateSubscriberProfileResponse2 updateSubscriberProfile(UpdateSubscriberProfileRequest2 request) {
        UpdateSubscriberProfileResponse2 response = new UpdateSubscriberProfileResponse2();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateSubscriberProfile2";
            response = (UpdateSubscriberProfileResponse2) transportService.executeRequest(url, request, null, UpdateSubscriberProfileResponse2.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public UpdateMobileSubscriberResponse updateMobileSubscriber(UpdateMobileSubscriberRequest request) {
        UpdateMobileSubscriberResponse response = new UpdateMobileSubscriberResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateMobileSubscriber";
            response = (UpdateMobileSubscriberResponse) transportService.executeRequest(url, request, null, UpdateMobileSubscriberResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public UpdateEmailSubscriberResponse updateEmailSubscriber(UpdateEmailSubscriberRequest request) {
        UpdateEmailSubscriberResponse response = new UpdateEmailSubscriberResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/updateEmailSubscriber";
            response = (UpdateEmailSubscriberResponse) transportService.executeRequest(url, request, null, UpdateEmailSubscriberResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Override
    public GetEmailSubscriberByEmailResponse getEmailSubscriberByEmail(GetEmailSubscriberByEmailRequest request) {
        GetEmailSubscriberByEmailResponse response = new GetEmailSubscriberByEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getEmailSubscriberByEmail";
            response = (GetEmailSubscriberByEmailResponse) transportService.executeRequest(url, request, null, GetEmailSubscriberByEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetSubscriberCityMigrationResponse getSubscriberCityMigration(GetSubscriberCityMigrationRequest request) {
        GetSubscriberCityMigrationResponse response = new GetSubscriberCityMigrationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSubscriberCityMigration";
            response = (GetSubscriberCityMigrationResponse) transportService.executeRequest(url, request, null, GetSubscriberCityMigrationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public SaveSubscriberCityMigrationResponse saveSubscriberCityMigration(SaveSubscriberCityMigrationRequest request) {
        SaveSubscriberCityMigrationResponse response = new SaveSubscriberCityMigrationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/saveSubscriberCityMigration";
            response = (SaveSubscriberCityMigrationResponse) transportService.executeRequest(url, request, null, SaveSubscriberCityMigrationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public FindEmailSubscriberWithPreferenceByEmailResponse findEmailSubscriberWithPreferenceByEmail(FindEmailSubscriberWithPreferenceByEmailRequest request) {
        FindEmailSubscriberWithPreferenceByEmailResponse response = new FindEmailSubscriberWithPreferenceByEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/findEmailSubscriberWithPreferenceByEmail";
            response = (FindEmailSubscriberWithPreferenceByEmailResponse) transportService.executeRequest(url, request, null,
                    FindEmailSubscriberWithPreferenceByEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetNoPreferenceEmailSubscribersIncrementalResponse getNoPreferenceEmailSubscribersIncremental(GetNoPreferenceEmailSubscribersIncrementalRequest request) {
        GetNoPreferenceEmailSubscribersIncrementalResponse response = new GetNoPreferenceEmailSubscribersIncrementalResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getNoPreferenceEmailSubscribersIncremental";
            response = (GetNoPreferenceEmailSubscribersIncrementalResponse) transportService.executeRequest(url, request, null,
                    GetNoPreferenceEmailSubscribersIncrementalResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public GetZonesByMobileResponse getZonesByMobile(GetZonesByMobileRequest request) {
        GetZonesByMobileResponse response = new GetZonesByMobileResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getZonesByMobile";
            response = (GetZonesByMobileResponse) transportService.executeRequest(url, request, null, GetZonesByMobileResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public AffiliateSubscriptionOfferResponse getAffiliateSubscriptionOfferByName(AffiliateSubscriptionOfferRequest request) {
        AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAffiliateSubscriptionOfferByName";
            response = (AffiliateSubscriptionOfferResponse) transportService.executeRequest(url, request, null, AffiliateSubscriptionOfferResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public AffiliateSubscriptionOfferResponse getAllAffiliateSubscriptionOffers(AffiliateSubscriptionOfferRequest request) {
        AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllAffiliateSubscriptionOffers";
            response = (AffiliateSubscriptionOfferResponse) transportService.executeRequest(url, request, null, AffiliateSubscriptionOfferResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    
    @Override
    @Deprecated
    public AffiliateSubscriptionOfferResponse getAllEnabledAffiliateSubscriptionOffers(AffiliateSubscriptionOfferRequest request) {
        AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getAllEnabledAffiliateSubscriptionOffers";
            response = (AffiliateSubscriptionOfferResponse) transportService.executeRequest(url, request, null, AffiliateSubscriptionOfferResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public AffiliateSubscriptionOfferResponse mergeAffiliateSubscriptionOffer(AffiliateSubscriptionOfferRequest request) {
        AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/mergeAffiliateSubscriptionOffer";
            response = (AffiliateSubscriptionOfferResponse) transportService.executeRequest(url, request, null, AffiliateSubscriptionOfferResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public AffiliateSubscriptionOfferResponse getSubscriptionOfferById(AffiliateSubscriptionOfferRequest request) {
        AffiliateSubscriptionOfferResponse response = new AffiliateSubscriptionOfferResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getSubscriptionOfferById";
            response = (AffiliateSubscriptionOfferResponse) transportService.executeRequest(url, request, null, AffiliateSubscriptionOfferResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }

    @Deprecated
    public DeleteMobileSubscriberResponse deleteSubscribers(DeleteMobileSubscriberRequest request) {
        DeleteMobileSubscriberResponse response = new DeleteMobileSubscriberResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/deleteSubscribers";
            response = (DeleteMobileSubscriberResponse) transportService.executeRequest(url, request, null, DeleteMobileSubscriberResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message:", e);
        }
        return response;
    }
}
