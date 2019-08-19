package com.snapdeal.ims.service;

import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.GetAccessTokenRequest;
import com.snapdeal.ims.request.GetAuthCodeRequest;
import com.snapdeal.ims.request.GetOAuthTokenDetailsRequest;
import com.snapdeal.ims.response.GetAccessTokenResponse;
import com.snapdeal.ims.response.GetAuthCodeResponse;
import com.snapdeal.ims.response.GetOAuthTokenDetailsResponse;

public interface IOAuthService {

	public GetAuthCodeResponse generateAuthCode(GetAuthCodeRequest getAuthCodeRequest)
			throws IMSServiceException, ValidationException;

	public GetAccessTokenResponse generateAccessToken(GetAccessTokenRequest getAccessTokenRequest)
			throws IMSServiceException, ValidationException;

	public GetOAuthTokenDetailsResponse getTokenDetails(GetOAuthTokenDetailsRequest getTokenDetailsrequest)
			throws IMSServiceException, ValidationException;
}
