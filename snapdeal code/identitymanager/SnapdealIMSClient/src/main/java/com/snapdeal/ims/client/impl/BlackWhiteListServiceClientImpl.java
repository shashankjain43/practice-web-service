package com.snapdeal.ims.client.impl;

import org.apache.commons.codec.binary.Base64;
import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.client.IBlackWhiteListServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.WhitelistEmailRequest;
import com.snapdeal.ims.response.BlacklistEntityResponse;
import com.snapdeal.ims.response.WhitelistEmailResponse;

public class BlackWhiteListServiceClientImpl extends AbstractClientImpl
		implements IBlackWhiteListServiceClient {

	@Override
	public WhitelistEmailResponse whitelistEmail(WhitelistEmailRequest request)
			throws ServiceException, HttpTransportException {

		return prepareResponse(request, WhitelistEmailResponse.class,
				HttpMethod.POST, RestURIConstants.USERS
						+ RestURIConstants.WHITELIST_EMAIL);
	}

	@Override
	public BlacklistEntityResponse addBlacklistEntity(
			BlacklistEntityRequest request) throws ServiceException,
			HttpTransportException {

		return prepareResponse(request, BlacklistEntityResponse.class,
				HttpMethod.POST, RestURIConstants.USERS
						+ RestURIConstants.BLACKLIST_ENTITY);
	}

	@Override
	public BlacklistEntityResponse removeBlacklistEntity(
			BlacklistEntityRequest request) throws ServiceException,
			HttpTransportException {
		return prepareResponse(
				request,
				BlacklistEntityResponse.class,
				HttpMethod.DELETE,
				RestURIConstants.USERS
						+ RestURIConstants.BLACKLIST_ENTITY
						+ "/"
						+ request.getBlackListType()
						+ "/"
						+ new String(Base64.encodeBase64String(request
								.getEntity().getBytes())));
	}
}
