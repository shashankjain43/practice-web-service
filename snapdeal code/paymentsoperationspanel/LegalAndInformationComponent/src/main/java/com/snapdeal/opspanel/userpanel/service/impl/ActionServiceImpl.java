/**
 * 
 */
package com.snapdeal.opspanel.userpanel.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.client.impl.BlackWhiteListServiceClientImpl;
import com.snapdeal.ims.dto.ClientConfig;
import com.snapdeal.ims.enums.ConfigureUserBasedOn;
import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.enums.Reason;
import com.snapdeal.ims.enums.StatusEnum;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.CloseAccountByEmailRequest;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.response.BlacklistEntityResponse;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.dao.ActionAuditDao;
import com.snapdeal.opspanel.userpanel.entity.ActionAuditEntity;
import com.snapdeal.opspanel.userpanel.enums.FraudManagementReason;
import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.enums.UserPanelIdType;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.filters.Filter;
import com.snapdeal.opspanel.userpanel.filters.FiltersFactory;
import com.snapdeal.opspanel.userpanel.request.BlackListWhiteListUserRequest;
import com.snapdeal.opspanel.userpanel.request.CloseUserAccountRequest;
import com.snapdeal.opspanel.userpanel.request.CloseUserAccountResponse;
import com.snapdeal.opspanel.userpanel.request.EnableDisableUserRequest;
import com.snapdeal.opspanel.userpanel.request.EnableWalletRequest;
import com.snapdeal.opspanel.userpanel.request.SearchTransactionRequest;
import com.snapdeal.opspanel.userpanel.request.SearchUserRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.request.ViewUserAccountHistoryRequest;
import com.snapdeal.opspanel.userpanel.response.BlackListWhiteListUserResponse;
import com.snapdeal.opspanel.userpanel.response.EnableDisableUserResponse;
import com.snapdeal.opspanel.userpanel.response.SearchTxnResponse;
import com.snapdeal.opspanel.userpanel.response.SearchUserResponse;
import com.snapdeal.opspanel.userpanel.response.TransactionDetails;
import com.snapdeal.opspanel.userpanel.response.ViewUserAccountResponse;
import com.snapdeal.opspanel.userpanel.service.ActionService;
import com.snapdeal.opspanel.userpanel.service.PMSService;
import com.snapdeal.opspanel.userpanel.service.SearchUserServices;
import com.snapdeal.opspanel.userpanel.service.SearchUserServicesAgg;
import com.snapdeal.opspanel.userpanel.service.WalletPanelService;
import com.snapdeal.opspanel.userpanel.utils.InfoPanelS3Utils;
import com.snapdeal.payments.pms.exceptions.ProfileManagementException;
import com.snapdeal.payments.pms.service.model.EntityDetailsResponse;
import com.snapdeal.payments.pms.service.model.GetEntityHistoryRequest;
import com.snapdeal.payments.pms.service.model.GetEntityHistoryResponse;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service("ActionService")
@Slf4j
public class ActionServiceImpl implements ActionService {

	@Autowired
	IUserServiceClient iUserService;

	@Autowired
	WalletPanelService walletPanelService;

	@Autowired
	SearchUserServices searchUserServices;

	@Autowired
	SearchUserServicesAgg searchUserServicesAgg;

	@Autowired
	ActionAuditDao actionAudit;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	BlackWhiteListServiceClientImpl blackWhiteListServiceClientImpl;

	@Autowired
	InfoPanelS3Utils amazonUtils;

	@Autowired
	private HttpServletRequest servletRequest;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private PMSService pms;

	public static final String USER = "USER";

