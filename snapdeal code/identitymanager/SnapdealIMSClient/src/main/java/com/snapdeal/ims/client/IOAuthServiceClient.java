package com.snapdeal.ims.client;

import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.GetAccessTokenRequest;
import com.snapdeal.ims.request.GetAuthCodeRequest;
import com.snapdeal.ims.request.GetOAuthTokenDetailsRequest;
import com.snapdeal.ims.response.GetAccessTokenResponse;
import com.snapdeal.ims.response.GetAuthCodeResponse;
import com.snapdeal.ims.response.GetOAuthTokenDetailsResponse;

public interface IOAuthServiceClient {

	public GetAuthCodeResponse getAuthCodeForMerchant(GetAuthCodeRequest getAuthCodeRequest)
			throws ServiceException, HttpTransportException;

	public GetAccessTokenResponse getAccessTokenForAuthCode(GetAccessTokenRequest getAccessTokenRequest)
			throws ServiceException, HttpTransportException;

	public GetOAuthTokenDetailsResponse getTokenDetails(GetOAuthTokenDetailsRequest getTokenDetailsRequest)
			throws ServiceException, HttpTransportException;;

}
