package com.snapdeal.opspanel.promotion.service;

import java.util.List;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.model.FileSpecificTemplateRequest;
import com.snapdeal.opspanel.promotion.model.TemplateResponseModel;
import com.snapdeal.opspanel.promotion.model.TemplatesEntity;

public interface TemplateService {


	//function to get list of all templates
	public List<TemplatesEntity> getAllTemplatesData(String merchantName)throws OpsPanelException;

	public String addTemplatesData(FileSpecificTemplateRequest fileSpecificTemplateRequest) throws OpsPanelException;
	
	public TemplateResponseModel getTemplatesData(String templateId) throws OpsPanelException;
	
	
	
}
