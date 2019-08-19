/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 25-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.admin.server.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.snapdeal.ums.admin.server.services.INewsletterServiceInternal;
import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.core.entity.Newsletter;
import com.snapdeal.ums.core.entity.NewsletterEspMapping;
import com.snapdeal.ums.core.sro.bulkemail.EmailServiceProviderSRO;
import com.snapdeal.ums.core.sro.newsletter.NewsletterEspMappingSRO;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;

@Service("umsNewsletterService")
public class NewsletterServiceImpl implements INewsletterService {

    @Autowired
    private INewsletterServiceInternal newsletterServiceInternalImpl;
    @Autowired
    private IUMSConvertorService          umsConvertorService;

    @Deprecated
    @Override
    public UpdateResponse update(UpdateRequest request) {
        UpdateResponse response = new UpdateResponse();
        NewsletterSRO sro = request.getNewsletter();
        if (sro != null) {
            Newsletter newsletter = newsletterServiceInternalImpl.update(umsConvertorService.getNewsletterEntityFromSRO(sro));
            NewsletterSRO updatedNewsletter = umsConvertorService.getNewsletterSROfromEntity(newsletter);
            response.setNewsletter(updatedNewsletter);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Request had null NewsletterSRO");
        }
        return response;
    }

    @Deprecated
    @Override
    public PersistResponse persist(PersistRequest request) {
        PersistResponse response = new PersistResponse();
        NewsletterSRO sro = request.getNewsletter();
        if (sro != null) {
            newsletterServiceInternalImpl.persist(umsConvertorService.getNewsletterEntityFromSRO(sro));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Request had null NewsletterSRO");
        }
        return response;
    }

