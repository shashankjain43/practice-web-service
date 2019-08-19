package com.snapdeal.ims.client.impl;

import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.client.IOAuthServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.GetAccessTokenRequest;
import com.snapdeal.ims.request.GetAuthCodeRequest;
import com.snapdeal.ims.request.GetOAuthTokenDetailsRequest;
import com.snapdeal.ims.response.GetAccessTokenResponse;
import com.snapdeal.ims.response.GetAuthCodeResponse;
import com.snapdeal.ims.response.GetOAuthTokenDetailsResponse;

public class OAuthServiceClientImpl extends AbstractClientImpl implements IOAuthServiceClient {

	@Override
	public GetAuthCodeResponse getAuthCodeForMerchant(GetAuthCodeRequest getAuthCodeRequest)
			throws ServiceException, HttpTransportException {
		return prepareResponse(getAuthCodeRequest, GetAuthCodeResponse.class, HttpMethod.POST,
				RestURIConstants.GET_AUTH_CODE);
	}

	@Override
	public GetAccessTokenResponse getAccessTokenForAuthCode(GetAccessTokenRequest getAccessTokenRequest)
			throws ServiceException, HttpTransportException {
		return prepareResponse(getAccessTokenRequest, GetAccessTokenResponse.class, HttpMethod.POST,
				RestURIConstants.GET_ACCESS_TOKEN);
	}

	@Override
	public GetOAuthTokenDetailsResponse getTokenDetails(GetOAuthTokenDetailsRequest getTokenDetailsRequest)
			throws ServiceException, HttpTransportException {
		return prepareResponse(getTokenDetailsRequest, GetOAuthTokenDetailsResponse.class, HttpMethod.POST,
				RestURIConstants.GET_OAUTH_TOKEN_DETAILS);
	}

}
