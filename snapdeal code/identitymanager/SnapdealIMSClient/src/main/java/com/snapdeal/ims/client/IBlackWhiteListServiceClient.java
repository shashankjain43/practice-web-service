package com.snapdeal.ims.client;

import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.WhitelistEmailRequest;
import com.snapdeal.ims.response.BlacklistEntityResponse;
import com.snapdeal.ims.response.WhitelistEmailResponse;

public interface IBlackWhiteListServiceClient {

	/**
	 * It is an API used to WhiteList Email, in response it will return status
	 */
	public WhitelistEmailResponse whitelistEmail(WhitelistEmailRequest request)
			throws ServiceException, HttpTransportException;

	/**
	 * It is an API for blacklisting a new entity.
	 */
	public BlacklistEntityResponse addBlacklistEntity(
			BlacklistEntityRequest request) throws ServiceException,
			HttpTransportException;

	/**
	 * It is an API for to delete a blacklisted entity.
	 */
	public BlacklistEntityResponse removeBlacklistEntity(
			BlacklistEntityRequest request) throws ServiceException,
			HttpTransportException;
}
