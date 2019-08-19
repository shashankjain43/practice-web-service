
package com.snapdeal.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.subscription.ext.subscription.*;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsService;

@Controller
@RequestMapping("/service/ums/subscription/")
public class SubscriptionsServiceController {

    @Autowired
    private ISubscriptionsService subscriptionsService;


    @RequestMapping(value = "addEmailSubscriber4", produces = "application/sd-service")
    @ResponseBody
    public AddEmailSubscriberResponse4 addEmailSubscriber(@RequestBody AddEmailSubscriberRequest4 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AddEmailSubscriberResponse4 response = subscriptionsService.addEmailSubscriber(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addMobileSubscriber4", produces = "application/sd-service")
    @ResponseBody
    public AddMobileSubscriberResponse4 addMobileSubscriber(@RequestBody AddMobileSubscriberRequest4 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AddMobileSubscriberResponse4 response = subscriptionsService.addMobileSubscriber(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "unsubscribeEmail", produces = "application/sd-service")
    @ResponseBody
    public UnsubscribeEmailResponse unsubscribeEmail(@RequestBody UnsubscribeEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UnsubscribeEmailResponse response = subscriptionsService.unsubscribeEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "unsubscribeEmail2", produces = "application/sd-service")
    @ResponseBody
    public UnsubscribeEmailResponse2 unsubscribeEmail(@RequestBody UnsubscribeEmailRequest2 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UnsubscribeEmailResponse2 response = subscriptionsService.unsubscribeEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "findMobileSubscriberByMobile", produces = "application/sd-service")
    @ResponseBody
    public FindMobileSubscriberByMobileResponse findMobileSubscriberByMobile(@RequestBody FindMobileSubscriberByMobileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        FindMobileSubscriberByMobileResponse response = subscriptionsService.findMobileSubscriberByMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSubscribedEmailSubscribers", produces = "application/sd-service")
    @ResponseBody
    public GetSubscribedEmailSubscribersResponse getSubscribedEmailSubscribers(@RequestBody GetSubscribedEmailSubscribersRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetSubscribedEmailSubscribersResponse response = subscriptionsService.getSubscribedEmailSubscribers(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "unsubscribeMobile", produces = "application/sd-service")
    @ResponseBody
    public UnsubscribeMobileResponse unsubscribeMobile(@RequestBody UnsubscribeMobileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UnsubscribeMobileResponse response = subscriptionsService.unsubscribeMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "unsubscribeMobile2", produces = "application/sd-service")
    @ResponseBody
    public UnsubscribeMobileResponse2 unsubscribeMobile(@RequestBody UnsubscribeMobileRequest2 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UnsubscribeMobileResponse2 response = subscriptionsService.unsubscribeMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailSubscriberDetailByEmail", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscriberDetailByEmailResponse getEmailSubscriberDetailByEmail(@RequestBody GetEmailSubscriberDetailByEmailRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscriberDetailByEmailResponse response = subscriptionsService.getEmailSubscriberDetailByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createEmailSubscriberDetail", produces = "application/sd-service")
    @ResponseBody
    public CreateEmailSubscriberDetailResponse createEmailSubscriberDetail(@RequestBody CreateEmailSubscriberDetailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        CreateEmailSubscriberDetailResponse response = subscriptionsService.createEmailSubscriberDetail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateEmailSubscriberVerification", produces = "application/sd-service")
    @ResponseBody
    public UpdateEmailSubscriberVerificationResponse updateEmailSubscriberVerification(@RequestBody UpdateEmailSubscriberVerificationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UpdateEmailSubscriberVerificationResponse response = subscriptionsService.updateEmailSubscriberVerification(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getMobileSubscriberDetailByMobile", produces = "application/sd-service")
    @ResponseBody
    public GetMobileSubscriberDetailByMobileResponse getMobileSubscriberDetailByMobile(@RequestBody GetMobileSubscriberDetailByMobileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetMobileSubscriberDetailByMobileResponse response = subscriptionsService.getMobileSubscriberDetailByMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addEmailMobileAssociation", produces = "application/sd-service")
    @ResponseBody
    public AddEmailMobileAssociationResponse addEmailMobileAssociation(@RequestBody AddEmailMobileAssociationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AddEmailMobileAssociationResponse response = subscriptionsService.addEmailMobileAssociation(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailSubscriptions", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscriptionsResponse getEmailSubscriptions(@RequestBody GetEmailSubscriptionsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscriptionsResponse response = subscriptionsService.getEmailSubscriptions(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getMobileSubscriptions", produces = "application/sd-service")
    @ResponseBody
    public GetMobileSubscriptionsResponse getMobileSubscriptions(@RequestBody GetMobileSubscriptionsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetMobileSubscriptionsResponse response = subscriptionsService.getMobileSubscriptions(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailSubscriberByZone", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscriberByZoneResponse getEmailSubscriberByZone(@RequestBody GetEmailSubscriberByZoneRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscriberByZoneResponse response = subscriptionsService.getEmailSubscriberByZone(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getMobileSubscriberByZone", produces = "application/sd-service")
    @ResponseBody
    public GetMobileSubscriberByZoneResponse getMobileSubscriberByZone(@RequestBody GetMobileSubscriberByZoneRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetMobileSubscriberByZoneResponse response = subscriptionsService.getMobileSubscriberByZone(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getMobileSubscriber", produces = "application/sd-service")
    @ResponseBody
    public GetMobileSubscriberResponse getMobileSubscriber(@RequestBody GetMobileSubscriberRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetMobileSubscriberResponse response = subscriptionsService.getMobileSubscriber(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addMobileSubscriberWithPin", produces = "application/sd-service")
    @ResponseBody
    public AddMobileSubscriberWithPinResponse addMobileSubscriberWithPin(@RequestBody AddMobileSubscriberWithPinRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AddMobileSubscriberWithPinResponse response = subscriptionsService.addMobileSubscriberWithPin(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateEmailSubscriberMemcache", produces = "application/sd-service")
    @ResponseBody
    public UpdateEmailSubscriberMemcacheResponse updateEmailSubscriberMemcache(@RequestBody UpdateEmailSubscriberMemcacheRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UpdateEmailSubscriberMemcacheResponse response = subscriptionsService.updateEmailSubscriberMemcache(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "isEmailMobileAssociation", produces = "application/sd-service")
    @ResponseBody
    public IsEmailMobileAssociationResponse isEmailMobileAssociation(@RequestBody IsEmailMobileAssociationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        IsEmailMobileAssociationResponse response = subscriptionsService.isEmailMobileAssociation(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailAssociationByEmail", produces = "application/sd-service")
    @ResponseBody
    public GetEmailAssociationByEmailResponse getEmailAssociationByEmail(@RequestBody GetEmailAssociationByEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailAssociationByEmailResponse response = subscriptionsService.getEmailAssociationByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getMobileAssociationByMobile", produces = "application/sd-service")
    @ResponseBody
    public GetMobileAssociationByMobileResponse getMobileAssociationByMobile(@RequestBody GetMobileAssociationByMobileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetMobileAssociationByMobileResponse response = subscriptionsService.getMobileAssociationByMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getMobileSubscribers", produces = "application/sd-service")
    @ResponseBody
    public GetMobileSubscribersResponse getMobileSubscribers(@RequestBody GetMobileSubscribersRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetMobileSubscribersResponse response = subscriptionsService.getMobileSubscribers(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailSubscriber", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscriberResponse getEmailSubscriber(@RequestBody GetEmailSubscriberRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscriberResponse response = subscriptionsService.getEmailSubscriber(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailSubscribersIncrementalByZone", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscribersIncrementalByZoneResponse getEmailSubscribersIncrementalByZone(@RequestBody GetEmailSubscribersIncrementalByZoneRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscribersIncrementalByZoneResponse response = subscriptionsService.getEmailSubscribersIncrementalByZone(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    
    @RequestMapping(value = "getEmailSubscribersIncrementalByZoneDateAndId", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscribersIncrementalByZoneDateAndIdResponse getEmailSubscribersIncrementalByZoneDateAndId(@RequestBody GetEmailSubscribersIncrementalByZoneDateAndIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscribersIncrementalByZoneDateAndIdResponse response = subscriptionsService.getEmailSubscribersIncrementalByZoneDateAndId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "getEmailSubscribersIncremental", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscribersIncrementalResponse getEmailSubscribersIncremental(@RequestBody GetEmailSubscribersIncrementalRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscribersIncrementalResponse response = subscriptionsService.getEmailSubscribersIncremental(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    @RequestMapping(value = "getEmailAllSubscribersIncremental", produces = "application/sd-service")
    @ResponseBody
    public GetEmailAllSubscribersIncrementalResponse getEmailAllSubscribersIncremental(@RequestBody GetEmailAllSubscribersIncrementalRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailAllSubscribersIncrementalResponse response = subscriptionsService.getEmailAllSubscribersIncremental(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailAllSubscribersIncrementalByDateAndId", produces = "application/sd-service")
    @ResponseBody
    public GetEmailAllSubscribersIncrementalByDateAndIdResponse getEmailAllSubscribersIncrementalByDateAndId(@RequestBody GetEmailAllSubscribersIncrementalByDateAndIdRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailAllSubscribersIncrementalByDateAndIdResponse response = subscriptionsService.getEmailAllSubscribersIncrementalByDateAndId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "getAllMobileSubscribersIncremental", produces = "application/sd-service")
    @ResponseBody
    public GetAllMobileSubscribersIncrementalResponse getAllMobileSubscribersIncremental(@RequestBody GetAllMobileSubscribersIncrementalRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetAllMobileSubscribersIncrementalResponse response = subscriptionsService.getAllMobileSubscribersIncremental(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateMobileSubscriberVerification", produces = "application/sd-service")
    @ResponseBody
    public UpdateMobileSubscriberVerificationResponse updateMobileSubscriberVerification(@RequestBody UpdateMobileSubscriberVerificationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UpdateMobileSubscriberVerificationResponse response = subscriptionsService.updateMobileSubscriberVerification(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "searchZonesByMobile", produces = "application/sd-service")
    @ResponseBody
    public SearchZonesByMobileResponse searchZonesByMobile(@RequestBody SearchZonesByMobileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        SearchZonesByMobileResponse response = subscriptionsService.searchZonesByMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "unsububscribeMobileFromZones", produces = "application/sd-service")
    @ResponseBody
    public UnsububscribeMobileFromZonesResponse unsububscribeMobileFromZones(@RequestBody UnsububscribeMobileFromZonesRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UnsububscribeMobileFromZonesResponse response = subscriptionsService.unsububscribeMobileFromZones(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "unsububscribeEmailFromZones", produces = "application/sd-service")
    @ResponseBody
    public UnsububscribeEmailFromZonesResponse unsububscribeEmailFromZones(@RequestBody UnsububscribeEmailFromZonesRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UnsububscribeEmailFromZonesResponse response = subscriptionsService.unsububscribeEmailFromZones(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSubscribedMobileSubscribers", produces = "application/sd-service")
    @ResponseBody
    public GetSubscribedMobileSubscribersResponse getSubscribedMobileSubscribers(@RequestBody GetSubscribedMobileSubscribersRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetSubscribedMobileSubscribersResponse response = subscriptionsService.getSubscribedMobileSubscribers(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createMobileSubscriberDetail", produces = "application/sd-service")
    @ResponseBody
    public CreateMobileSubscriberDetailResponse createMobileSubscriberDetail(@RequestBody CreateMobileSubscriberDetailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        CreateMobileSubscriberDetailResponse response = subscriptionsService.createMobileSubscriberDetail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailSubscriberDetailByUID", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscriberDetailByUIDResponse getEmailSubscriberDetailByUID(@RequestBody GetEmailSubscriberDetailByUIDRequest request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscriberDetailByUIDResponse response = subscriptionsService.getEmailSubscriberDetailByUID(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createMobileSubscriber", produces = "application/sd-service")
    @ResponseBody
    public CreateMobileSubscriberResponse createMobileSubscriber(@RequestBody CreateMobileSubscriberRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        CreateMobileSubscriberResponse response = subscriptionsService.createMobileSubscriber(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getMobileSubscriberPin", produces = "application/sd-service")
    @ResponseBody
    public GetMobileSubscriberPinResponse getMobileSubscriberPin(@RequestBody GetMobileSubscriberPinRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetMobileSubscriberPinResponse response = subscriptionsService.getMobileSubscriberPin(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewSubscriberInDateRange", produces = "application/sd-service")
    @ResponseBody
    public GetNewSubscriberInDateRangeResponse getNewSubscriberInDateRange(@RequestBody GetNewSubscriberInDateRangeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetNewSubscriberInDateRangeResponse response = subscriptionsService.getNewSubscriberInDateRange(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewMobileSubscriberByChannelInDateRange", produces = "application/sd-service")
    @ResponseBody
    public GetNewMobileSubscriberByChannelInDateRangeResponse getNewMobileSubscriberByChannelInDateRange(@RequestBody GetNewMobileSubscriberByChannelInDateRangeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetNewMobileSubscriberByChannelInDateRangeResponse response = subscriptionsService.getNewMobileSubscriberByChannelInDateRange(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getMobileSubscribersAndDNDStatus", produces = "application/sd-service")
    @ResponseBody
    public GetMobileSubscribersAndDNDStatusResponse getMobileSubscribersAndDNDStatus(@RequestBody GetMobileSubscribersAndDNDStatusRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetMobileSubscribersAndDNDStatusResponse response = subscriptionsService.getMobileSubscribersAndDNDStatus(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateDNDStatus", produces = "application/sd-service")
    @ResponseBody
    public UpdateDNDStatusResponse updateDNDStatus(@RequestBody UpdateDNDStatusRequest request)
        throws TransportException
    {
        UpdateDNDStatusResponse response = subscriptionsService.updateDNDStatus(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateEmailMobileAssociation", produces = "application/sd-service")
    @ResponseBody
    public UpdateEmailMobileAssociationResponse updateEmailMobileAssociation(@RequestBody UpdateEmailMobileAssociationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UpdateEmailMobileAssociationResponse response = subscriptionsService.updateEmailMobileAssociation(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateEmailSubscriberDetail", produces = "application/sd-service")
    @ResponseBody
    public UpdateEmailSubscriberDetailResponse updateEmailSubscriberDetail(@RequestBody UpdateEmailSubscriberDetailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UpdateEmailSubscriberDetailResponse response = subscriptionsService.updateEmailSubscriberDetail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailMobileAssociation", produces = "application/sd-service")
    @ResponseBody
    public GetEmailMobileAssociationResponse getEmailMobileAssociation(@RequestBody GetEmailMobileAssociationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailMobileAssociationResponse response = subscriptionsService.getEmailMobileAssociation(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getZonesByEmail", produces = "application/sd-service")
    @ResponseBody
    public GetZonesByEmailResponse getZonesByEmail(@RequestBody GetZonesByEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetZonesByEmailResponse response = subscriptionsService.getZonesByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "addSubscriber2", produces = "application/sd-service")
    @ResponseBody
    public AddSubscriberResponse2 addSubscriber(@RequestBody AddSubscriberRequest2 request, HttpServletRequest httpServletRequest  , 
            HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AddSubscriberResponse2 response = subscriptionsService.addSubscriber(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSubscriberProfile", produces = "application/sd-service")
    @ResponseBody
    public GetSubscriberProfileResponse getSubscriberProfile(@RequestBody GetSubscriberProfileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetSubscriberProfileResponse response = subscriptionsService.getSubscriberProfile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createSubscriberProfile", produces = "application/sd-service")
    @ResponseBody
    public CreateSubscriberProfileResponse createSubscriberProfile(@RequestBody CreateSubscriberProfileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        CreateSubscriberProfileResponse response = subscriptionsService.createSubscriberProfile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "createSubscriberProfile2", produces = "application/sd-service")
    @ResponseBody
    public CreateSubscriberProfileResponse2 createSubscriberProfile(@RequestBody CreateSubscriberProfileRequest2 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        CreateSubscriberProfileResponse2 response = subscriptionsService.createSubscriberProfile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateSubscriberProfile", produces = "application/sd-service")
    @ResponseBody
    public UpdateSubscriberProfileResponse updateSubscriberProfile(@RequestBody UpdateSubscriberProfileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UpdateSubscriberProfileResponse response = subscriptionsService.updateSubscriberProfile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateSubscriberProfile2", produces = "application/sd-service")
    @ResponseBody
    public UpdateSubscriberProfileResponse2 updateSubscriberProfile(@RequestBody UpdateSubscriberProfileRequest2 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UpdateSubscriberProfileResponse2 response = subscriptionsService.updateSubscriberProfile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateMobileSubscriber", produces = "application/sd-service")
    @ResponseBody
    public UpdateMobileSubscriberResponse updateMobileSubscriber(@RequestBody UpdateMobileSubscriberRequest request)
        throws TransportException
    {
        UpdateMobileSubscriberResponse response = subscriptionsService.updateMobileSubscriber(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "updateEmailSubscriber", produces = "application/sd-service")
    @ResponseBody
    public UpdateEmailSubscriberResponse updateEmailSubscriber(@RequestBody UpdateEmailSubscriberRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        UpdateEmailSubscriberResponse response = subscriptionsService.updateEmailSubscriber(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getEmailSubscriberByEmail", produces = "application/sd-service")
    @ResponseBody
    public GetEmailSubscriberByEmailResponse getEmailSubscriberByEmail(@RequestBody GetEmailSubscriberByEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetEmailSubscriberByEmailResponse response = subscriptionsService.getEmailSubscriberByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getSubscriberCityMigration", produces = "application/sd-service")
    @ResponseBody
    public GetSubscriberCityMigrationResponse getSubscriberCityMigration(@RequestBody GetSubscriberCityMigrationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetSubscriberCityMigrationResponse response = subscriptionsService.getSubscriberCityMigration(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "saveSubscriberCityMigration", produces = "application/sd-service")
    @ResponseBody
    public SaveSubscriberCityMigrationResponse saveSubscriberCityMigration(@RequestBody SaveSubscriberCityMigrationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        SaveSubscriberCityMigrationResponse response = subscriptionsService.saveSubscriberCityMigration(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "findEmailSubscriberWithPreferenceByEmail", produces = "application/sd-service")
    @ResponseBody
    public FindEmailSubscriberWithPreferenceByEmailResponse findEmailSubscriberWithPreferenceByEmail(@RequestBody FindEmailSubscriberWithPreferenceByEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        FindEmailSubscriberWithPreferenceByEmailResponse response = subscriptionsService.findEmailSubscriberWithPreferenceByEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNoPreferenceEmailSubscribersIncremental", produces = "application/sd-service")
    @ResponseBody
    public GetNoPreferenceEmailSubscribersIncrementalResponse getNoPreferenceEmailSubscribersIncremental(@RequestBody GetNoPreferenceEmailSubscribersIncrementalRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetNoPreferenceEmailSubscribersIncrementalResponse response = subscriptionsService.getNoPreferenceEmailSubscribersIncremental(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getZonesByMobile", produces = "application/sd-service")
    @ResponseBody
    public GetZonesByMobileResponse getZonesByMobile(@RequestBody GetZonesByMobileRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        GetZonesByMobileResponse response = subscriptionsService.getZonesByMobile(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getAffiliateSubscriptionOfferByName", produces = "application/sd-service")
    @ResponseBody
    public AffiliateSubscriptionOfferResponse getAffiliateSubscriptionOfferByName(@RequestBody AffiliateSubscriptionOfferRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AffiliateSubscriptionOfferResponse response = subscriptionsService.getAffiliateSubscriptionOfferByName(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "getAllAffiliateSubscriptionOffers", produces = "application/sd-service")
    @ResponseBody
    public AffiliateSubscriptionOfferResponse getAllAffiliateSubscriptionOffers(@RequestBody AffiliateSubscriptionOfferRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AffiliateSubscriptionOfferResponse response = subscriptionsService.getAllAffiliateSubscriptionOffers(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getAllEnabledAffiliateSubscriptionOffers", produces = "application/sd-service")
    @ResponseBody
    public AffiliateSubscriptionOfferResponse getAllEnabledAffiliateSubscriptionOffers(@RequestBody AffiliateSubscriptionOfferRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AffiliateSubscriptionOfferResponse response = subscriptionsService.getAllEnabledAffiliateSubscriptionOffers(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "mergeAffiliateSubscriptionOffer", produces = "application/sd-service")
    @ResponseBody
    public AffiliateSubscriptionOfferResponse mergeAffiliateSubscriptionOffer(@RequestBody AffiliateSubscriptionOfferRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AffiliateSubscriptionOfferResponse response = subscriptionsService.mergeAffiliateSubscriptionOffer(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    
    @RequestMapping(value = "getSubscriptionOfferById", produces = "application/sd-service")
    @ResponseBody
    public AffiliateSubscriptionOfferResponse getSubscriptionOfferById(@RequestBody AffiliateSubscriptionOfferRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        AffiliateSubscriptionOfferResponse response = subscriptionsService.getSubscriptionOfferById(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "deleteSubscribers", produces = "application/sd-service")
    @ResponseBody
    public DeleteMobileSubscriberResponse deleteSubscribers(@RequestBody DeleteMobileSubscriberRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
        throws TransportException
    {
        DeleteMobileSubscriberResponse response = subscriptionsService.deleteSubscribers(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
