package com.snapdeal.ims.client.impl;

import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.client.IUserMigrationServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserWithLinkedStateRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserWithLinkedStateResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;

public class UserMigrationServiceClientImpl extends AbstractClientImpl implements IUserMigrationServiceClient {

	@Override
	public UserUpgradationResponse userUpgradeStatus(UserUpgradeByEmailRequest request)
			throws ServiceException, HttpTransportException {
		return prepareResponse(request, UserUpgradationResponse.class, HttpMethod.POST,
				RestURIConstants.IS_USER_UPGRADE);
	}

	@Override
	public UpgradeUserResponse upgradeUser(UserUpgradeRequest request) throws ServiceException, HttpTransportException {

		return prepareResponse(request, UpgradeUserResponse.class, HttpMethod.POST, RestURIConstants.UPGRADE_USER);
	}

	@Override
	public VerifyUpgradeUserResponse verifyUpgradeUser(VerifyUserUpgradeRequest request)
			throws ServiceException, HttpTransportException {
		return prepareResponse(request, VerifyUpgradeUserResponse.class, HttpMethod.POST,
				RestURIConstants.VERIFY_UPGRADE_USER);
	}

	@Override
	public VerifyUserWithLinkedStateResponse verifyUserWithLinkedState(VerifyUserWithLinkedStateRequest request)
			throws ServiceException, HttpTransportException {
		return prepareResponse(request, VerifyUserWithLinkedStateResponse.class, HttpMethod.POST, RestURIConstants.VERIFY_UPGRADE_LINKED_USER);
	}
}
