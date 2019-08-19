package com.snapdeal.opspanel.rms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.GetUserByTokenRequest;
import com.snapdeal.payments.roleManagementModel.response.GetUserByTokenResponse;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service("tokenService")
@Slf4j
public class TokenServiceImpl implements TokenService {

	@Autowired
	RoleMgmtClient rmsClient;

	@Override
	public String getEmailFromToken(String token) throws OpsPanelException{

		if(token == null){
			throw new OpsPanelException("MT-7262","Token is null");
		}
		
		GetUserByTokenRequest getUserByTokenRequest = new GetUserByTokenRequest();
		getUserByTokenRequest.setRequestToken(token);

		GetUserByTokenResponse getUserByTokenResponse = null;

		String message = null;
		boolean retry = true;
		int retryCount = 0;
		
		while ((retry) && (retryCount < 2)) {
			try {
				getUserByTokenResponse = rmsClient.getUserByToken(getUserByTokenRequest);
				retry = false;
			} catch ( Exception e) {
				log.info( "Exception occurred while getting email by token from RMS " + ExceptionUtils.getFullStackTrace( e ) );

				message = e.getMessage();
				retryCount++;
			}
		}

		if(getUserByTokenResponse == null || getUserByTokenResponse.getUser() == null) {
			throw new OpsPanelException("MT-7263",message);
		}
		
		return getUserByTokenResponse.getUser().getEmail();
	}
}
