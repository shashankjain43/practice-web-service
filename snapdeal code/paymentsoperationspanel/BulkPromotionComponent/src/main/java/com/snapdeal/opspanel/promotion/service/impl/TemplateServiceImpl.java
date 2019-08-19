package com.snapdeal.opspanel.promotion.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.dao.TemplateDao;
import com.snapdeal.opspanel.promotion.model.FileSpecificTemplateEntity;
import com.snapdeal.opspanel.promotion.model.FileSpecificTemplateRequest;
import com.snapdeal.opspanel.promotion.model.TemplateResponseModel;
import com.snapdeal.opspanel.promotion.model.TemplatesEntity;
import com.snapdeal.opspanel.promotion.service.TemplateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("templateService")
public class TemplateServiceImpl implements TemplateService
{
	
	@Autowired
	TemplateDao templateDao;


	@Override
	public List<TemplatesEntity> getAllTemplatesData(String merchantName) throws OpsPanelException {


		List<TemplatesEntity> templatesResponses=new ArrayList<TemplatesEntity>();


		try {
			templatesResponses=templateDao.getAllTemplatesList(merchantName);

		} catch (Exception e) {
		   log.info( "Exception in adding template " + e);

			throw new OpsPanelException("MT-1108","Error in adding template");
		}

		return templatesResponses;
		
	}

	@Override
	public String addTemplatesData(FileSpecificTemplateRequest fileSpecificTemplateRequest)
			throws OpsPanelException {

        String templateUUId=UUID.randomUUID().toString();
		
		FileSpecificTemplateEntity fileSpecificTemplateEntity=new FileSpecificTemplateEntity();

		fileSpecificTemplateEntity.setTemplateId(templateUUId);
		
		fileSpecificTemplateEntity.setUserId(fileSpecificTemplateRequest.getUserId());
		
		fileSpecificTemplateEntity.setTemplateName(fileSpecificTemplateRequest.getTemplateName());
		
		
		Gson gson=new Gson();
		
		String templateParamsName=gson.toJson(fileSpecificTemplateRequest.getTemplateParamsName());	
		
		templateParamsName=templateParamsName.replace("\\", "\\\\");
		
		String templateParamsValue=gson.toJson(fileSpecificTemplateRequest.getTemplateParamsValue());
		
		templateParamsValue=templateParamsValue.replace("\\", "\\\\");
		
		fileSpecificTemplateEntity.setTemplateParamsName(templateParamsName);
		fileSpecificTemplateEntity.setTemplateParamsValue(templateParamsValue);
		
		templateDao.addTemplate(fileSpecificTemplateEntity);

		return templateUUId;

	}
	

	@Override
	public TemplateResponseModel getTemplatesData(String templateId) throws OpsPanelException {
		
		TemplateResponseModel templateResponseModel=new TemplateResponseModel();
		
		try {
			
			FileSpecificTemplateEntity fileSpecificTemplateEntity=templateDao.getTemplateDetails(templateId);
			
			templateResponseModel.setTemplateName(fileSpecificTemplateEntity.getTemplateName());
			
			Gson gson=new Gson();
			
		    ArrayList<String> paramsName,paramsValues ;

			
			paramsName=(ArrayList<String>)gson.fromJson(fileSpecificTemplateEntity.getTemplateParamsName(),
					  new TypeToken<ArrayList<String>>() {}.getType());
			
			paramsValues=(ArrayList<String>)gson.fromJson(fileSpecificTemplateEntity.getTemplateParamsValue(),
					  new TypeToken<ArrayList<String>>() {}.getType());
			
			templateResponseModel.setTemplateParamsName(paramsName);
			
			templateResponseModel.setTemplateParamsValue(paramsValues);
			
			TemplatesEntity templatesEntity=templateDao.getTemplateData(fileSpecificTemplateEntity.getTemplateName());
			
			templateResponseModel.setTemplateUrl(templatesEntity.getTemplateUrl());
			templateResponseModel.setTemplateData(templatesEntity.getMessageString());
			
			
		} catch (Exception e) {
			log.info( "Exception in fetching template datails " + e);
			throw new OpsPanelException("MT-11010","Error in fetching template details");
		}
		
		
		return templateResponseModel;
	}

}
