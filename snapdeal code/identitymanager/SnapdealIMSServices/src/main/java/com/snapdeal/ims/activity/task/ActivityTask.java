package com.snapdeal.ims.activity.task;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.client.dao.IActivityDetailsDao;
import com.snapdeal.ims.client.dbmapper.entity.Activity;
import com.snapdeal.ims.client.dbmapper.entity.info.ActivityStatus;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.response.AbstractResponse;

@Component
@Slf4j
public class ActivityTask {
	@Autowired
	AuthorizationContext context;

	@Autowired
	IActivityDetailsDao activityDao;
	
	@Autowired
	@Qualifier("activityTaskExecutor")
	private TaskExecutor taskExecutor;

	public void logActivity(Object request, Object response,
			String className, String methodName) {
		
		final Activity activity = new Activity();
		activity.setActivityType(className);
		activity.setActivitySubtype(methodName);
		activity.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		activity.setIpAddress(context.get(IMSRequestHeaders.CLIENT_IP_ADDRESS
				.toString()));
		activity.setMacAddress(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER
				.toString()));
		activity.setServerIpAddress(context.get(IMSRequestHeaders.SERVER_IP_ADDRESS.toString()));
		// setting status
		ActivityStatus status = ActivityStatus.FAILURE;
		if (response != null) {
			if (response instanceof AbstractResponse) {
				status = ActivityStatus.SUCCESS;
			}
		}
		if (context.get(IMSRequestHeaders.USER_ID.toString()) != null) {
			activity.setEntityId(context.get(IMSRequestHeaders.USER_ID
					.toString()));
		} else if (context.get(IMSRequestHeaders.EMAIL_ID.toString()) != null) {
			activity.setEntityId(context.get(IMSRequestHeaders.EMAIL_ID
					.toString()));
		} else if (context.get(IMSRequestHeaders.MOBILE_NUMBER.toString()) != null) {
			activity.setEntityId(context.get(IMSRequestHeaders.MOBILE_NUMBER
					.toString()));
		}
		activity.setStatus(status);
		try{
			
			taskExecutor.execute( new Runnable() {
	               public void run() {
	            	   activityDao.createActivity(activity);
	               }
	          });
			
		} catch(RuntimeException e){
		   //Log failures
		   log.error("Exception occured while submitting activity to executor: " 
				   + activity, e);   	
		}
		return;
	}
	
	private String getMachineIpAddress() {
		InetAddress ip;
		String hostIpAddress = null;

		try {
			ip = InetAddress.getLocalHost();
			hostIpAddress = ip.getHostAddress();
		} catch (UnknownHostException e) {
			log.error("error occured while fetching ip address of server",e);
		}
		return hostIpAddress;
	}
}
