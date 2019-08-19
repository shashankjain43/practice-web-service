package com.snapdeal.ums.admin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.admin.ext.newsletter.GetAllESPRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetAllESPResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetFailedCitiesForNewsletterRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetFailedCitiesForNewsletterResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByIdRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByIdResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByMsgIdRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterByMsgIdResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsRequest3;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterDetailsResponse3;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingForCityRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingForCityResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewsletterESPMappingResponse;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersRequest;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersRequest2;
import com.snapdeal.ums.admin.ext.newsletter.GetNewslettersResponse;
import com.snapdeal.ums.admin.ext.newsletter.PersistRequest;
import com.snapdeal.ums.admin.ext.newsletter.PersistRequest2;
import com.snapdeal.ums.admin.ext.newsletter.PersistResponse;
import com.snapdeal.ums.admin.ext.newsletter.PersistResponse2;
import com.snapdeal.ums.admin.ext.newsletter.SetNewsletterEspMappingFailedRequest;
import com.snapdeal.ums.admin.ext.newsletter.SetNewsletterEspMappingFailedResponse;
import com.snapdeal.ums.admin.ext.newsletter.UpdateRequest;
import com.snapdeal.ums.admin.ext.newsletter.UpdateResponse;
import com.snapdeal.ums.admin.server.services.INewsletterService;

@Controller
@RequestMapping("/service/ums/admin/newsletter/")
public class NewsletterServiceController {

    @Autowired
    private INewsletterService newsletterService;

    @RequestMapping(value = "update", produces = "application/sd-service")
    @ResponseBody
    public UpdateResponse update(@RequestBody UpdateRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        UpdateResponse response = newsletterService.update(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "persist", produces = "application/sd-service")
    @ResponseBody
    public PersistResponse persist(@RequestBody PersistRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        PersistResponse response = newsletterService.persist(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "persist2", produces = "application/sd-service")
    @ResponseBody
    public PersistResponse2 persist(@RequestBody PersistRequest2 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        PersistResponse2 response = newsletterService.persist(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletterDetails", produces = "application/sd-service")
    @ResponseBody
    public GetNewsletterDetailsResponse getNewsletterDetails(@RequestBody GetNewsletterDetailsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewsletterDetailsResponse response = newsletterService.getNewsletterDetails(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletterDetails2", produces = "application/sd-service")
    @ResponseBody
    public GetNewsletterDetailsResponse2 getNewsletterDetails(@RequestBody GetNewsletterDetailsRequest2 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewsletterDetailsResponse2 response = newsletterService.getNewsletterDetails(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletterDetails3", produces = "application/sd-service")
    @ResponseBody
    public GetNewsletterDetailsResponse3 getNewsletterDetails(@RequestBody GetNewsletterDetailsRequest3 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewsletterDetailsResponse3 response = newsletterService.getNewsletterDetails(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletters", produces = "application/sd-service")
    @ResponseBody
    public GetNewslettersResponse getNewsletters(@RequestBody GetNewslettersRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewslettersResponse response = newsletterService.getNewsletters(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletters2", produces = "application/sd-service")
    @ResponseBody
    public GetNewslettersResponse getNewsletters(@RequestBody GetNewslettersRequest2 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewslettersResponse response = newsletterService.getNewsletters(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletterById", produces = "application/sd-service")
    @ResponseBody
    public GetNewsletterByIdResponse getNewsletterById(@RequestBody GetNewsletterByIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewsletterByIdResponse response = newsletterService.getNewsletterById(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletterByMsgId", produces = "application/sd-service")
    @ResponseBody
    public GetNewsletterByMsgIdResponse getNewsletterByMsgId(@RequestBody GetNewsletterByMsgIdRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewsletterByMsgIdResponse response = newsletterService.getNewsletterByMsgId(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletterESPMapping", produces = "application/sd-service")
    @ResponseBody
    public GetNewsletterESPMappingResponse getNewsletterESPMapping(@RequestBody GetNewsletterESPMappingRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewsletterESPMappingResponse response = newsletterService.getNewsletterESPMapping(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getNewsletterESPMappingForCity", produces = "application/sd-service")
    @ResponseBody
    public GetNewsletterESPMappingForCityResponse getNewsletterESPMappingForCity(@RequestBody GetNewsletterESPMappingForCityRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetNewsletterESPMappingForCityResponse response = newsletterService.getNewsletterESPMappingForCity(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "setNewsletterEspMappingFailed", produces = "application/sd-service")
    @ResponseBody
    public SetNewsletterEspMappingFailedResponse setNewsletterEspMappingFailed(@RequestBody SetNewsletterEspMappingFailedRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SetNewsletterEspMappingFailedResponse response = newsletterService.setNewsletterEspMappingFailed(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getFailedCitiesForNewsletter", produces = "application/sd-service")
    @ResponseBody
    public GetFailedCitiesForNewsletterResponse getFailedCitiesForNewsletter(@RequestBody GetFailedCitiesForNewsletterRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetFailedCitiesForNewsletterResponse response = newsletterService.getFailedCitiesForNewsletter(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "getAllESP", produces = "application/sd-service")
    @ResponseBody
    public GetAllESPResponse getAllESP(@RequestBody GetAllESPRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetAllESPResponse response = newsletterService.getAllESP(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
}
