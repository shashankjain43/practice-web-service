package com.snapdeal.ims.controller;

import com.snapdeal.ims.activity.annotations.CollectActivity;
import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.ChangePasswordRequest;
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.ChangePasswordWithLoginResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping(RestURIConstants.USER)
public class PasswordManagementController extends AbstractController {
   @Autowired
   private IUserService userService;

   @Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.FORGOT_PASSWORD_OTP, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public @ResponseBody ForgotPasswordResponse forgotPassword(
			@RequestBody @Valid ForgotPasswordRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException, IMSServiceException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error in ForgotPasswordRequest");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEmailId(EmailUtils.toLowerCaseEmail(request.getEmailId()));
		return userService.forgotPassword(request);
	}
   
   
   @RequestAware
   @Timed
	@Marked
	
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.CHANGE_PASSWORD, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	public @ResponseBody ChangePasswordResponse changePassword(
			@RequestBody @Valid ChangePasswordRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException, IMSServiceException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error in ChangePasswordRequest");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		return userService.changePassword(request);
	}
   @Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.CHANGE_PASSWORD_WITH_LOGIN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	public @ResponseBody ChangePasswordWithLoginResponse changePasswordWithLogin(
			@RequestBody @Valid ChangePasswordRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException, IMSServiceException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error in ChangePasswordRequest");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		return userService.changePasswordWithLogin(request);
	}
   @Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.RESET_PASSWORD, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	public @ResponseBody ResetPasswordResponse resetPassword(
			@RequestBody @Valid ResetPasswordRequest request,
			BindingResult results, HttpServletRequest header)
			throws ValidationException, IMSServiceException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error in ResetPasswordRequest");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		
		return userService.resetPassword(request);

	}
   @Timed
	@Marked
		@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.RESET_PASSWORD_AND_LOGIN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	public @ResponseBody ResetPasswordWithLoginResponse resetPasswordAndLogin(
			@RequestBody @Valid ResetPasswordRequest request,
			BindingResult results, HttpServletRequest header)
			throws ValidationException, IMSServiceException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error in ResetPasswordRequest");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		return userService.resetPasswordAndLogin(request);
	}

}
