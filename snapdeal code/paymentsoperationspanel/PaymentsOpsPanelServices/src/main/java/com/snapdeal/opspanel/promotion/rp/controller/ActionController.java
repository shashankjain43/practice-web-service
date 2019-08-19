package com.snapdeal.opspanel.promotion.rp.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.utils.AmazonS3Utils;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.enums.UserPanelAction;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;
import com.snapdeal.opspanel.userpanel.request.BlackListWhiteListUserRequest;
import com.snapdeal.opspanel.userpanel.request.CloseUserAccountRequest;
import com.snapdeal.opspanel.userpanel.request.DownloadActionBulkFileRequest;
import com.snapdeal.opspanel.userpanel.request.EnableDisableUserRequest;
import com.snapdeal.opspanel.userpanel.request.EnableWalletRequest;
import com.snapdeal.opspanel.userpanel.request.FraudManagementRequest;
import com.snapdeal.opspanel.userpanel.request.SuspendWalletRequest;
import com.snapdeal.opspanel.userpanel.request.ViewUserAccountHistoryRequest;
import com.snapdeal.opspanel.userpanel.response.BlackListWhiteListUserResponse;
import com.snapdeal.opspanel.userpanel.response.EnableDisableUserResponse;
import com.snapdeal.opspanel.userpanel.response.FraudManagementResponse;
import com.snapdeal.opspanel.userpanel.response.GenericResponse;
import com.snapdeal.opspanel.userpanel.service.ActionService;
import com.snapdeal.opspanel.userpanel.service.BulkService;
import com.snapdeal.opspanel.userpanel.service.FileManagementService;
import com.snapdeal.opspanel.userpanel.service.FraudManagementService;
import com.snapdeal.opspanel.userpanel.service.WalletPanelService;
import com.snapdeal.opspanel.userpanel.utils.GenericUtils;
import com.snapdeal.opspanel.userpanel.utils.InfoPanelS3Utils;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Controller
@RequestMapping("action/")
@Slf4j
public class ActionController {

	@Autowired
	ActionService actionService;

	@Autowired
	BulkService bulkService;

	@Autowired
	AmazonS3Utils amazonUtils;

	@Autowired
	WalletPanelService walletPanelService;

	@Autowired
	GenericUtils genericUtils;

	@Autowired
	InfoPanelS3Utils amazonS3Utils;

	@Autowired
	FileManagementService fileManagementService;

	@Autowired
	FraudManagementService fraudManagementService;

	@Autowired
	HttpServletRequest servletRequest;

	@Autowired
	TokenService tokenService;
	
	private final String csvDelimiter = ",";

	@Audited(context = "Action", searchId = "blackListWhiteListUserRequest.emailId", reason = "blackListWhiteListUserRequest.reason", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('actionpanel_singleAction')  or hasPermission('OPS_IMS_ACTION'))")
	@RequestMapping(value = "/blackListUser", method = RequestMethod.POST)
	public @ResponseBody GenericResponse blackListUser(
			@RequestBody BlackListWhiteListUserRequest blackListWhiteListUserRequest) throws InfoPanelException, OpsPanelException {

		blackListWhiteListUserRequest.setActionPerformer( getEmailIdFromSession() );
		if (blackListWhiteListUserRequest.getAction().equals(UserPanelAction.BLACK_LIST_USER))
			return blackWhiteListUser(blackListWhiteListUserRequest);

		else
			throw new InfoPanelException("ET-11023",
					"Action doesnot match with the api.Action must be BLACK_LIST_USER");
	}

	@Audited(context = "Action", searchId = "blackListWhiteListUserRequest.emailId", reason = "blackListWhiteListUserRequest.reason", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('actionpanel_singleAction')  or hasPermission('OPS_IMS_ACTION'))")
	@RequestMapping(value = "/whiteListUser", method = RequestMethod.POST)
	public @ResponseBody GenericResponse whiteListUser(
			@RequestBody BlackListWhiteListUserRequest blackListWhiteListUserRequest) throws InfoPanelException, OpsPanelException {

		blackListWhiteListUserRequest.setActionPerformer( getEmailIdFromSession() );
		blackListWhiteListUserRequest.setRequestId( getRequestId() );
		if (blackListWhiteListUserRequest.getAction().equals(UserPanelAction.WHITE_LIST_USER))
			return blackWhiteListUser(blackListWhiteListUserRequest);

		else
			throw new InfoPanelException("ET-11023",
					"Action doesnot match with the api. Action must be WHITE_LIST_USER");
	}


