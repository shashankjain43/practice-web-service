/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 26-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.core.entity.ESPFilterCityMapping;
import com.snapdeal.ums.core.entity.ESPProfileField;
import com.snapdeal.ums.core.entity.EmailBulkEspCityMapping;
import com.snapdeal.ums.admin.ext.bulkemail.GetAllEmailBulkEspCityMappingRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetAllEmailBulkEspCityMappingResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetBulkEspCityMappingForCityRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetBulkEspCityMappingForCityResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetFiltersForCityRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetFiltersForCityResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetProfileFieldsForESPRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetProfileFieldsForESPResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsBounceRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsBounceResponse;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsMauRequest;
import com.snapdeal.ums.admin.ext.bulkemail.GetResultsMauResponse;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateFilterCityMappingRequest;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateFilterCityMappingResponse;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateRequest;
import com.snapdeal.ums.admin.ext.bulkemail.UpdateResponse;
import com.snapdeal.ums.core.sro.bulkemail.ESPFilterCityMappingSRO;
import com.snapdeal.ums.core.sro.bulkemail.ESPProfileFieldSRO;
import com.snapdeal.ums.core.sro.bulkemail.EmailBulkEspCityMappingSRO;
import com.snapdeal.ums.server.services.IBulkEmailService;
import com.snapdeal.ums.server.services.IBulkEmailServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;

@Service("umsBulkEmailService")
public class BulkEmailServiceImpl implements IBulkEmailService {
    @Autowired
    private IBulkEmailServiceInternal bulkEmailServiceInternal;
    @Autowired
    private IUMSConvertorService      umsConvertorService;

    @Override
    public UpdateResponse update(UpdateRequest request) {
        UpdateResponse response = new UpdateResponse();
        EmailBulkEspCityMappingSRO sro = request.getFilterCityMapping();

        if (sro != null) {
            EmailBulkEspCityMapping mapping = bulkEmailServiceInternal.update(umsConvertorService.getEmailBulkEspCityMappingEntityFromSRO(sro));
            EmailBulkEspCityMappingSRO responseSRO = umsConvertorService.getEmailBulkEspCityMappingSROFromEntity(mapping);
            response.setUpdate(responseSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("request contained null EmailBulkEspCityMappingSRO");
        }
        return response;
    }

    @Deprecated
    @Override
    public UpdateFilterCityMappingResponse updateFilterCityMapping(UpdateFilterCityMappingRequest request) {
        UpdateFilterCityMappingResponse response = new UpdateFilterCityMappingResponse();
        ESPFilterCityMappingSRO sro = request.getFilterCityMapping();
        if (sro != null) {
            bulkEmailServiceInternal.updateFilterCityMapping(umsConvertorService.getESPFilterCityMappingEntityFromSRO(sro));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("request contained null ESPFilterCityMappingSRO");
        }

        return response;
    }

    @Deprecated
    @Override
    public GetFiltersForCityResponse getFiltersForCity(GetFiltersForCityRequest request) {
        GetFiltersForCityResponse response = new GetFiltersForCityResponse();
        int cityId = request.getCityId();
        int espId = request.getEspId();
        if ((cityId > 0) && (espId > 0)) {
            List<ESPFilterCityMapping> mappings = bulkEmailServiceInternal.getFiltersForCity(cityId, espId);
            List<ESPFilterCityMappingSRO> responseSROs = new ArrayList<ESPFilterCityMappingSRO>();
            for (ESPFilterCityMapping mapping : mappings) {
                responseSROs.add(umsConvertorService.getESPFilterCityMappingSROFromEntity(mapping));
            }
            response.setGetFiltersForCity(responseSROs);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid cityid/espId cityId :" + cityId + " espId " + espId);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetProfileFieldsForESPResponse getProfileFieldsForESP(GetProfileFieldsForESPRequest request) {
        GetProfileFieldsForESPResponse response = new GetProfileFieldsForESPResponse();
        int espId = request.getEspId();

        if (espId > 0) {
            List<ESPProfileField> espProfileFields = bulkEmailServiceInternal.getProfileFieldsForESP(espId);
            List<ESPProfileFieldSRO> espProfileFieldSROs = new ArrayList<ESPProfileFieldSRO>();
            for (ESPProfileField field : espProfileFields)
                espProfileFieldSROs.add(umsConvertorService.getESPProfileFieldSROFromEntity(field));
            response.setGetProfileFieldsForESP(espProfileFieldSROs);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid EspId EspId:" + espId);
        }
        return response;
    }

    @Deprecated
    @Override
    public GetAllEmailBulkEspCityMappingResponse getAllEmailBulkEspCityMapping(GetAllEmailBulkEspCityMappingRequest request) {
        GetAllEmailBulkEspCityMappingResponse response = new GetAllEmailBulkEspCityMappingResponse();
        List<EmailBulkEspCityMapping> mappings = bulkEmailServiceInternal.getAllEmailBulkEspCityMapping();
        List<EmailBulkEspCityMappingSRO> mappingSROs = new ArrayList<EmailBulkEspCityMappingSRO>();
        for (EmailBulkEspCityMapping mapping : mappings)
            mappingSROs.add(umsConvertorService.getEmailBulkEspCityMappingSROFromEntity(mapping));
        response.setGetAllEmailBulkEspCityMapping(mappingSROs);
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public GetBulkEspCityMappingForCityResponse getBulkEspCityMappingForCity(GetBulkEspCityMappingForCityRequest request) {
        GetBulkEspCityMappingForCityResponse response = new GetBulkEspCityMappingForCityResponse();
        Integer citySRO = request.getCity();
        if (citySRO != null) {
            EmailBulkEspCityMapping mapping = bulkEmailServiceInternal.getBulkEspCityMappingForCity(citySRO);
            EmailBulkEspCityMappingSRO sro = umsConvertorService.getEmailBulkEspCityMappingSROFromEntity(mapping);
            response.setGetBulkEspCityMappingForCity(sro);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("Null CitySRO in request");
        }
        return response;
    }

    @Deprecated
    @Override
    public GetResultsMauResponse getResultsMau(GetResultsMauRequest request) {
        GetResultsMauResponse response = new GetResultsMauResponse();
        String city = request.getCity();
        int number = request.getNumber();
        int start = request.getStart();
        
        if(StringUtils.isNotEmpty(city)){
          List<Object[]> mauResults =  bulkEmailServiceInternal.getResultsMau(start, number, city);
          response.setGetResultsMau(mauResults);
          response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("Invalid cityID "+city);
        }
        
        return response;
    }

    @Deprecated
    @Override
    public GetResultsBounceResponse getResultsBounce(GetResultsBounceRequest request) {
        GetResultsBounceResponse response = new GetResultsBounceResponse();
        String city = request.getCity();
        int number = request.getNumber();
        int start = request.getStart();
        if(StringUtils.isNotEmpty(city)){
            List<Object> bouncedResults  = bulkEmailServiceInternal.getResultsBounce(start, number, city);
            response.setGetResultsBounce(bouncedResults);
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("Invalid cityID "+city);
        }
        
        return response;
    }

}