	@Override
	public SearchTxnResponse searchTransaction(SearchTransactionRequest request) throws InfoPanelException {
		//SearchTxnResponse with last evaluated Key used for pagination
		SearchTxnResponse searchTxnResponse = new SearchTxnResponse();
		String key = null;
		List<TransactionDetails> transactionDetailsList = null;

		FiltersFactory filtersFactory = FiltersFactory.getInstance();
		Filter<TransactionDetails, SearchTransactionRequest> filter = filtersFactory
				.getFilter(TransactionDetails.class);

		if (request.getWalletTransactionId() != null) {

			transactionDetailsList = walletPanelService.getTransactionsById(request);

			setUserInfoInTransactionDetails(transactionDetailsList);
			filter.filterData(transactionDetailsList, request);

		} else if (request.getFcLoadingTransactionId() != null || request.getFcPaymentTransactionId() != null
				|| request.getFcWithdrawlTransactionId() != null || request.getSdPaymentOrderId() != null) {

			transactionDetailsList = walletPanelService.getTransactionByReference(request);

			setUserInfoInTransactionDetails(transactionDetailsList);
			filter.filterData(transactionDetailsList, request);

		} else if (request.getUserId() != null) {

			GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest();
			getUserByIdRequest.setUserId(request.getUserId());
			GetUserResponse getUserResponse = searchUserServices.searchUserById(getUserByIdRequest);
			if (request.getEmailId() != null
					&& !request.getEmailId().equalsIgnoreCase(getUserResponse.getUserDetails().getUserId())) {
				return null;
			}
			request.setEmailId(getUserResponse.getUserDetails().getEmailId());
			//SearchTxnResponse with last evaluated Key used for pagination
			SearchTxnResponse temp = new SearchTxnResponse();
			temp = walletPanelService.getTransactionsForUser(request);
			transactionDetailsList = temp.getTransactionDetailsList();
			key = temp.getLastEvaluatedKey();
			filter.filterData(transactionDetailsList, request);

		} else if (request.getEmailId() != null) {

			try {
				GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
				getUserByEmailRequest.setEmailId(request.getEmailId());
				GetUserResponse getUserResponse = searchUserServices.searchUserByEmail(getUserByEmailRequest);
				request.setUserId(getUserResponse.getUserDetails().getUserId());
			} catch( Exception e ) {
				log.info( "Exception while getting user id from ims " + ExceptionUtils.getFullStackTrace( e ) );
			}

			//SearchTxnResponse with last evaluated Key used for pagination
			SearchTxnResponse temp = new SearchTxnResponse();
			temp = walletPanelService.getTransactionsForUser(request);
			transactionDetailsList = temp.getTransactionDetailsList();
			key = temp.getLastEvaluatedKey();

			filter.filterData(transactionDetailsList, request);

		} else {
			return null;
		}
		Collections.sort( transactionDetailsList, new Comparator<TransactionDetails>(){
			public int compare(TransactionDetails o1, TransactionDetails o2){
				return o2.getTransactionDate().compareTo(o1.getTransactionDate());
			}
		});
		searchTxnResponse.setLastEvaluatedKey(key);
		searchTxnResponse.setTransactionDetailsList(transactionDetailsList);
		return searchTxnResponse;
	}

