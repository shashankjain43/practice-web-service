package com.snapdeal.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.dto.AccessTokenInformationDTO;
import com.snapdeal.ims.dto.AuthCodeInformationDTO;
import com.snapdeal.ims.dto.OAuthTokenInformationDTO;
import com.snapdeal.ims.enums.OAuthTokenTypes;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.GetAccessTokenRequest;
import com.snapdeal.ims.request.GetAuthCodeRequest;
import com.snapdeal.ims.request.GetOAuthTokenDetailsRequest;
import com.snapdeal.ims.response.GetAccessTokenResponse;
import com.snapdeal.ims.response.GetAuthCodeResponse;
import com.snapdeal.ims.response.GetOAuthTokenDetailsResponse;
import com.snapdeal.ims.service.IOAuthService;
import com.snapdeal.ims.token.request.AccessTokenRequest;
import com.snapdeal.ims.token.request.AuthCodeRequest;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.token.service.ITransferTokenService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;

@Service("oauthService")
@Slf4j
public class OAuthServiceImpl implements IOAuthService {

	@Autowired
	private IGlobalTokenService globalTokenService;

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private ITransferTokenService transferTokenService;

	@Override
	@Timed
	@Marked
	@Logged
	public GetAuthCodeResponse generateAuthCode(GetAuthCodeRequest getAuthCodeRequest)
			throws IMSServiceException, ValidationException {
		GetAuthCodeResponse getAuthCodeResponse = new GetAuthCodeResponse();
		AuthCodeRequest authCodeRequest = new AuthCodeRequest();
		authCodeRequest.setMerchantId(getAuthCodeRequest.getMerchantId());
		String userId = tokenService.getUserIdByToken(getAuthCodeRequest.getToken());
		authCodeRequest.setUserId(userId);
		AuthCodeInformationDTO authDetailsDTO = globalTokenService.createAuthCode(authCodeRequest);
		if (log.isInfoEnabled())
			log.info("Response from auth code service for auth code generation request{} is : {}", authCodeRequest,
					authDetailsDTO);
		if (null != authDetailsDTO) {
			getAuthCodeResponse.setAuthCode(authDetailsDTO.getAuthCode());
		}
		return getAuthCodeResponse;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetAccessTokenResponse generateAccessToken(GetAccessTokenRequest getAccessTokenRequest)
			throws IMSServiceException, ValidationException {
		GetAccessTokenResponse accessTokenResponse = new GetAccessTokenResponse();		
		AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
		accessTokenRequest.setAuthCode(getAccessTokenRequest.getAuthCode());
		accessTokenRequest.setMerchantId(getAccessTokenRequest.getMerchantId());
		AccessTokenInformationDTO accessTokenDTO = tokenService.getAccessTokenFromAuthCode(accessTokenRequest);
		if (log.isInfoEnabled())
			log.info("Response from access token service for auth code generation request{} is : {}",
					accessTokenRequest, accessTokenDTO);
		if (null != accessTokenDTO) {
			accessTokenResponse.setAccessToken(accessTokenDTO.getAccessToken());
			accessTokenResponse.setAccessTokenExpiry(accessTokenDTO.getAccessTokenExpiry());
			accessTokenResponse.setRefreshToken(accessTokenDTO.getGlobalToken());
			accessTokenResponse.setRefreshTOkenExpiry(accessTokenDTO.getGlobalTokenExpiry());
		}
		return accessTokenResponse;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetOAuthTokenDetailsResponse getTokenDetails(GetOAuthTokenDetailsRequest getTokenDetailsrequest)
			throws IMSServiceException, ValidationException {
		GetOAuthTokenDetailsResponse getOauthTokenDetails = new GetOAuthTokenDetailsResponse();
		OAuthTokenTypes tokenType = getTokenDetailsrequest.getTokenType();
		OAuthTokenInformationDTO oauthTokenInfo = null;

		if (log.isInfoEnabled())
			log.info("getting oauth code details for token type {} with request {} ", tokenType,
					getTokenDetailsrequest);

		switch (tokenType) {
		case AUTH_CODE:
			oauthTokenInfo = transferTokenService.getOAuthTokenInfo(getTokenDetailsrequest.getToken());
			break;
		case ACCESS_TOKEN:
			oauthTokenInfo = tokenService.getOAuthTokenInfo(getTokenDetailsrequest.getToken());
			break;
		case REFRESH_TOKEN:
			oauthTokenInfo = globalTokenService.getOAuthTokenInfo(getTokenDetailsrequest.getToken());
			break;
		default:
			log.error("Invalid token type for Getting Oauth token details which is : {}", tokenType);
			throw new InternalServerException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}

		if (log.isInfoEnabled())
			log.info("response from service for oauth token details for token type {} is : {}", tokenType,
					oauthTokenInfo);

		if (null != oauthTokenInfo) {
			getOauthTokenDetails.setMerchantId(oauthTokenInfo.getMerchantId());
			getOauthTokenDetails.setUserId(oauthTokenInfo.getUserId());
		}

		return getOauthTokenDetails;
	}

}
