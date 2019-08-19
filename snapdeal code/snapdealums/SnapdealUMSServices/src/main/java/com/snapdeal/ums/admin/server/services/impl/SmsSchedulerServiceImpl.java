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

import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerByIdRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerByIdResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListRequest2;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerListResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest2;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest3;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerRequest4;
import com.snapdeal.ums.admin.ext.smsscheduler.GetSmsSchedulerResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.PersistRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.PersistResponse;
import com.snapdeal.ums.admin.ext.smsscheduler.UpdateRequest;
import com.snapdeal.ums.admin.ext.smsscheduler.UpdateResponse;
import com.snapdeal.ums.admin.server.services.ISmsSchedulerService;
import com.snapdeal.ums.admin.server.services.ISmsSchedulerServiceInternal;
import com.snapdeal.ums.core.entity.SmsScheduler;
import com.snapdeal.ums.core.sro.smsscheduler.SmsSchedulerSRO;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;

@Service("umsSmsSchedulerService")
public class SmsSchedulerServiceImpl implements ISmsSchedulerService {
    
    @Autowired
    private ISmsSchedulerServiceInternal smsSchedulerServiceInternalImpl;
    @Autowired
    private IUMSConvertorService              umsConvertorService;
    
    @Override
    public UpdateResponse update(UpdateRequest request) {
        UpdateResponse response = new UpdateResponse();
        SmsSchedulerSRO sro = request.getSmsScheduler();
        if(sro != null){
            smsSchedulerServiceInternalImpl.update(umsConvertorService.getSmsSchedulerEntityFromSRO(sro));
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage(" Null SmsSchedulerSRO given in request" );
        }
        return response;
    }

    @Override
    public PersistResponse persist(PersistRequest request) {
        PersistResponse response = new PersistResponse();
        SmsSchedulerSRO sro = request.getSmsScheduler();
        if(sro != null){
            SmsScheduler entity = umsConvertorService.getSmsSchedulerEntityFromSRO(sro);
            smsSchedulerServiceInternalImpl.persist(entity);
             response.setSmsSchedulerId(entity.getId());
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage(" Null SmsSchedulerSRO given in request" );
        }
        return response;
    }

    @Deprecated
    @Override
    public GetSmsSchedulerByIdResponse getSmsSchedulerById(GetSmsSchedulerByIdRequest request) {
        GetSmsSchedulerByIdResponse response = new GetSmsSchedulerByIdResponse();
        int id = request.getId();
        if(id>0){
            SmsScheduler  smsScheduler = smsSchedulerServiceInternalImpl.getSmsSchedulerById(id);
            response.setGetSmsSchedulerById(umsConvertorService.getSmsSchedulerSROfromEntity(smsScheduler));
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage(" invalid id given in request "+id );
        }
        return response;
    }
    
    @Deprecated
    @Override
    public GetSmsSchedulerListResponse getSmsSchedulerList(GetSmsSchedulerListRequest request) {
        GetSmsSchedulerListResponse response = new GetSmsSchedulerListResponse();
        Date date = request.getDate();
        String status = request.getStatus();
        if((date != null) && (StringUtils.isNotEmpty(status))){
          List<SmsScheduler> smsSchedulerList =  smsSchedulerServiceInternalImpl.getSmsSchedulerList(date, status);
          List<SmsSchedulerSRO> sros = new ArrayList<SmsSchedulerSRO>();
          for(SmsScheduler smsScheduler : smsSchedulerList){
              sros.add(umsConvertorService.getSmsSchedulerSROfromEntity(smsScheduler));
          }
          response.setGetSmsSchedulerList(sros);
          response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage(" invalid date/staus given in request, date :"+date +" status :"+status);
        }
        
        return response;
    }

