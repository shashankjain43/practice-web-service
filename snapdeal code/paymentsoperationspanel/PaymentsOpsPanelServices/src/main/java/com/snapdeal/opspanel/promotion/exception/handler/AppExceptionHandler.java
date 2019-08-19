package com.snapdeal.opspanel.promotion.exception.handler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.bulkprocess.exception.BulkProcessorException;
import com.snapdeal.opspanel.bulk.exception.BulkComponentException;
import com.snapdeal.opspanel.clientintegrationscomponent.exception.ClientIntegrationException;
import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.exception.GenericError;
import com.snapdeal.opspanel.promotion.exception.OneCheckServiceException;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.payments.pms.exceptions.ProfileManagementException;
import com.snapdeal.payments.roleManagementClient.exceptions.ServiceException;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.vanila.exception.MerchantException;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler { 

	@Autowired
	HttpServletRequest servRequest;

	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleException(Exception ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);
		String finalMsg = "Error Occured, please try after sometime.";
		
//		if (ex.getMessage() == null || ex.getMessage().trim().equals("")) {
//			finalMsg = "Error Occured, please try after sometime.";
//		} else {
//			finalMsg = ex.getMessage();
//		}

		appResponse.setError(new GenericError("MT-1002", finalMsg + " request ID :" + getRequestId()));
		log.info("Generic exception catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	@ExceptionHandler({ WalletServiceException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleWalletServiceException(WalletServiceException ex,
			HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);

		appResponse.setError(new GenericError(ex.getErrCode(), ex.getErrMessage() + " Source : " + ex.getSource() + " request ID :" + getRequestId(),
				ex.getSource()));
		log.info("Wallet Service Exception catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler({ OneCheckServiceException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleOneCheckServiceException(OneCheckServiceException ex,
			HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);

		appResponse.setError(new GenericError(ex.getErrCode(), ex.getErrMessage() + " Source : " + ex.getSource() + " request ID :" + getRequestId(),
				ex.getSource()));
		log.info("One Check Service Exception catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	@ExceptionHandler({ InfoPanelException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleInfoServiceException(InfoPanelException ex,
			HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);
		appResponse.setError(new GenericError(ex.getErrCode(), ex.getErrMessage() + " Source : " + ex.getSource() + " request ID :" + getRequestId(),
				ex.getSource()));
		log.info("InfoPanelException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	@ExceptionHandler({ ClientViewerException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleClientViewerException(ClientViewerException ex,
			HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);
		appResponse.setError(new GenericError(ex.getErrCode(), ex.getErrMessage() + " Source : " + ex.getSource() + " request ID :" + getRequestId(),
				ex.getSource()));
		log.info("ClientViewerException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	@ExceptionHandler({ ServiceException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleServiceException(ServiceException ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);
		appResponse.setError(new GenericError(ex.getErrCode(), ex.getMessage() + " Source : " + "RMS" + " request ID :" + getRequestId(), "RMS"));
		log.info("ServiceException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	@ExceptionHandler({ RoleMgmtException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleRoleMgmtException(RoleMgmtException ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);
		appResponse.setError(
				new GenericError(ex.getErrorCode().getErrorCode(), ex.getMessage() + " Source : " + "RMS" + " request ID :" + getRequestId(), "RMS"));
		log.info("ServiceException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	@ExceptionHandler({ ClientIntegrationException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleDisbursementEngineException(ClientIntegrationException ex,
			HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);
		appResponse.setError(new GenericError(ex.getErrorCode().toString(),
				ex.getErrorMessage() + " Source : " + "ClientIntegration" + " request ID :" + getRequestId(), "ClientIntegration"));
		log.info("ServiceException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler({ BulkComponentException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleBulkException(BulkComponentException ex,
			HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);
		appResponse.setError(new GenericError(ex.getErrorCode().toString(),
				ex.getErrorMsg() + " Source : " + "BulkComponent" + " request ID :" + getRequestId(), "BulkComponent"));
		log.info("ServiceException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	@ExceptionHandler({ MerchantException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleMerchantException(MerchantException ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);

		appResponse.setError(new GenericError(ex.getErrCode(), ex.getErrMessage() + " Source : " + ex.getSource() + " request ID :" + getRequestId(),
				ex.getSource()));
		log.info("MerchantException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	@ExceptionHandler({ OpsPanelException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleOpsPanelException(OpsPanelException ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);

		appResponse.setError(new GenericError(ex.getErrCode(), ex.getErrMessage() + " Source : " + ex.getSource() + " request ID :" + getRequestId(),
				ex.getSource()));
		log.info("OpsPanelException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}

	//Catching Exception for MOB Service Exception
	@ExceptionHandler({ com.snapdeal.mob.exception.ServiceException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleMOBException(com.snapdeal.mob.exception.ServiceException ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);

		appResponse.setError(new GenericError(ex.getErrCode(), ex.getErrMsg() + " Source : " + "MOB" + " request ID :" + getRequestId(),
				"MOB"));
		log.info("MOB Exception catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}
	
	@ExceptionHandler({ BulkProcessorException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleMOBException(BulkProcessorException ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);

		appResponse.setError(new GenericError(ex.getErrorCode(), ex.getMessage() + " Source : " + "BulkProcessor" + " request ID :" + getRequestId(),
				"BulkProcessor"));
		log.info("BulkProcessorException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}
	
	
	@ExceptionHandler({ ProfileManagementException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleProfileManagementException(ProfileManagementException ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);

		appResponse.setError(new GenericError(ex.getErrorCode().toString(), ex.getMessage() + " Source : " + "ProfileManagement" + " request ID :" + getRequestId(),
				"ProfileManagement"));
		log.info("ProfileManagementException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}
	
	
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseBody
	public ResponseEntity<GenericResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletResponse response) {
		GenericResponse appResponse = new GenericResponse();
		appResponse.setData(null);

		StringBuilder completeErrorMessage = new StringBuilder("");
		
		BindingResult bindingResult = ex.getBindingResult();
		List<ObjectError> allErrors = new ArrayList<ObjectError>();
		if(bindingResult != null){
			allErrors = bindingResult.getAllErrors();
		}
		if(allErrors != null){
			int errorSize = allErrors.size();
			for(int i=0; i< errorSize; i++){
				completeErrorMessage.append((i+1) + ". " + allErrors.get(i).getDefaultMessage() + ",  ");
			}
		}
		appResponse.setError(new GenericError(" ", completeErrorMessage + " request ID :" + getRequestId(),
				"MethodArgumentNotValidException"));
		log.info("MethodArgumentNotValidException catched in AppExceptionHandler " + ExceptionUtils.getFullStackTrace(ex));
		return new ResponseEntity<GenericResponse>(appResponse, HttpStatus.OK);
	}
	
	private String getRequestId() {
		return (String) servRequest.getAttribute("requestId");
	}
}
