package com.snapdeal.opspanel.promotion.service.impl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.snapdeal.opspanel.promotion.dao.TemplateDao;
import com.snapdeal.opspanel.promotion.model.FileSpecificTemplateEntity;
import com.snapdeal.opspanel.promotion.model.NotificationRequestModel;
import com.snapdeal.opspanel.promotion.service.NotificationService;
import com.snapdeal.payments.notification.api.Notifier;
import com.snapdeal.payments.notification.model.NotifierResponse;
import com.snapdeal.payments.notification.model.NotifyRequest;
import com.snapdeal.payments.notification.utility.ChannelType;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService {


	@Autowired
	private Notifier notifier;

	@Autowired
	private TemplateDao templatedao;

	private String testMobile="7503359825";

	@Value("${snapdeal.payments.sdmoney.email.test}")
	private boolean test;

	@Override
	public NotifierResponse sendNotification(NotificationRequestModel notificationRequestModel)  throws Exception {

		FileSpecificTemplateEntity fileTemplateEntity= templatedao.getTemplateDetails(notificationRequestModel.getTemplateId());


		NotifyRequest notifyRequest=new NotifyRequest();
		notifyRequest.setChannelType(notificationRequestModel.getChannelType());
		notifyRequest.setUserId(notificationRequestModel.getUserId());

		notifyRequest.setTemplateName(fileTemplateEntity.getTemplateName());


		Map<String,Object> replaceParams=notificationRequestModel.getTemplateParams();

		notifyRequest.setTemplateParams(notificationRequestModel.getTemplateParams());		

		Gson gson=new Gson(); 		 

		ArrayList<String> paramsName,paramsValues ;

		paramsName=(ArrayList<String>)gson.fromJson(fileTemplateEntity.getTemplateParamsName(),
				new TypeToken<ArrayList<String>>() {}.getType());

		paramsValues=(ArrayList<String>)gson.fromJson(fileTemplateEntity.getTemplateParamsValue(),
				new TypeToken<ArrayList<String>>() {}.getType());

		for(int i=0;i<paramsName.size();i++){

			replaceParams.put(paramsName.get(i),paramsValues.get(i));
		}


		if(notifyRequest.getChannelType().equals(ChannelType.SMS) && test){

			notifyRequest.setUserId(testMobile);
		}

		NotifierResponse response =notifier.sendNotification(notifyRequest);		 	

		return response;
	}

}