	@Override
	public EnableDisableUserResponse enableDisableUser(EnableDisableUserRequest enableDisableUserRequest)
			throws InfoPanelException {

		ConfigureUserStateResponse configureUserStateResponse;
		EnableDisableUserResponse enableDisableUserResponse;
		String actionKeyType;

		try {

			ConfigureUserStateRequest configureUserStateRequest = new ConfigureUserStateRequest();

			imsReasonMapper(configureUserStateRequest, enableDisableUserRequest);

			if (StringUtils.isNumeric(enableDisableUserRequest.getUserId())) {
				configureUserStateRequest.setMobileNumber(enableDisableUserRequest.getUserId());
				configureUserStateRequest.setConfigureUserBasedOn(ConfigureUserBasedOn.MOBILE);
				actionKeyType = UserPanelIdType.MOBILE_NUMBER.toString();
			}

			else if (Pattern.compile(".+@.+\\.[a-z]+").matcher(enableDisableUserRequest.getUserId()).matches()) {
				configureUserStateRequest.setEmailId((enableDisableUserRequest.getUserId()));
				configureUserStateRequest.setConfigureUserBasedOn(ConfigureUserBasedOn.EMAIL);
				actionKeyType = UserPanelIdType.EMAIL_ID.toString();
			}

			else {
				configureUserStateRequest.setUserId(enableDisableUserRequest.getUserId());
				configureUserStateRequest.setConfigureUserBasedOn(ConfigureUserBasedOn.USER_ID);
				actionKeyType = UserPanelIdType.IMS_ID.toString();
			}

			if (enableDisableUserRequest.getAction().equals(UserPanelAction.ENABLE_USER))
				configureUserStateRequest.setEnable(true);

			else if (enableDisableUserRequest.getAction().equals(UserPanelAction.DISABLE_USER))
				configureUserStateRequest.setEnable(false);
			else
				throw new InfoPanelException("MT-5103", "Incorrect Action Type");

			ClientConfig clientConfig = new ClientConfig();
			//			clientConfig.setAppRequestId(getRequestId());
			clientConfig.setAppRequestId( enableDisableUserRequest.getRequestId() );

			configureUserStateRequest.setClientConfig(clientConfig);

			configureUserStateResponse = iUserService.configureUserState(configureUserStateRequest);


			// audit actions
			ActionAuditEntity actionAuditEntity = new ActionAuditEntity();
			PopulateAuditEntity(actionAuditEntity, enableDisableUserRequest.getAction(), enableDisableUserRequest.getUserId(), enableDisableUserRequest.getReason(), enableDisableUserRequest.getActionPerformer(),UserPanelIdType.valueOf(actionKeyType),enableDisableUserRequest.getOtherReason(),enableDisableUserRequest.getTypeOfFraud());
			if (enableDisableUserRequest.getReason().equals(FraudManagementReason.OTHERS))
				actionAuditEntity.setReason(enableDisableUserRequest.getOtherReason());
			auditActions(actionAuditEntity);

		} catch (ServiceException e) {
			log.info("Error while enabling disabling user Request " + enableDisableUserRequest + " Exception " + ExceptionUtils.getFullStackTrace(e));
			throw new InfoPanelException(e.getErrCode(), e.getErrMsg());
		}

		enableDisableUserResponse = new EnableDisableUserResponse();
		enableDisableUserResponse.setUserId(enableDisableUserRequest.getUserId());

		if (configureUserStateResponse.getStatus().equals(StatusEnum.SUCCESS)) {
			if (enableDisableUserRequest.getAction().equals(UserPanelAction.ENABLE_USER))
				enableDisableUserResponse.setMessage("user has been enabled successfully");

			else if (enableDisableUserRequest.getAction().equals(UserPanelAction.DISABLE_USER))
				enableDisableUserResponse.setMessage("user has been disabled successfully");
			else
				throw new InfoPanelException("MT-5103", "Incorrect Action Type");

		} else {

			enableDisableUserResponse.setMessage("Failed in operating due to server issue");
		}

		return enableDisableUserResponse;

	}

	@Override
	public void suspendWallet(SuspendWalletRequest suspendWalletRequest) throws InfoPanelException {


		ActionAuditEntity actionAuditEntity = new ActionAuditEntity();
		PopulateAuditEntity(actionAuditEntity, UserPanelAction.SUSPEND_WALLET, suspendWalletRequest.getUserId(), suspendWalletRequest.getReason(), suspendWalletRequest.getActionPerformer(),suspendWalletRequest.getUserIdType(),suspendWalletRequest.getOtherReason(),suspendWalletRequest.getTypeOfFraud());
		GetUserResponse getUserResponse;

		if (suspendWalletRequest.getUserIdType() == null) {
			throw new InfoPanelException("MT-5100", "Please Enter a valid user Id and user Id type");
		}

		switch (suspendWalletRequest.getUserIdType()) {
		case IMS_ID:
			break;
		case EMAIL_ID:
			GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
			getUserByEmailRequest.setEmailId(suspendWalletRequest.getUserId());
			getUserResponse = searchUserServices.searchUserByEmail(getUserByEmailRequest);
			suspendWalletRequest.setUserId(getUserResponse.getUserDetails().getUserId());
			break;
		case MOBILE_NUMBER:
			GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
			getUserByMobileRequest.setMobileNumber(suspendWalletRequest.getUserId());
			getUserResponse = searchUserServices.searchUserByMobile(getUserByMobileRequest);
			suspendWalletRequest.setUserId(getUserResponse.getUserDetails().getUserId());
			break;
		default:
			throw new InfoPanelException("MT-5100", "Please Enter a valid user Id and user Id type");
		}

		// audit actions


		if (suspendWalletRequest.getReason().equals(FraudManagementReason.OTHERS))
			actionAuditEntity.setReason(suspendWalletRequest.getOtherReason());

		suspendWalletRequest.setUserIdType(UserPanelIdType.IMS_ID);
		walletPanelService.suspendSDMoneyAccount(suspendWalletRequest);


		auditActions(actionAuditEntity);

	}

