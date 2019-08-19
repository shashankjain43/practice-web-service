package com.snapdeal.ims.token.service.impl;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.ITokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityDataServiceImpl implements IActivityDataService {

	@Autowired
	AuthorizationContext context;

	@Autowired
	ITokenService tokenService;

	@Override
	public void validateToken(String token) {
      validateToken(token, false);
	}
	
	@Override
	public void setActivityDataByUserId(String userId) {
		context.set(IMSRequestHeaders.USER_ID.toString(), userId);
	}

	@Override
	public void setActivityDataByToken(String token) {
		String userId=tokenService.getUserIdByToken(token);
		setActivityDataByUserId(userId);
	}

	@Override
	public void setActivityDataByEmailId(String emailId) {
		context.set(IMSRequestHeaders.EMAIL_ID.toString(), emailId);		
	}

   @Override
   public void validateToken(String token, boolean isLinkUpgradeFlow) {

      TokenRequest tokenRequest = new TokenRequest();
      tokenRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
      tokenRequest.setToken(token);
      tokenRequest.setUserAgent(context.get(IMSRequestHeaders.USER_AGENT.toString()));
      tokenRequest.setUserMachineIdentifier(context.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER
               .toString()));
      tokenRequest.setLinkUpgradeFlow(isLinkUpgradeFlow);
      tokenService.isTokenValid(tokenRequest);

   }

}
