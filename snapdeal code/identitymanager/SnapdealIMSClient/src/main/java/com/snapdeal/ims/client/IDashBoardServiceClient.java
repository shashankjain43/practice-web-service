package com.snapdeal.ims.client;

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

public interface IDashBoardServiceClient {

	public BlacklistEmailResponse getBlacklistEmails(BlackListRequest request)
			throws HttpTransportException, ServiceException;

	public GetDiscrepencyCountResponse getSDFCIdDiscrepencyCountForUsers(
			GetDiscrepencyCountRequest request) throws ServiceException,
			HttpTransportException;

	public DiscrepencyCasesEmailResponse getAllEmailForSDFCIdDiscrepenceycases(
			GetEmailForDiscrepencyCasesRequest request)
			throws ServiceException, HttpTransportException;

	public GtokenSizeResponse gTokenSizeWithEmailId(GtokenSizeRequest request)
			throws ServiceException, HttpTransportException;

	public GetUserOtpDetailsResponse getUserOtpDetails(
			GetUserOtpDetailsRequest request) throws ServiceException,
			HttpTransportException;

	public UserSearchResponse UserSearch(UserSearchRequest request)
			throws HttpTransportException, ServiceException;

	public WalletCountResponse getWalletCountBasedOnFilter(
			WalletFilterRequest request) throws HttpTransportException,
			ServiceException;

	public FailedEmailResponse retrieveFailedEmailBasedOnFilter(
			WalletFilterRequest request) throws HttpTransportException,
			ServiceException;
	
	public UserStatusResponse getStatus(UserStatusRequest request) 
			throws HttpTransportException, ServiceException;

	public GetUserHistoryDetailsResponse getUserHistoryDetails(GetUserHistoryDetailsRequest request)
			throws ServiceException, HttpTransportException ;

}