    @Override
    public GetSmsSchedulerListResponse getSmsSchedulerList(GetSmsSchedulerListRequest2 request) {
        GetSmsSchedulerListResponse response = new GetSmsSchedulerListResponse();
        Date date = request.getDate();
        if(date != null){
            List<SmsScheduler> smsSchedulers = smsSchedulerServiceInternalImpl.getSmsSchedulerList(date);
            List<SmsSchedulerSRO> smsSchedulerSROs = new ArrayList<SmsSchedulerSRO>();
            for(SmsScheduler smsScheduler : smsSchedulers)
                smsSchedulerSROs.add(umsConvertorService.getSmsSchedulerSROfromEntity(smsScheduler));
            response.setGetSmsSchedulerList(smsSchedulerSROs);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("invalid date in request "+date);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest request) {
        
        GetSmsSchedulerResponse response = new GetSmsSchedulerResponse();
        int cityId=  request.getCityId();
        Date date = request.getDate();
        String status = request.getStatus();
        if((cityId>0) && (date != null) && (StringUtils.isNotEmpty(status))){
            List<SmsScheduler> smsSchedulers = smsSchedulerServiceInternalImpl.getSmsScheduler(cityId, date, status);
            List<SmsSchedulerSRO> smsSchedulerSROs = new ArrayList<SmsSchedulerSRO>();
            for(SmsScheduler smsScheduler : smsSchedulers)
                smsSchedulerSROs.add(umsConvertorService.getSmsSchedulerSROfromEntity(smsScheduler));
            response.setGetSmsScheduler(smsSchedulerSROs);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("invalid date/cityId/status in request cityId :"+cityId +" date "+date + " status"+status);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest2 request) {
        GetSmsSchedulerResponse response = new GetSmsSchedulerResponse();
        List<Integer> cityIds = request.getCityId();
        Date  date = request.getDate();
        String status = request.getStatus();
        if((cityIds != null) && (date != null) && (StringUtils.isNotEmpty(status))){
            List<SmsScheduler> smsSchedulers =  smsSchedulerServiceInternalImpl.getSmsScheduler(cityIds, date, status);
            List<SmsSchedulerSRO> smsSchedulerSROs = new ArrayList<SmsSchedulerSRO>();
            for(SmsScheduler smsScheduler : smsSchedulers)
                smsSchedulerSROs.add(umsConvertorService.getSmsSchedulerSROfromEntity(smsScheduler));
            response.setGetSmsScheduler(smsSchedulerSROs);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("invalid date/cityIds/status in request cityIds :"+cityIds +" date "+date + " status"+status);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest3 request) {
        GetSmsSchedulerResponse response = new GetSmsSchedulerResponse();
        Date date = request.getDate();
        int cityId = request.getCityID();
       
        if((date!=null) && (cityId >0)){
            List<SmsScheduler> smsSchedulers =   smsSchedulerServiceInternalImpl.getSmsScheduler(cityId, date);
            List<SmsSchedulerSRO> smsSchedulerSROs = new ArrayList<SmsSchedulerSRO>();
            for(SmsScheduler smsScheduler : smsSchedulers)
                smsSchedulerSROs.add(umsConvertorService.getSmsSchedulerSROfromEntity(smsScheduler));
            response.setGetSmsScheduler(smsSchedulerSROs);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("invalid date/cityIdsin request cityIds :"+cityId +" date "+date);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetSmsSchedulerResponse getSmsScheduler(GetSmsSchedulerRequest4 request) {
        GetSmsSchedulerResponse response = new GetSmsSchedulerResponse();
        List<Integer> cityIds = request.getcityIds();
        Date  date = request.getStatus();
        if(( date !=null) && (cityIds != null)){
            List<SmsScheduler> smsSchedulers =  smsSchedulerServiceInternalImpl.getSmsScheduler(cityIds, date);
            List<SmsSchedulerSRO> smsSchedulerSROs = new ArrayList<SmsSchedulerSRO>();
            for(SmsScheduler smsScheduler : smsSchedulers)
                smsSchedulerSROs.add(umsConvertorService.getSmsSchedulerSROfromEntity(smsScheduler));
            response.setGetSmsScheduler(smsSchedulerSROs);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("invalid date/cityIds in request cityIds :"+cityIds +" date "+date);
        }
        return response;
    }

}

 