	@Audited(context = "Action", searchId = "enableDisableUserRequest.userId", reason = "enableDisableUserRequest.reason", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('actionpanel_singleAction') or hasPermission('OPS_IMS_ACTION'))")
	@RequestMapping(value = "/enableUser", method = RequestMethod.POST)
	public @ResponseBody GenericResponse enableUser(@RequestBody EnableDisableUserRequest enableDisableUserRequest)
			throws InfoPanelException, OpsPanelException {

		enableDisableUserRequest.setActionPerformer( getEmailIdFromSession() );
		enableDisableUserRequest.setRequestId( getRequestId() );

		return enableDisableUser(enableDisableUserRequest);
	}

	@Audited(context = "Action", searchId = "enableDisableUserRequest.userId", reason = "enableDisableUserRequest.reason", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('actionpanel_singleAction') or hasPermission('OPS_IMS_ACTION'))")
	@RequestMapping(value = "/disableUser", method = RequestMethod.POST)
	public @ResponseBody GenericResponse disableUser(@RequestBody EnableDisableUserRequest enableDisableUserRequest)
			throws InfoPanelException, OpsPanelException {

		enableDisableUserRequest.setActionPerformer(getEmailIdFromSession());
		enableDisableUserRequest.setRequestId( getRequestId() );
		if (enableDisableUserRequest.getAction().equals(UserPanelAction.DISABLE_USER))
			return enableDisableUser(enableDisableUserRequest);

		else
			throw new InfoPanelException("ET-11023", "Action doesnot match with the api. Action must be DISABLE_USER");

	}

	@Audited(context = "Action", searchId = "actionBulkRequest.idType", reason = "actionBulkRequest.reason", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('actionpanel_bulkAction'))")
	@RequestMapping(value = "/bulk/userActions", method = RequestMethod.POST)
	public @ResponseBody GenericResponse bulkUserActions(@Valid ActionBulkRequest actionBulkRequest, BindingResult bindingResult,
			@RequestPart(value = "file", required = false) MultipartFile file) throws InfoPanelException, OpsPanelException {

		String emailId = getEmailIdFromSession();

		actionBulkRequest.setActionPerformer( emailId );
		actionBulkRequest.setRequestId( getRequestId() );

		log.info( "BulkAction Step: Recieved a file for bulk user actions: " + file.getName() + "Request " + actionBulkRequest + "Email Id " + emailId );

		GenericControllerUtils.checkBindingResult( bindingResult, "Bulk User Action Request" );

		log.info( "BulkAction Step: Uploading file");
		String uploadFilePath = genericUtils.uploadFile(file);
		log.info( "File upload complete");

		log.info( "BulkAction Step: Verifying file" );
		verifActionBulkFile( uploadFilePath );

		Date currDate = new Date();
		actionBulkRequest.setUploadFilePath(uploadFilePath);

		try {
			log.info( "BulkAction Step: pushing bulk action input file to amazon");
			fileManagementService.pushActionBulkInputFile(actionBulkRequest, currDate, emailId);
		} catch (Exception e) {
			log.info("Exception while uploading result file to amazon " + ExceptionUtils.getFullStackTrace (e ) );
			try {
				log.info( "BulkAction Step: retrying bulk actoin input file to amazon" );
				fileManagementService.pushActionBulkInputFile(actionBulkRequest, currDate, emailId );
			} catch( Exception ex ) {
				log.info( "Exception even after retrying file upload to amazon " + ExceptionUtils.getFullStackTrace( ex ) );
				deleteFile( uploadFilePath );
				throw new InfoPanelException("MT-5102", "Unable to upload file to amazon s3 server");
			}
		}

		bulkService.executeBulkAction(actionBulkRequest, currDate, emailId );

		return GenericUtils.getGenericResponse( "File successfully uploaded, check file history for results" );
	}