	@Override
	public BlackListWhiteListUserResponse blackListWhiteListUser(
			BlackListWhiteListUserRequest blackListWhiteListUserRequest) throws InfoPanelException {

		BlackListWhiteListUserResponse blackListWhiteListUserResponse = new BlackListWhiteListUserResponse();

		BlacklistEntityResponse blacklistEntityResponse = new BlacklistEntityResponse();

		BlacklistEntityRequest blacklistEntityRequest = new BlacklistEntityRequest();
		blacklistEntityRequest.setBlackListType(EntityType.EMAIL);
		blacklistEntityRequest.setEntity(blackListWhiteListUserRequest.getEmailId());

		ClientConfig clientConfig = new ClientConfig();
		//clientConfig.setAppRequestId(getRequestId());
		clientConfig.setAppRequestId(blackListWhiteListUserRequest.getRequestId());
		blacklistEntityRequest.setClientConfig(clientConfig);

		try {
			if (blackListWhiteListUserRequest.getAction().equals(UserPanelAction.BLACK_LIST_USER))
				blacklistEntityResponse = blackWhiteListServiceClientImpl.addBlacklistEntity(blacklistEntityRequest);

			else if (blackListWhiteListUserRequest.getAction().equals(UserPanelAction.WHITE_LIST_USER))
				blacklistEntityResponse = blackWhiteListServiceClientImpl.removeBlacklistEntity(blacklistEntityRequest);

		} catch (Exception e) {

			throw new InfoPanelException("MT-11019",
					"Exception in While making Request with message " + e.getMessage());

		}

		if (blacklistEntityResponse.isSuccess()) {

			blackListWhiteListUserResponse.setEmailId(blackListWhiteListUserRequest.getEmailId());
			if (blackListWhiteListUserRequest.getAction().equals(UserPanelAction.BLACK_LIST_USER))
				blackListWhiteListUserResponse.setMessage("emailId has been black listed successfully");

			if (blackListWhiteListUserRequest.getAction().equals(UserPanelAction.WHITE_LIST_USER))
				blackListWhiteListUserResponse.setMessage("emailId has been white listed successfully");

			ActionAuditEntity actionAuditEntity = new ActionAuditEntity();
			PopulateAuditEntity(actionAuditEntity, blackListWhiteListUserRequest.getAction(), blackListWhiteListUserRequest.getEmailId(), blackListWhiteListUserRequest.getReason(),  blackListWhiteListUserRequest.getActionPerformer(),UserPanelIdType.EMAIL_ID,blackListWhiteListUserRequest.getOtherReason(),blackListWhiteListUserRequest.getTypeOfFraud());
			if (blackListWhiteListUserRequest.getReason().equals(FraudManagementReason.OTHERS))
				actionAuditEntity.setReason(blackListWhiteListUserRequest.getOtherReason());
			auditActions(actionAuditEntity);

		}

		else {

			blackListWhiteListUserResponse.setEmailId(blackListWhiteListUserRequest.getEmailId());

			if (blackListWhiteListUserRequest.getAction().equals(UserPanelAction.BLACK_LIST_USER))
				blackListWhiteListUserResponse.setMessage("Failed in black listing emailId ");

			if (blackListWhiteListUserRequest.getAction().equals(UserPanelAction.WHITE_LIST_USER))
				blackListWhiteListUserResponse.setMessage("Failed in white listing emailId ");
		}

		return blackListWhiteListUserResponse;
	}

	private List<TransactionDetails> setUserInfoInTransactionDetails(List<TransactionDetails> transactionDetailsList)
			throws InfoPanelException {

		for (TransactionDetails transactionDetails : transactionDetailsList) {
			if (transactionDetails.getUserId() != null) {
				try {
					SearchUserRequest searchUserRequest = new SearchUserRequest();
					searchUserRequest.setUserId(transactionDetails.getUserId());
					SearchUserResponse searchUserResponse = searchUserServicesAgg.searchUser(searchUserRequest);
					transactionDetails.setEmailId(searchUserResponse.getEmail());
					transactionDetails.setMigrationStatus(searchUserResponse.getMigrationStatus());
					transactionDetails.setImsAccountStatus(searchUserResponse.getImsAccountStatus());
				} catch( Exception e ) {

					log.info( "Exception while searching user through searchUserServicesAgg for user Id " + transactionDetails.getUserId() );

					transactionDetails.setEmailId( "Could not fetch" );
					transactionDetails.setMigrationStatus( "Could not fetch" );
					transactionDetails.setImsAccountStatus( "Could not fetch" );
				}
			} else {

				transactionDetails.setUserId( "Could not fetch" );
				transactionDetails.setEmailId( "Could not fetch" );
				transactionDetails.setMigrationStatus( "Could not fetch" );
				transactionDetails.setImsAccountStatus( "Could not fetch" );
			}
		}
		return transactionDetailsList;
	}

