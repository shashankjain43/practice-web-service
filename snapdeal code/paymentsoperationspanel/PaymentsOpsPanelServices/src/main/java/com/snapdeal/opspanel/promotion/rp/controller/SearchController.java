package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.ims.entity.UserEntity;
import com.snapdeal.ims.enums.ConfigureUserBasedOn;
import com.snapdeal.ims.request.BlackListRequest;
import com.snapdeal.ims.request.GetDiscrepencyCountRequest;
import com.snapdeal.ims.request.GetEmailForDiscrepencyCasesRequest;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;
import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
import com.snapdeal.ims.request.GtokenSizeRequest;
// import com.snapdeal.ims.request.AbstractRequest;
// import com.snapdeal.ims.request.GetUserOtpDetailsRequest;
// import com.snapdeal.ims.request.GtokenSizeRequest;
import com.snapdeal.ims.request.UserSearchRequest;
import com.snapdeal.ims.request.UserStatusRequest;
import com.snapdeal.ims.request.WalletFilterRequest;
import com.snapdeal.ims.response.BlacklistEmailResponse;
import com.snapdeal.ims.response.DiscrepencyCasesEmailResponse;
import com.snapdeal.ims.response.GetDiscrepencyCountResponse;
import com.snapdeal.ims.response.GetUserHistoryDetailsResponse;
import com.snapdeal.ims.response.GetUserOtpDetailsResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
// import com.snapdeal.ims.request.UserStatusRequest;
// import com.snapdeal.ims.request.WalletFilterRequest;
// import com.snapdeal.ims.response.BlacklistEmailResponse;
// import com.snapdeal.ims.response.GetDiscrepencyCountResponse;
// import com.snapdeal.ims.response.GetUserOtpDetailsResponse;
// import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.response.UserSearchResponse;
import com.snapdeal.ims.response.UserStatusResponse;
import com.snapdeal.ims.response.WalletCountResponse;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
// import com.snapdeal.ims.response.UserStatusResponse;
// import com.snapdeal.ims.response.WalletCountResponse;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.SearchShopoTransactionRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTransactionRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTxnByIdFlowRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTxnByReferenceFlowRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTxnFlowRequest;
import com.snapdeal.opspanel.userpanel.request.SearchUserRequest;
import com.snapdeal.opspanel.userpanel.request.SearchWithdrawalFlowRequest;
import com.snapdeal.opspanel.userpanel.response.GenericResponse;
import com.snapdeal.opspanel.userpanel.response.SearchTxnResponse;
import com.snapdeal.opspanel.userpanel.response.SearchUserResponse;
import com.snapdeal.opspanel.userpanel.service.ActionService;
import com.snapdeal.opspanel.userpanel.service.IMSDashboardService;
//import com.snapdeal.opspanel.userpanel.service.IMSDashboardService;
import com.snapdeal.opspanel.userpanel.service.SearchUserServices;
import com.snapdeal.opspanel.userpanel.service.SearchUserServicesAgg;
import com.snapdeal.opspanel.userpanel.service.ShopoService;
import com.snapdeal.opspanel.userpanel.utils.GenericUtils;
import com.snapdeal.opspanel.webappcomponent.service.CSToolsService;
import com.snapdeal.opspanel.webappcomponent.service.CampaignService;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceDetailsRequest;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceDetailsResponse;

import lombok.extern.slf4j.Slf4j;

// import com.snapdeal.ims.request.GetDiscrepencyCountRequest;

@Controller
@RequestMapping("search")
@Slf4j
public class SearchController {

	@Autowired
	SearchUserServicesAgg userServicesAgg;

	@Autowired
	ActionService actionService;

	@Autowired
	SearchUserServices searchUserServices;

	@Autowired
	IMSDashboardService imsDashboardService;

	@Autowired
	ShopoService shopoService;

	@Autowired
	CSToolsService csToolsService;

	@Autowired
	CampaignService campaignService;


	@Audited(context = "Search", searchId = "request.name", skipRequestKeys = {
	"request.mobileNumber" }, skipResponseKeys = { "response.data.mobileNumber", "response.data.email" })
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/searchUser", method = RequestMethod.POST)
	public @ResponseBody GenericResponse searchUser(@RequestBody SearchUserRequest request) throws InfoPanelException {

		if (request.getEmail() != null && request.getEmail().trim().equals("")) {
			request.setEmail(null);
		}
		if (request.getMobileNumber() != null && request.getMobileNumber().trim().equals("")) {
			request.setMobileNumber(null);
		}
		if (request.getUserId() != null && request.getUserId().trim().equals("")) {
			request.setUserId(null);
		}

		SearchUserResponse response = new SearchUserResponse();

		response = userServicesAgg.searchUser(request);
		return GenericUtils.getGenericResponse(response);
	}