	private void verifActionBulkFile( String uploadFilePath ) throws OpsPanelException {
		BufferedReader br = null;
		int rowNumber = 1;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStream = new FileInputStream( uploadFilePath );
			inputStreamReader = new InputStreamReader(inputStream);
			br = new BufferedReader( inputStreamReader);
			String line = br.readLine();
			line = Joiner.on( ",").join( line.trim().split(",") );
			if( ! line.equals( "id" ) ) {
				log.info( "BulkAction Step: Invalid header, header does not contains id." );
				throw new OpsPanelException( "UA-1000", "Invalid header. Please look at sample files for correct headers." );
			}
			while( ( line = br.readLine() ) != null ) {
				rowNumber ++;
				String[] tokens = line.split(csvDelimiter);
				if(tokens.length!=1) {
					log.info( "BulkAction Step: Invalid number of columns in row number " + rowNumber );
					throw new OpsPanelException( "UA-1001", "Inavlid number of columns in row " + rowNumber );
				}
			}
		} catch( IOException ioException ) {
			log.info( "BulkAction Step: IOExcecption while reading file " + ExceptionUtils.getFullStackTrace( ioException ) );
			throw new OpsPanelException( "UA-1002", "Could not read file. Please retry. " );
		} finally {
			try {
				if( br != null ) {
					br.close();
				}
			} catch( IOException ioException ) {
				log.info( "BulkAction Step: IO Exception while closing stream. " + ExceptionUtils.getFullStackTrace( ioException ) );
			}
		}
	}

	@Audited(context = "Action", searchId = "suspendWalletRequest.userId", reason = "suspendWalletRequest.reason", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('actionpanel_singleAction') or hasPermission('OPS_IMS_ACTION'))")
	@RequestMapping(value = "/suspendWallet", method = RequestMethod.POST)
	public @ResponseBody GenericResponse suspendWallet(
			@RequestBody(required = false) SuspendWalletRequest suspendWalletRequest) throws InfoPanelException, OpsPanelException {

		suspendWalletRequest.setActionPerformer( getEmailIdFromSession() );
		suspendWalletRequest.setRequestId( getRequestId() );
		if (suspendWalletRequest.getUserIdType() == null || suspendWalletRequest.getUserId() == null
				|| suspendWalletRequest.getReason() == null) {
			log.info("Invalid request ");
			throw new InfoPanelException("MT-5800", "Invalid request. ");
		}

		try {
			actionService.suspendWallet(suspendWalletRequest);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Wallet suspended successfully");
			response.put("userId", suspendWalletRequest.getUserId());

			return GenericUtils.getGenericResponse(response);
		} catch (InfoPanelException e) {
			log.info("Exception while uploading result file to amazon " + e);
			throw new InfoPanelException(e.getErrCode(), e.getErrMessage());
		}
	}


	@Audited(context = "Action", searchId = "enableWalletRequest.userId", reason = "enableWalletRequest.reason", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('actionpanel_singleAction') or hasPermission('OPS_IMS_ACTION'))")
	@RequestMapping(value = "/enableWallet", method = RequestMethod.POST)
	public @ResponseBody GenericResponse enableWallet(
			@RequestBody(required = false) EnableWalletRequest enableWalletRequest) throws InfoPanelException, OpsPanelException {

		enableWalletRequest.setActionPerformer( getEmailIdFromSession() );
		enableWalletRequest.setRequestId( getRequestId() );
		if (enableWalletRequest.getUserIdType() == null || enableWalletRequest.getUserId() == null
				|| enableWalletRequest.getReason() == null) {
			log.error("Invalid request ");
			throw new InfoPanelException("MT-5800", "Invalid request. ");
		}

		try {
			actionService.enableWallet(enableWalletRequest);

			Map<String, Object> response = new HashMap<>();
			response.put("message", "Wallet enabled successfully");
			response.put("userId", enableWalletRequest.getUserId());

			return GenericUtils.getGenericResponse(response);
		} catch (InfoPanelException e) {
			log.info("Exception while uploading result file to amazon " + e);
			throw new InfoPanelException(e.getErrCode(), e.getErrMessage());
		}
	}

	@Audited(context = "Action", searchId = "fraudManagementRequest.dateOfFraudTransaction", reason = "fraudManagementRequest.reason", skipRequestKeys = {}, skipResponseKeys = {"response.data.data.transactions","response.data.file"})
	@PreAuthorize("(hasPermission('OPS_USERPANEL_fraudManagemenet'))")
	@RequestMapping(value = "/fraudManager", method = RequestMethod.POST)
	public @ResponseBody GenericResponse  fraudManageMent(@RequestBody FraudManagementRequest fraudManagementRequest)
			throws InfoPanelException , OpsPanelException{

		FraudManagementResponse fraudManagementResponse = fraudManagementService
				.interceptFraudActions(fraudManagementRequest);

		HashMap<String, Object> response = new HashMap<>();

		if (fraudManagementResponse != null) {

			if (fraudManagementResponse.getStatus().equals("CONFLICTING")) {

				response.put("data", fraudManagementResponse);
				response.put("msg", "There is Confliction in selecting transactions. please select from one of them");
				return GenericUtils.getGenericResponse(response);

			} else if (fraudManagementResponse.getStatus().equalsIgnoreCase("SUCCESS")) {

				return fraudManagementService.performFraudActions(fraudManagementResponse, fraudManagementRequest);

			}

		}

		throw new InfoPanelException("ET-110043","Error in perfroming fraud actions");
	}

	@Audited(context = "Action", searchId = "closeUserAccountRequest.userId", reason = "closeUserAccountRequest.reason", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('actionpanel_singleAction')  or hasPermission('OPS_IMS_ACTION'))")
	@RequestMapping(value = "/closeUserAccount", method = RequestMethod.POST)
	public @ResponseBody GenericResponse closeUserAccount(@RequestBody CloseUserAccountRequest closeUserAccountRequest)
			throws InfoPanelException, OpsPanelException {

		closeUserAccountRequest.setActionPerformer( getEmailIdFromSession() );
		closeUserAccountRequest.setRequestId( getRequestId() );
		
		return GenericUtils.getGenericResponse(actionService.closeUserAccount(closeUserAccountRequest));
	}



	private GenericResponse enableDisableUser(EnableDisableUserRequest enableDisableUserRequest)
			throws InfoPanelException, OpsPanelException {

		EnableDisableUserResponse enableDisableUserResponse;
		enableDisableUserRequest.setActionPerformer( getEmailIdFromSession());
		enableDisableUserRequest.setRequestId( getRequestId() );
		try {
			enableDisableUserResponse = actionService.enableDisableUser(enableDisableUserRequest);
			return GenericUtils.getGenericResponse(enableDisableUserResponse);
		} catch (InfoPanelException e) {
			log.info("Exception while enableDisableUser " + e);
			throw new InfoPanelException(e.getErrCode(), e.getErrMessage());
		}

	}

	private GenericResponse blackWhiteListUser(BlackListWhiteListUserRequest blackListWhiteListUserRequest)
			throws InfoPanelException, OpsPanelException {

		BlackListWhiteListUserResponse blackListWhiteListUserResponse;

		blackListWhiteListUserRequest.setActionPerformer(getEmailIdFromSession());
		blackListWhiteListUserRequest.setRequestId( getRequestId() );
		try {
			blackListWhiteListUserResponse = actionService.blackListWhiteListUser(blackListWhiteListUserRequest);
			return GenericUtils.getGenericResponse(blackListWhiteListUserResponse);
		} catch (InfoPanelException e) {

			log.info("Exception while black list white list user " + e);
			throw new InfoPanelException(e.getErrCode(), e.getErrMessage());
		}

	} 

	@PreAuthorize("(hasPermission('actionpanel_bulkAction'))")
	@Audited(context = "Action", searchId = "", reason = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/getFileActionHistoryForUser", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getFileActionHistoryForUser() throws InfoPanelException , OpsPanelException{
		return GenericUtils.getGenericResponse(fileManagementService.getFileHistoryForUser());
	}

	@PreAuthorize("(hasPermission('actionpanel_bulkAction'))")
	@Audited(context = "Action", searchId = "request.time", reason = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/downloadBulkFile", method = RequestMethod.POST)
	public @ResponseBody GenericResponse downloadBulkFile(@RequestBody DownloadActionBulkFileRequest request)
			throws InfoPanelException {
		return GenericUtils.getGenericResponse(fileManagementService.getDownloadUrl(request));
	}
	
	@PreAuthorize("(hasPermission('actionpanel_singleAction') or hasPermission('OPS_IMS_ACTION'))")
	@Audited(context = "Action", searchId = "request.userId", reason = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@RequestMapping(value = "/viewUserAccountHistory", method = RequestMethod.POST)
	public @ResponseBody GenericResponse viewUserAccountHistory(@RequestBody ViewUserAccountHistoryRequest request)
			throws InfoPanelException {
		return GenericUtils.getGenericResponse(actionService.viewUserHistoryAccount(request));
	}	

	private String getEmailIdFromSession() throws InfoPanelException, OpsPanelException {

		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		return emailId;
	}

	private void deleteFile(String filePath) {
		if (filePath == null)
			return;
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
	}

	private String getRequestId() {
		//TODO test if its working.
		return ( String ) servletRequest.getAttribute( "requestId" );
	}
}
