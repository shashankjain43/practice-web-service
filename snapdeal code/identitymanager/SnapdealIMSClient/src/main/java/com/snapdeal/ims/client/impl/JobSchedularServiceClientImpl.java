package com.snapdeal.ims.client.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.client.IJobSchedularServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.UpgradeUserByEmailRequest;
import com.snapdeal.ims.response.UpgradeUserByEmailResponse;

public class JobSchedularServiceClientImpl extends AbstractClientImpl implements IJobSchedularServiceClient {

	@Override
	public UpgradeUserByEmailResponse upgradeUser(UpgradeUserByEmailRequest request)
			throws ServiceException, HttpTransportException {
		if(request.getEmailId()==null)
		{
				throw new ServiceException(
						IMSRequestExceptionCodes.BLANK_PARAMS.errMsg(),
						IMSRequestExceptionCodes.BLANK_PARAMS.errCode());
		}
		request.setEmailId(Base64.encodeBase64String(request.getEmailId().getBytes()));
	      return prepareResponse(request,
                  UpgradeUserByEmailResponse.class, 
                  HttpMethod.POST,
                  RestURIConstants.UPGRADE_USERS_EMAIL);
}
	}

	
