/**
 * 
 */
package com.snapdeal.ims.client.impl;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.commons.codec.binary.Base64;
import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.client.IDashBoardServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.BlackListRequest;
import com.snapdeal.ims.request.GetDiscrepencyCountRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesRequest;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;
import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
import com.snapdeal.ims.request.GtokenSizeRequest;
import com.snapdeal.ims.request.UserSearchRequest;
import com.snapdeal.ims.request.UserStatusRequest;
import com.snapdeal.ims.request.WalletFilterRequest;
import com.snapdeal.ims.response.BlacklistEmailResponse;
import com.snapdeal.ims.response.DiscrepencyCasesEmailResponse;
import com.snapdeal.ims.response.FailedEmailResponse;
import com.snapdeal.ims.response.GetDiscrepencyCountResponse;
import com.snapdeal.ims.response.GetUserHistoryDetailsResponse;
import com.snapdeal.ims.response.GetUserOtpDetailsResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.response.UserSearchResponse;
import com.snapdeal.ims.response.UserStatusResponse;
import com.snapdeal.ims.response.WalletCountResponse;

/**
 * @author kishan
 *
 */
public class DashboardClientServiceImpl extends AbstractClientImpl implements
IDashBoardServiceClient {

	@Override
	public BlacklistEmailResponse getBlacklistEmails(BlackListRequest request)
			throws HttpTransportException, ServiceException {

		return prepareResponse(request, BlacklistEmailResponse.class,
				HttpMethod.GET, RestURIConstants.GET_BLACKLIST);
	}

	@Override
	public GetDiscrepencyCountResponse getSDFCIdDiscrepencyCountForUsers(
			GetDiscrepencyCountRequest request) throws ServiceException,
	HttpTransportException {
		if(request.getFromDate()==null||request.getToDate()==null)
		{
			throw new ServiceException(
					IMSRequestExceptionCodes.BLANK_PARAMS.errMsg(),
					IMSRequestExceptionCodes.BLANK_PARAMS.errCode());
		}
		request.setFromDate(Base64.encodeBase64String(request.getFromDate().getBytes()));
		request.setToDate(Base64.encodeBase64String(request.getToDate().getBytes()));
		return prepareResponse(request, GetDiscrepencyCountResponse.class,
				HttpMethod.GET, RestURIConstants.GET_DISCREPENCY_COUNT);

	}

	@Override
	public DiscrepencyCasesEmailResponse getAllEmailForSDFCIdDiscrepenceycases(
			GetEmailForDiscrepencyCasesRequest request)
					throws ServiceException, HttpTransportException {
		if(request.getDCase()==null||request.getFromDate()==null||request.getToDate()==null)
		{
			throw new ServiceException(
					IMSRequestExceptionCodes.BLANK_PARAMS.errMsg(),
					IMSRequestExceptionCodes.BLANK_PARAMS.errCode());
		}
		request.setFromDate(Base64.encodeBase64String(request.getFromDate().getBytes()));
		request.setToDate(Base64.encodeBase64String(request.getToDate().getBytes()));
		request.setDCase(Base64.encodeBase64String(request.getDCase().getBytes()));
		return prepareResponse(request, DiscrepencyCasesEmailResponse.class,
				HttpMethod.GET, RestURIConstants.GET_DISCREPENCY_LIST);

	}

	@Override
	public GtokenSizeResponse gTokenSizeWithEmailId(GtokenSizeRequest request)
			throws ServiceException, HttpTransportException {
		if (StringUtils.isBlank(request.getEmailId())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errCode());
		} 
		return prepareResponse(request, GtokenSizeResponse.class,
				HttpMethod.GET, RestURIConstants.GET_TOKEN_SIZE.replace(
						"{email}", Base64.encodeBase64String(request
								.getEmailId().getBytes())));
	}

	@Override
	public GetUserOtpDetailsResponse getUserOtpDetails(
			GetUserOtpDetailsRequest request) throws ServiceException,
	HttpTransportException {
		if(request.getValue()==null||request.getSearchField()==null)
		{
				throw new ServiceException(
						IMSRequestExceptionCodes.BLANK_PARAMS.errMsg(),
						IMSRequestExceptionCodes.BLANK_PARAMS.errCode());
		}
		request.setValue(Base64.encodeBase64String(request.getValue().getBytes()));
		return prepareResponse(request,
				GetUserOtpDetailsResponse.class,
				HttpMethod.GET,
				RestURIConstants.GET_OTP_DETAILS);
	}

	@Override
	public UserSearchResponse UserSearch(UserSearchRequest request)
			throws HttpTransportException, ServiceException {
		if(request.getUserId()!=null){
			request.setUserId(Base64.encodeBase64String(request.getUserId().getBytes()));
		}
		if(request.getName()!=null){
			request.setName(Base64.encodeBase64String(request.getName().getBytes()));
		}
		if(request.getEmail()!=null){
			request.setEmail(Base64.encodeBase64String(request.getEmail().getBytes()));
		}
		if(request.getMobile()!=null){
			request.setMobile(Base64.encodeBase64String(request.getMobile().getBytes()));
		}
		if(request.getFromDate()!=null){
			request.setFromDate(Base64.encodeBase64String(request.getFromDate().getBytes()));
		}
		if(request.getToDate()!=null){
			request.setToDate(Base64.encodeBase64String(request.getToDate().getBytes()));
		}
		return prepareResponse(request, UserSearchResponse.class,
				HttpMethod.GET, RestURIConstants.USER_SEARCH);
	}

	@Override
	public WalletCountResponse getWalletCountBasedOnFilter(
			WalletFilterRequest request) throws HttpTransportException,
	ServiceException {
		return prepareResponse(request, WalletCountResponse.class,
				HttpMethod.POST, RestURIConstants.GET_WALLET_COUNT);
	}

	@Override
	public FailedEmailResponse retrieveFailedEmailBasedOnFilter(
			WalletFilterRequest request) throws HttpTransportException,
	ServiceException {
		return prepareResponse(request, FailedEmailResponse.class,
				HttpMethod.POST,
				RestURIConstants.GET_FAILED_EMAIL_LIST);
	}

	@Override
	public UserStatusResponse getStatus(UserStatusRequest request) throws HttpTransportException, ServiceException {
		if (StringUtils.isBlank(request.getEmailId())) {
			throw new ServiceException(
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errMsg(),
					IMSRequestExceptionCodes.EMAIL_ID_IS_BLANK.errCode());
		} 
		return prepareResponse(request, UserStatusResponse.class, HttpMethod.GET,RestURIConstants.GET_STATUS.replace(
				"{emailId}", Base64.encodeBase64String(request
						.getEmailId().getBytes())));
	}

	@Override
	public GetUserHistoryDetailsResponse getUserHistoryDetails(GetUserHistoryDetailsRequest request)
			throws ServiceException, HttpTransportException {
		if(request.getUserId()==null)
		{
				throw new ServiceException(
						IMSRequestExceptionCodes.BLANK_PARAMS.errMsg(),
						IMSRequestExceptionCodes.BLANK_PARAMS.errCode());
		}
		request.setUserId(Base64.encodeBase64String(request.getUserId().getBytes()));
		return prepareResponse(request,
				GetUserHistoryDetailsResponse.class, 
				HttpMethod.GET,
				RestURIConstants.GET_USER_HISTORY);

}}