	private void auditActions(ActionAuditEntity actionAuditEntity) throws InfoPanelException {

		//String token = servletRequest.getHeader("token");
		//String emailId = tokenService.getEmailFromToken(token);

		//actionAuditEntity.setClientName(emailId);

		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("IST"));
		actionAuditEntity.setActionTime(cal.getTime());
		actionAudit.auditAction(actionAuditEntity);

	}

	private void imsReasonMapper(ConfigureUserStateRequest configureUserStateRequest,
			EnableDisableUserRequest enableDisableUserRequest) {

		if(enableDisableUserRequest.getReason().trim().equalsIgnoreCase("Suspected Fraud") ||
				enableDisableUserRequest.getReason().trim().equalsIgnoreCase("Confirmed Fraud")	) {
			configureUserStateRequest.setReason(Reason.FRAUD);
		} else if (enableDisableUserRequest.getReason().trim().equalsIgnoreCase("Confirmed by Customer")) {
			configureUserStateRequest.setReason(Reason.USER_INITIATED);

		} else {
			configureUserStateRequest.setReason(Reason.OTHERS);
			configureUserStateRequest.setDescription(enableDisableUserRequest.getReason());
		}

	}

	private String getRequestId() {
		return ( String ) servletRequest.getAttribute( "requestId" );
	}

	@Override
	public CloseUserAccountResponse closeUserAccount(CloseUserAccountRequest closeUserAccountRequest)
			throws InfoPanelException {

		ClientConfig clientConfig = new ClientConfig();
		//clientConfig.setAppRequestId(getRequestId());
		clientConfig.setAppRequestId(closeUserAccountRequest.getRequestId());

		CloseAccountByEmailRequest request = new CloseAccountByEmailRequest();
		request.setEmailId(closeUserAccountRequest.getUserId());
		request.setClientConfig(clientConfig);

		CloseAccountResponse closeAccountResponse = null;

		try {
			closeAccountResponse = iUserService.closeAccount(request);
		} catch (ServiceException e) {

			log.error(e.getErrMsg() + " " + closeUserAccountRequest.getUserId());
			throw new InfoPanelException("ET-11033", e.getErrMsg(), "IUserServiceClient");

		}

		if (!closeAccountResponse.isStatus()) {

			throw new InfoPanelException("ET-11033", "User Account can'nt be disabled");

		}

		// audit actions
		ActionAuditEntity actionAuditEntity = new ActionAuditEntity();
		PopulateAuditEntity(actionAuditEntity, closeUserAccountRequest.getAction(), closeUserAccountRequest.getUserId(), closeUserAccountRequest.getReason(),  closeUserAccountRequest.getActionPerformer(),UserPanelIdType.EMAIL_ID,closeUserAccountRequest.getOtherReason(),closeUserAccountRequest.getTypeOfFraud());
		if (closeUserAccountRequest.getReason() == null)
			actionAuditEntity.setReason(closeUserAccountRequest.getOtherReason());
		auditActions(actionAuditEntity);

		CloseUserAccountResponse closeUserAccountResponse = new CloseUserAccountResponse();
		closeUserAccountResponse.setUserId(closeUserAccountRequest.getUserId());
		closeUserAccountResponse.setMessage("User account closed successfully");

		return closeUserAccountResponse;
	}

	@Override
	public void enableWallet(EnableWalletRequest enableWalletRequest) throws InfoPanelException {

		GetUserResponse getUserResponse;

		if (enableWalletRequest.getUserIdType() == null) {
			throw new InfoPanelException("MT-5100", "Please Enter a valid user Id and user Id type");
		}

		switch (enableWalletRequest.getUserIdType()) {
		case IMS_ID:
			break;
		case EMAIL_ID:
			GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
			getUserByEmailRequest.setEmailId(enableWalletRequest.getUserId());
			getUserResponse = searchUserServices.searchUserByEmail(getUserByEmailRequest);
			enableWalletRequest.setUserId(getUserResponse.getUserDetails().getUserId());
			break;
		case MOBILE_NUMBER:
			GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
			getUserByMobileRequest.setMobileNumber(enableWalletRequest.getUserId());
			getUserResponse = searchUserServices.searchUserByMobile(getUserByMobileRequest);
			enableWalletRequest.setUserId(getUserResponse.getUserDetails().getUserId());
			break;
		default:
			throw new InfoPanelException("MT-5100", "Please Enter a valid user Id and user Id type");
		}

		// audit actions

		ActionAuditEntity actionAuditEntity = new ActionAuditEntity();

		PopulateAuditEntity(actionAuditEntity, UserPanelAction.ENABLE_WALLET,enableWalletRequest.getUserId(),enableWalletRequest.getReason(),enableWalletRequest.getActionPerformer(),enableWalletRequest.getUserIdType(),enableWalletRequest.getOtherReason(),enableWalletRequest.getTypeOfFraud());

		if (enableWalletRequest.getReason().equals(FraudManagementReason.OTHERS))
			actionAuditEntity.setReason(enableWalletRequest.getOtherReason());

		enableWalletRequest.setUserIdType(UserPanelIdType.IMS_ID);
		walletPanelService.enableSDMoneyAccount(enableWalletRequest);
		auditActions(actionAuditEntity);

	}

	public void PopulateAuditEntity(ActionAuditEntity ae,UserPanelAction action,String userId,String reason,String actionPerformer,UserPanelIdType idType,String comments, String typeOfFraud) {
		GetUserResponse getUserResponse;
		try {
			ae.setActionkeyType(idType.toString());
			if(idType.equals(UserPanelIdType.EMAIL_ID)) {
				GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
				getUserByEmailRequest.setEmailId(userId);
				getUserResponse = searchUserServices.searchUserByEmail(getUserByEmailRequest);	
				userId=getUserResponse.getUserDetails().getUserId();
				ae.setActionkeyType(UserPanelIdType.IMS_ID.toString());
			} else if (idType.equals(UserPanelIdType.MOBILE_NUMBER)) {
				GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
				getUserByMobileRequest.setMobileNumber(userId);
				getUserResponse = searchUserServices.searchUserByMobile(getUserByMobileRequest);
				userId=getUserResponse.getUserDetails().getUserId();
				ae.setActionkeyType(UserPanelIdType.IMS_ID.toString());
			} 
		} catch(InfoPanelException ipe) {
			log.info(" Could not get User Object from IMS ");
		}

		ae.setReason(reason);
		ae.setAction(action);
		ae.setClientName(actionPerformer);
		ae.setActionTime(new Date());
		ae.setActionKey(userId);
		ae.setComments(comments);
		ae.setTypeOfFraud(typeOfFraud);
	}

	@Override
	public List<ViewUserAccountResponse> viewUserHistoryAccount(ViewUserAccountHistoryRequest viewUserAccountRequest) throws InfoPanelException
	{

		String actionKeyType=null;
		GetUserResponse getUserResponse = new GetUserResponse();
		List<ViewUserAccountResponse> globalList= new ArrayList<ViewUserAccountResponse>();
		try{

			if (StringUtils.isNumeric(viewUserAccountRequest.getUserId())) {
				GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
				getUserByMobileRequest.setMobileNumber(viewUserAccountRequest.getUserId());
				getUserResponse = searchUserServices.searchUserByMobile(getUserByMobileRequest);
				globalList.addAll(actionAudit.viewUserHistory(getUserResponse.getUserDetails().getEmailId()));
				globalList.addAll(actionAudit.viewUserHistory(getUserResponse.getUserDetails().getUserId()));
				actionKeyType = UserPanelIdType.MOBILE_NUMBER.toString();
			} else if (Pattern.compile(".+@.+\\.[a-z]+").matcher(viewUserAccountRequest.getUserId()).matches()) {
				GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
				getUserByEmailRequest.setEmailId(viewUserAccountRequest.getUserId());
				getUserResponse = searchUserServices.searchUserByEmail(getUserByEmailRequest);	
				globalList.addAll(actionAudit.viewUserHistory(getUserResponse.getUserDetails().getMobileNumber()));
				globalList.addAll(actionAudit.viewUserHistory(getUserResponse.getUserDetails().getUserId()));
				actionKeyType = UserPanelIdType.EMAIL_ID.toString();
			} else {
				GetUserByIdRequest request = new GetUserByIdRequest();
				request.setUserId(viewUserAccountRequest.getUserId());
				getUserResponse = searchUserServices.searchUserById(request);
				globalList.addAll(actionAudit.viewUserHistory(getUserResponse.getUserDetails().getMobileNumber()));
				globalList.addAll(actionAudit.viewUserHistory(getUserResponse.getUserDetails().getEmailId()));
				actionKeyType = UserPanelIdType.IMS_ID.toString();
			}

		} catch(InfoPanelException ipe) {
			log.info("InfoPanel Exception Failed to get history" + ipe.getErrCode()+":"+ipe.getErrMessage());
		}
		String userId = null;
		try {
			globalList.addAll(actionAudit.viewUserHistory(viewUserAccountRequest.getUserId()));


			if(getUserResponse != null){
				if(getUserResponse.getUserDetails() != null){
					userId = getUserResponse.getUserDetails().getUserId();
					log.info("Successfully Fetched userId from IMS : " + userId);
				} else{
					log.info("getUserResponse FOUND but userDetails = NULL ...");
				}
			} else {
				log.info("getUserResponse = NULL ...");
			}

			GetEntityHistoryRequest getEntityHistoryRequest = new GetEntityHistoryRequest();
			GetEntityHistoryResponse getEntityHistoryResponse  = new GetEntityHistoryResponse();

			getEntityHistoryRequest.setEntityId(userId);
			getEntityHistoryRequest.setEntityType(USER);


			getEntityHistoryResponse = pms.getEntityHistory(getEntityHistoryRequest);
			log.info("Successfully Called getEntityHistory for " + userId);


			List<EntityDetailsResponse> entityDetailsResponses = new ArrayList<EntityDetailsResponse>();


			if(getEntityHistoryResponse != null && getEntityHistoryResponse.size() != 0){
				log.info("getEntityHistoryResponse not NULL,size = " + getEntityHistoryResponse.size());
				entityDetailsResponses = getEntityHistoryResponse;
				for(EntityDetailsResponse edr : entityDetailsResponses){

					ViewUserAccountResponse viewUserAccountResponse = new ViewUserAccountResponse();
					viewUserAccountResponse.setAction(edr.getEntityStatus().name());
					viewUserAccountResponse.setActionKey(edr.getEntityId());
						viewUserAccountResponse.setActionKeyType(UserPanelIdType.IMS_ID.name());
					DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = edr.getUpdatedOn();
					if(date!=null) {
						log.info("Original date from PMS : " + date.toString());

						String formattedDate = targetFormat.format(date);

						viewUserAccountResponse.setActionTime(formattedDate);
					} else {
						viewUserAccountResponse.setActionTime(targetFormat.format(new Date()));
					}
					viewUserAccountResponse.setReason(edr.getUpdateReason());
					if(edr.getMetaInfo()!=null){
						viewUserAccountResponse.setAgentId(edr.getMetaInfo().getUpdatedBy());
						viewUserAccountResponse.setComments("JIRA ID :" + edr.getMetaInfo().getJiraId() + ", Update Code : " + edr.getMetaInfo().getUpdateCode() + ", Breached Rule : " + edr.getMetaInfo().getBreachedRule() + ", Info : " + edr.getMetaInfo().getBlackListedEntityInfo());
						viewUserAccountResponse.setTypeOfFraud(edr.getMetaInfo().getFraudType());
					}
					globalList.add(viewUserAccountResponse);
				}
			} else {
				log.info("entityDetailsResponses either empty or null ...");
			}
		} catch (ProfileManagementException e) {
			log.info("ProfileManagementException while calling getEntityHistory for " + userId);
			log.info("Excpetion : [" + e.getErrorCode().name() +  "," + e.getMessage() + "]");

		} catch (Exception e) {
			log.info("Other Exception while calling getEntityHistory for " + userId);
			log.info("Other Excpetion : [" + e.getMessage() + "]");
		}	

		Comparator<ViewUserAccountResponse > comparator= new Comparator<ViewUserAccountResponse>(){

			public int compare(ViewUserAccountResponse o1, ViewUserAccountResponse o2) {
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date d1;
				Date d2;
				try {
					d1=formatter.parse(o1.getActionTime());
					log.info("Successfully parsed date = " + o1.getActionTime());
					d2 = formatter.parse(o2.getActionTime());
					log.info("Successfully parsed date = " + o2.getActionTime());
				} catch (ParseException e) {
					log.info("ParseException while parsing date, [" + e.getMessage() + "]");
					return 0;
				}
				return d1.after(d2)?-1:1;
			}
		};
		Collections.sort(globalList, comparator);																						
		return globalList;
	}
}
