package com.snapdeal.ims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.ims.activity.annotations.CollectActivity;
import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.enums.GetConfigurationDetails;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.IsOTPValidRequest;
import com.snapdeal.ims.request.ResendOTPRequest;
import com.snapdeal.ims.request.VerifyOTPRequest;
import com.snapdeal.ims.response.ConfigurationDetailsResponse;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.IsOTPValidResponse;
import com.snapdeal.ims.response.VerifyOTPResponse;
import com.snapdeal.ims.service.IConfigurationDetails;
import com.snapdeal.ims.service.IOTPServiceGeneration;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;

@Slf4j	
@RestController
@RequestMapping(value = RestURIConstants.OTP)
public class OTPManagementController extends AbstractController {

	@Autowired
	private IOTPServiceGeneration otpService;
	
	@Autowired
	private IConfigurationDetails configurationDetails ;

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GENERATE_OTP, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GenerateOTPResponse generateOTP(
			@Valid @RequestBody GenerateOTPRequest request,
			BindingResult results, HttpServletRequest httpRequest) {

		if (results.hasErrors() && null != results.getAllErrors()) {

			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());

			log.error("Invalid Request Error occured while sending OTP to the user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEmailId(EmailUtils.toLowerCaseEmail(request.getEmailId()));
		return otpService.generateAndSendOTP(request);
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.RESEND_OTP, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GenerateOTPResponse reSendOTP(
			@Valid @RequestBody ResendOTPRequest request,
			BindingResult results, HttpServletRequest httpRequest) {

		if (results.hasErrors() && results.getAllErrors() != null) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());

			log.error("Invalid Request Error occured while resending the otp to the user.");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		return otpService.reSendOTP(request);
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.VALID_OTP, 
					produces = RestURIConstants.APPLICATION_JSON, 	
					method = RequestMethod.GET)
	public IsOTPValidResponse isValidOTP(@PathVariable("otpId") String otpId,
									     @PathVariable("otp") String otp,
									     HttpServletRequest httpRequest) {

		IsOTPValidRequest isOTPValidRequest = new IsOTPValidRequest();
		isOTPValidRequest.setOtp(otp);
		isOTPValidRequest.setOtpId(otpId);
		return otpService.isOTPValid(isOTPValidRequest);
	}
	
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.FETCH_CONFIGURATION_DETAILS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public ConfigurationDetailsResponse getConfigurationDetails(
			@PathVariable("configuration") String configurationType,
			HttpServletRequest httpRequest) throws ValidationException,
			IMSServiceException {
		
		GetConfigurationDetails getConfigurationDetailsOf = null;
		try{
			getConfigurationDetailsOf = GetConfigurationDetails
					.valueOf(configurationType);
		}catch(Exception e){
			log.debug("exception occured while get configurationDettails" + e);
			throw new InternalServerException(
					IMSRequestExceptionCodes.INVALID_CONFIGURATION_FIELD.errCode(),
					IMSRequestExceptionCodes.INVALID_CONFIGURATION_FIELD.errMsg());
		}
		
		return configurationDetails.getConfigurationDetails(getConfigurationDetailsOf) ;
	}
	
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.VERIFY_OTP, 
					produces = RestURIConstants.APPLICATION_JSON, 
					method = RequestMethod.POST)
	public @ResponseBody VerifyOTPResponse verifyOTP(
			@Valid @RequestBody VerifyOTPRequest request,
			BindingResult results, HttpServletRequest httpRequest){
		
		if (results.hasErrors() && null != results.getAllErrors()) {

			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());

			log.error("Invalid Request Error occured while verifying OTP for the user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		return otpService.verifyOTP(request);
	}
}
