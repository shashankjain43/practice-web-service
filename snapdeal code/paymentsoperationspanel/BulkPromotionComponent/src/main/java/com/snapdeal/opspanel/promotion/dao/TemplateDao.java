package com.snapdeal.opspanel.promotion.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.snapdeal.opspanel.promotion.model.FileSpecificTemplateEntity;
import com.snapdeal.opspanel.promotion.model.TemplatesEntity;


public interface TemplateDao {

   public ArrayList<TemplatesEntity> getAllTemplatesList(@Param("merchantId") String merchantId);	
	
	public void addTemplate(FileSpecificTemplateEntity templatesEntity);

	public FileSpecificTemplateEntity getTemplateDetails(String templatedId);
	
	public TemplatesEntity getTemplateData(String templateName);
	
	
}