	@Audited(context = "Search", searchId = "request.userId", skipRequestKeys = {
	"request.mobile" }, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view') or hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/retrieveUser", method = RequestMethod.POST)
	public @ResponseBody GenericResponse retrieveUser(@RequestBody SearchUserRequest request)
			throws InfoPanelException {

		if (StringUtils.isNumeric(request.getSearchId())) {
			request.setMobileNumber(request.getSearchId());
		} else if (Pattern.compile(".+@.+\\.[a-z]+").matcher(request.getSearchId()).matches()) {
			request.setEmail(request.getSearchId());
		} else {
			request.setUserId(request.getSearchId());
		}

		List<SearchUserResponse> searchUserResponseList = new ArrayList<SearchUserResponse>();

		if (request.getFromDate() == null && request.getToDate() == null) {
			searchUserResponseList.add(userServicesAgg.searchUser(request));
			return GenericUtils.getGenericResponse(searchUserResponseList);
		}

		trimUserSearchRequest(request);

		UserSearchRequest userSearchRequest = new UserSearchRequest();
		userSearchRequest.setEmail(userSearchRequest.getEmail());
		userSearchRequest.setMobile(request.getMobileNumber());
		userSearchRequest.setUserId(userSearchRequest.getUserId());
		UserSearchResponse response = searchUserServices.userSearch(userSearchRequest);
		for (UserEntity userEntity : response.getUsers()) {
			SearchUserRequest searchUserRequest = new SearchUserRequest();
			searchUserRequest.setUserId(userEntity.getUser_id());
			searchUserResponseList.add(userServicesAgg.searchUser(searchUserRequest));
		}

		return GenericUtils.getGenericResponse(searchUserResponseList);
	}

	@Audited(context = "Search", searchId = "request.transactionStartDate", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/searchTransaction", method = RequestMethod.POST)
	public @ResponseBody GenericResponse searchTransaction(
			@RequestBody(required = false) SearchTransactionRequest request) throws InfoPanelException {
		trimSearchTransactionRequest(request);
		return GenericUtils.getGenericResponse(actionService.searchTransaction(request));
	}

	// NEW
	@Audited(context = "Search", searchId = "request.searchKey", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/searchTransactionForUser", method = RequestMethod.POST)
	public @ResponseBody GenericResponse searchTransactionForUser(
			@RequestBody(required = true) SearchTxnFlowRequest request) throws InfoPanelException {
		request.setSearchKey(request.getSearchKey().trim());
		return GenericUtils.getGenericResponse(searchUserServices.searchTxnFlow(request));
	}

	// NEW
	@Audited(context = "Search", searchId = "request.request.transactionId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/searchTransactionById", method = RequestMethod.POST)
	public @ResponseBody GenericResponse searchTransactionById(
			@RequestBody(required = true) SearchTxnByIdFlowRequest request) throws InfoPanelException {

		return GenericUtils.getGenericResponse(searchUserServices.searchTxnByIdFlow(request));
	}

	// NEW
	@Audited(context = "Search", searchId = "request.moneyOutStatusRequest.transactionRef", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/searchWithdrawal", method = RequestMethod.POST)
	public @ResponseBody GenericResponse searchWithdrawal(
			@RequestBody(required = true) SearchWithdrawalFlowRequest request) throws InfoPanelException {

		return GenericUtils.getGenericResponse(searchUserServices.searchWithdrawal(request));
	}

	// NEW
	@Audited(context = "Search", searchId = "request.transactionsByReferenceRequest.transactionReference", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/searchTransactionByReference", method = RequestMethod.POST)
	public @ResponseBody GenericResponse searchTransactionByReference(
			@RequestBody(required = true) SearchTxnByReferenceFlowRequest request) throws InfoPanelException {

		return GenericUtils.getGenericResponse(searchUserServices.searchTxnByReferenceFlow(request));
	}

	@Audited(context = "Search", searchId = "", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/blackListedUsers", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody GenericResponse getBlacklistEmails() throws InfoPanelException {
		BlackListRequest request = new BlackListRequest();
		BlacklistEmailResponse response = new BlacklistEmailResponse();
		response = imsDashboardService.getBlacklistEmails(request);
		return GenericUtils.getGenericResponse(response);
	}

	@Audited(context = "Search", searchId = "request.fromDate", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/discrepancyCountForUser", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody GenericResponse getDiscrepencyCountForUsers(@RequestBody GetDiscrepencyCountRequest request)
			throws InfoPanelException {
		GetDiscrepencyCountResponse response = new GetDiscrepencyCountResponse();
		response = imsDashboardService.getDiscrepencyCountForUsers(request);
		return GenericUtils.getGenericResponse(response);

	}

	@Audited(context = "Search", searchId = "request.fromDate", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/getAllEmailsDiscrepencey", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody GenericResponse getDiscrepencyCountForUsers(
			@RequestBody GetEmailForDiscrepencyCasesRequest request) throws InfoPanelException {
		DiscrepencyCasesEmailResponse response = new DiscrepencyCasesEmailResponse();
		response = imsDashboardService.getAllEmailsDiscrepenceycases(request);
		return GenericUtils.getGenericResponse(response);

	}

	@Audited(context = "Search", searchId = "emailId.emailId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/tokensForUser", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody GenericResponse getTokensForUser(@RequestBody GtokenSizeRequest emailId)
			throws InfoPanelException {
		GtokenSizeResponse response = new GtokenSizeResponse();
		response = imsDashboardService.getTokensForUser(emailId);
		return GenericUtils.getGenericResponse(response);
	}

	@Audited(context = "Search", searchId = "request.merchant", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/walletCountForMerchant", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody GenericResponse getWalletCount(@RequestBody WalletFilterRequest request)
			throws InfoPanelException {
		WalletCountResponse response = new WalletCountResponse();
		response = imsDashboardService.getWalletCount(request);
		return GenericUtils.getGenericResponse(response);
	}

	@Audited(context = "Search", searchId = "request.value", skipRequestKeys = {}, skipResponseKeys = {
	"response.data.userOtpDetails" }, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/userOTPDetails", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody GenericResponse getUserOtpDetails(@RequestBody GetUserOtpDetailsRequest request)
			throws InfoPanelException {
		GetUserOtpDetailsResponse response = new GetUserOtpDetailsResponse();
		response = imsDashboardService.getUserOtpDetails(request);
		return GenericUtils.getGenericResponse(response);
	}

	@Audited(context = "Search", searchId = "request.emailId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/getStatus", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody GenericResponse getStatus(@RequestBody UserStatusRequest request) throws InfoPanelException {
		UserStatusResponse response = new UserStatusResponse();
		response = imsDashboardService.getStatus(request);
		return GenericUtils.getGenericResponse(response);
	}

	@Audited(context = "Search", searchId = "request.userId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/userHistory", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody GenericResponse getStatus(@RequestBody GetUserHistoryDetailsRequest request)
			throws InfoPanelException {
		GetUserHistoryDetailsResponse response = new GetUserHistoryDetailsResponse();
		response = imsDashboardService.getUserHistoryDetails(request);
		return GenericUtils.getGenericResponse(response);
	}

	// @Audited(context = "Search", searchId = "emailId", skipRequestKeys = {},
	// skipResponseKeys = {})
	// @PreAuthorize("(hasPermission('OPS_IMS_VIEW'))")
	@RequestMapping(value = "/download", method = RequestMethod.GET, produces = { "application/json" })
	public @ResponseBody ResponseEntity test(@RequestParam("emailId") String emailId) throws InfoPanelException {
		byte[] out = searchUserServices.getTransactionHistoryAsCSV(emailId);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("content-disposition", "attachment; filename=" + emailId + "_transaction_history.csv");
		responseHeaders.add("Content-Type", "text/csv");

		return new ResponseEntity(out, responseHeaders, HttpStatus.OK);
	}

	@Audited(context = "Search", searchId = "request.searchValue", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_USERPANEL_SHOPO_VIEW'))")
	@RequestMapping(value = "/shopoTransactions", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody GenericResponse searchShopoTransaction(@RequestBody SearchShopoTransactionRequest request,
			BindingResult bindingResult) throws OpsPanelException {

		GenericControllerUtils.checkBindingResult(bindingResult, "shopoTransactions in Search Controller");
		return GenericUtils.getGenericResponse(shopoService.searchShopoTransaction(request));
	}

	// @Audited(context = "Search", searchId = "orderId", skipRequestKeys =
	// {"request.mobileNumber"}, skipResponseKeys =
	// {"response.data.mobileNumber","response.data.email"})
	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/paymentPlanInfo", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getPaymentPlanInfo(@RequestParam String orderId) throws Exception {
		return GenericUtils.getGenericResponse(csToolsService.getPaymentPlanInfo(orderId));
	}

	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/paymentAttempts", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getPaymentAttempts(@RequestParam String orderId) throws Exception {
		return GenericUtils.getGenericResponse(csToolsService.getPaymentAttempts(orderId));
	}

	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/fulfillmentDetails", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getFulfillmentDetails(@RequestParam String orderId) throws Exception {
		return GenericUtils.getGenericResponse(csToolsService.getFulfillmentDetails(orderId));
	}

	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/fulfillmentAttemptDetails", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getFulfillmentAttemptDetails(@RequestParam String orderId) throws Exception {
		return GenericUtils.getGenericResponse(csToolsService.getFulfillmentAttemptDetails(orderId));
	}

	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/refundDetails", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getRefundDetails(@RequestParam String orderId) throws Exception {
		return GenericUtils.getGenericResponse( csToolsService.getRefundDetails(orderId) );
	}

	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/campaignHistory", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getCampaignHistory(@RequestParam String orderId) throws Exception {
		return GenericUtils.getGenericResponse(campaignService.getCampaignHistory(orderId));
	}

	private void trimUserSearchRequest(SearchUserRequest request) {
		if (request.getEmail() == null) {
			request.setEmail("");
		} else {
			request.setEmail(request.getEmail().trim());
		}
		if (request.getMobileNumber() == null) {
			request.setMobileNumber("");
		} else {
			request.setMobileNumber(request.getMobileNumber().trim());
		}
		if (request.getUserId() == null) {
			request.setUserId("");
		} else {
			request.setUserId(request.getUserId().trim());
		}
		if (request.getName() == null) {
			request.setName("");
		} else {
			request.setName(request.getName().trim());
		}
		if (request.getFromDate() == null) {
			request.setFromDate("");
		} else {
			request.setFromDate(request.getFromDate().trim());
		}
		if (request.getToDate() == null) {
			request.setToDate("");
		} else {
			request.setToDate(request.getToDate().trim());
		}
	}

	private void trimSearchTransactionRequest(SearchTransactionRequest request) {
		if (request.getEmailId() != null) {
			request.setEmailId(request.getEmailId().trim());
			if (request.getEmailId().equals("")) {
				request.setEmailId(null);
			}
		}
		if (request.getUserId() != null) {
			request.setUserId(request.getUserId().trim());
			if (request.getUserId().equals("")) {
				request.setUserId(null);
			}
		}
		if (request.getWalletTransactionId() != null) {
			request.setWalletTransactionId(request.getWalletTransactionId().trim());
			if (request.getWalletTransactionId().equals("")) {
				request.setWalletTransactionId(null);
			}
		}
		if (request.getFcLoadingTransactionId() != null) {
			request.setFcLoadingTransactionId(request.getFcLoadingTransactionId().trim());
			if (request.getFcLoadingTransactionId().equals("")) {
				request.setFcLoadingTransactionId(null);
			}
		}
		if (request.getFcPaymentTransactionId() != null) {
			request.setFcPaymentTransactionId(request.getFcPaymentTransactionId().trim());
			if (request.getFcPaymentTransactionId().equals("")) {
				request.setFcPaymentTransactionId(null);
			}
		}
		if (request.getFcWithdrawlTransactionId() != null) {
			request.setFcWithdrawlTransactionId(request.getFcWithdrawlTransactionId().trim());
			if (request.getFcWithdrawlTransactionId().equals("")) {
				request.setFcWithdrawlTransactionId(null);
			}
		}
		if (request.getSdPaymentOrderId() != null) {
			request.setSdPaymentOrderId(request.getSdPaymentOrderId().trim());
			if (request.getSdPaymentOrderId().equals("")) {
				request.setSdPaymentOrderId(null);
			}
		}
		if (request.getWalletTransactionType() != null) {
			request.setWalletTransactionType(request.getWalletTransactionType().trim());
			if (request.getWalletTransactionType().equals("")) {
				request.setWalletTransactionType(null);
			}
		}
		if (request.getInstrumentType() != null) {
			request.setInstrumentType(request.getInstrumentType().trim());
			if (request.getInstrumentType().equals("")) {
				request.setInstrumentType(null);
			}
		}
		if (request.getMerchantName() != null) {
			request.setMerchantName(request.getMerchantName().trim());
			if (request.getMerchantName().equals("")) {
				request.setMerchantName(null);
			}
		}
	}

	@PreAuthorize("(hasPermission('OPS_USERPANEL_view'))")
	@RequestMapping(value = "/getAccBalanceDetails", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getAccBalanceDetails(@RequestBody GetAccountBalanceDetailsRequest request)
			throws InfoPanelException {
		
		GetAccountBalanceDetailsResponse getAccountBalanceDetailsResponse = searchUserServices.getAccBalanceDetails(request);
		return GenericUtils.getGenericResponse(getAccountBalanceDetailsResponse);
	}
}