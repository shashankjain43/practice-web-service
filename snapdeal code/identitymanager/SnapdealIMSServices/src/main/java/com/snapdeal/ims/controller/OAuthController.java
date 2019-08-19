package com.snapdeal.ims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.GetAccessTokenRequest;
import com.snapdeal.ims.request.GetAuthCodeRequest;
import com.snapdeal.ims.request.GetOAuthTokenDetailsRequest;
import com.snapdeal.ims.response.GetAccessTokenResponse;
import com.snapdeal.ims.response.GetAuthCodeResponse;
import com.snapdeal.ims.response.GetOAuthTokenDetailsResponse;
import com.snapdeal.ims.service.IOAuthService;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(RestURIConstants.BASE_URI)
public class OAuthController extends AbstractController {

	@Autowired
	IOAuthService oauthService;

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GET_AUTH_CODE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetAuthCodeResponse getAuthCodeForMerchant(@RequestBody @Valid GetAuthCodeRequest getAuthCodeRequest,
			BindingResult results, HttpServletRequest httpRequest) throws ValidationException {
		GetAuthCodeResponse authCodeResponse = null;
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while getting auth code for merchant with errorcode {} ", code);
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		authCodeResponse = oauthService.generateAuthCode(getAuthCodeRequest);
		return authCodeResponse;
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GET_ACCESS_TOKEN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetAccessTokenResponse getAccessToken(@RequestBody @Valid GetAccessTokenRequest getAccessTokenRequest,
			BindingResult results, HttpServletRequest httpRequest) throws ValidationException {
		GetAccessTokenResponse accessTokenResponse = null;
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while getting access token for merchant with errorcode {} ", code);
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		accessTokenResponse = oauthService.generateAccessToken(getAccessTokenRequest);
		return accessTokenResponse;
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GET_OAUTH_TOKEN_DETAILS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetOAuthTokenDetailsResponse getTokenDetails(
			@RequestBody @Valid GetOAuthTokenDetailsRequest getTokenDetailsRequest, BindingResult results,
			HttpServletRequest httpRequest) throws ValidationException {
		GetOAuthTokenDetailsResponse getTokenDetailsResponse = null;
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while getting token details with errorcode {} ", code);
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		getTokenDetailsResponse = oauthService.getTokenDetails(getTokenDetailsRequest);
		return getTokenDetailsResponse;
	}

}