    @Deprecated
    @Override
    public PersistResponse2 persist(PersistRequest2 request) {
        PersistResponse2 response = new PersistResponse2();
        NewsletterEspMappingSRO sro = request.getNewsletterEspMapping();
        if (sro != null) {
            newsletterServiceInternalImpl.persist(umsConvertorService.getNewsletterEspMappingEntityFromSRO(sro));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("NewsletterEspMappingSRO was null");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetNewsletterDetailsResponse getNewsletterDetails(GetNewsletterDetailsRequest request) {
        GetNewsletterDetailsResponse response = new GetNewsletterDetailsResponse();
        String cityId = request.getCityId();
        Date date = request.getDate();
        if(StringUtils.isNotEmpty(cityId) && (date != null)){
            Newsletter newsletter = newsletterServiceInternalImpl.getNewsletterDetails(cityId, date);
            NewsletterSRO sro =  umsConvertorService.getNewsletterSROfromEntity(newsletter);
            response.setGetNewsletterDetails(sro);
            response.setSuccessful(true);
        } else{
            response.setSuccessful(false);
            response.setMessage("cityID/date was null/empty");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetNewsletterDetailsResponse2 getNewsletterDetails(GetNewsletterDetailsRequest2 request) {
        
        GetNewsletterDetailsResponse2 response = new GetNewsletterDetailsResponse2();
        Date date = request.getDate();
        if(date != null){
          List<Newsletter> newsletters =  newsletterServiceInternalImpl.getNewsletterDetails(date);
          List<NewsletterSRO> newsletterSROs = new ArrayList<NewsletterSRO>();
          for(Newsletter newsletter : newsletters){
              newsletterSROs.add(umsConvertorService.getNewsletterSROfromEntity(newsletter));
          }
          response.setSuccessful(true);
          response.setGetNewsletterDetails(newsletterSROs);
        }else{
            response.setSuccessful(false);
            response.setMessage("date was null");
        }
        
        return response;
    }

    @Deprecated
    @Override
    public GetNewsletterDetailsResponse3 getNewsletterDetails(GetNewsletterDetailsRequest3 request) {
        GetNewsletterDetailsResponse3 response = new GetNewsletterDetailsResponse3();
        Date date = request.getDate();
        String state = request.getState();
        if((date !=null) && (StringUtils.isNotEmpty(state))){
            List<Newsletter> newsletters = newsletterServiceInternalImpl.getNewsletterDetails(date, state);
            List<NewsletterSRO> newsletterSROs = new ArrayList<NewsletterSRO>();
            for(Newsletter newsletter : newsletters){
                newsletterSROs.add(umsConvertorService.getNewsletterSROfromEntity(newsletter));
            }
            response.setGetNewsletterDetails(newsletterSROs);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("date/state was null/empty");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetNewslettersResponse getNewsletters(GetNewslettersRequest request) {
        
        GetNewslettersResponse response = new GetNewslettersResponse();
        String cityId = request.getCityId();
        Date date  = request.getDate();
        if(StringUtils.isNotEmpty(cityId) && (date != null)){
            List<Newsletter> newsletters =  newsletterServiceInternalImpl.getNewsletters(cityId, date);
            List<NewsletterSRO> newsletterSROs = new ArrayList<NewsletterSRO>();
            for(Newsletter newsletter : newsletters){
                newsletterSROs.add(umsConvertorService.getNewsletterSROfromEntity(newsletter));
            }
            response.setGetNewsletters(newsletterSROs);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("date/state was null/empty");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetNewslettersResponse getNewsletters(GetNewslettersRequest2 request) {
        GetNewslettersResponse response = new GetNewslettersResponse();
        String cityId = request.getCityId();
        Date date = request.getDate();
        String state = request.getState();
        if(StringUtils.isNotEmpty(cityId) &&(date != null) &&(StringUtils.isNotEmpty(state))){
            List<Newsletter> newsletters = newsletterServiceInternalImpl.getNewsletters(cityId, date, state);
            List<NewsletterSRO> newsletterSROs = new ArrayList<NewsletterSRO>();
            for(Newsletter newsletter : newsletters){
                newsletterSROs.add(umsConvertorService.getNewsletterSROfromEntity(newsletter));
            }
            response.setGetNewsletters(newsletterSROs);
            response.setSuccessful(true);
        }
        else{
            response.setSuccessful(false);
            response.setMessage("date/cityId/state was null/empty");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetNewsletterByIdResponse getNewsletterById(GetNewsletterByIdRequest request) {
        GetNewsletterByIdResponse response = new GetNewsletterByIdResponse();
        Integer id = request.getId();
        if(id > 0){
            Newsletter newsletter = newsletterServiceInternalImpl.getNewsletterById(id);
            NewsletterSRO sro = umsConvertorService.getNewsletterSROfromEntity(newsletter);
            response.setGetNewsletterById(sro);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("id was invalid :"+id);
        }
        
        return response;
    }

    @Override
    public GetNewsletterByMsgIdResponse getNewsletterByMsgId(GetNewsletterByMsgIdRequest request) {
        GetNewsletterByMsgIdResponse response = new GetNewsletterByMsgIdResponse();
        String msgId  = request.getMsgId();
        if(StringUtils.isNotEmpty(msgId)){
            Newsletter newsletter =  newsletterServiceInternalImpl.getNewsletterByMsgId(msgId);
            NewsletterSRO sro = umsConvertorService.getNewsletterSROfromEntity(newsletter);
            response.setGetNewsletterByMsgId(sro);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("Msgid was invalid :" + msgId);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetNewsletterESPMappingResponse getNewsletterESPMapping(GetNewsletterESPMappingRequest request) {
        GetNewsletterESPMappingResponse response = new GetNewsletterESPMappingResponse();
        int espId =  request.getEspId();
        int newsletterId = request.getNewsletterId();
        if((espId>0) &&( newsletterId >0)){
            List<NewsletterEspMapping> nlEspMappings =  newsletterServiceInternalImpl.getNewsletterESPMapping(newsletterId, espId);
            List<NewsletterEspMappingSRO> sros = new ArrayList<NewsletterEspMappingSRO>();
            for (NewsletterEspMapping mapping : nlEspMappings)
                    sros.add(umsConvertorService.getNewsletterEspMappingSRO(mapping));
            response.setGetNewsletterESPMapping(sros);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("espId/newsletterId  was invalid ");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetNewsletterESPMappingForCityResponse getNewsletterESPMappingForCity(GetNewsletterESPMappingForCityRequest request) {
        GetNewsletterESPMappingForCityResponse response = new GetNewsletterESPMappingForCityResponse();
        int cityId =  request.getCityId();
        int espId = request.getEspId();
        String filterType = request.getFilterType();
        int newsletterId =  request.getNewsletterId();
        if((cityId>0) &&(espId > 0)&&(StringUtils.isNotEmpty(filterType) && (newsletterId >0))){
            NewsletterEspMapping newsletterESPMapping = newsletterServiceInternalImpl.getNewsletterESPMappingForCity(newsletterId, espId, cityId, filterType);
            NewsletterEspMappingSRO sro = umsConvertorService.getNewsletterEspMappingSRO(newsletterESPMapping);
            response.setGetNewsletterESPMappingForCity(sro);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("espId/newsletterId/cityId/filterType were invalid ");
        }
        
        return response;
    }
    
    @Deprecated
    @Override
    public GetFailedCitiesForNewsletterResponse getFailedCitiesForNewsletter(GetFailedCitiesForNewsletterRequest request) {
        GetFailedCitiesForNewsletterResponse response = new GetFailedCitiesForNewsletterResponse();
        Integer id = request.getNewsletterId();
        if(id > 0){
            List<Integer> ids= newsletterServiceInternalImpl.getFailedCitiesForNewsletter(id);
            response.setGetFailedCitiesForNewsletter(ids);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("id was invalid :"+id);
        }
        
        return response;
    }
    
 
    @Override
    public SetNewsletterEspMappingFailedResponse setNewsletterEspMappingFailed(SetNewsletterEspMappingFailedRequest request) {
        SetNewsletterEspMappingFailedResponse response = new SetNewsletterEspMappingFailedResponse();
        
        if(request.getNewsletterId()>0 && request.getCityId() >0){
            newsletterServiceInternalImpl.setNewsletterEspMappingFailed(request.getNewsletterId(), request.getCityId());
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("either newsletter id was invalid :"+request.getNewsletterId() +" or ApiServiceImpl.java ");
        }
        
        return response;
    }

    @Override
    public GetAllESPResponse getAllESP(GetAllESPRequest request) {
        GetAllESPResponse response = new GetAllESPResponse();
        for(EmailServiceProvider esp:newsletterServiceInternalImpl.getAllESP()){
            response.getEspSROs().add(umsConvertorService.getEmailServiceProviderSROFromEntity(esp));
        }
        return response;
    }
}
