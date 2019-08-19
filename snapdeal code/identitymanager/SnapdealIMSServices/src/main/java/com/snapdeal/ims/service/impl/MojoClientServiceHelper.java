package com.snapdeal.ims.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.mojoClient.request.MojoRequest;
import com.snapdeal.ims.response.UpdateMobileNumberResponse;
import com.snapdeal.ims.service.IMojoClientServiceHelper;
import com.snapdeal.ims.service.IMojoService;

@Component
public class MojoClientServiceHelper implements IMojoClientServiceHelper {

	@Autowired
	private IMojoService mojoClientService;
	
	@Override
	public void createMojoClient(UpdateMobileNumberResponse response){
		MojoRequest mojoRequest = new MojoRequest();
		mojoRequest.setUserId(response.getUserDetails().getUserId());
		mojoRequest.setMobileNumber(response.getUserDetails().getMobileNumber());
		mojoRequest.setTaskId(response.getUserDetails().getUserId()+response.getUserDetails().getMobileNumber()+new Date());
		mojoClientService.createMojoClientTask(mojoRequest);
	}
}
