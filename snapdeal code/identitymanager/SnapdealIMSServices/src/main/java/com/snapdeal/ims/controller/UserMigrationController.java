package com.snapdeal.ims.controller;


import com.snapdeal.ims.activity.annotations.CollectActivity;
import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserWithLinkedStateRequest;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.response.VerifyUserWithLinkedStateResponse;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(RestURIConstants.USER)
public class UserMigrationController extends AbstractController {

	@Qualifier("userMigrationService")
	@Autowired
	private IUserMigrationService userMigrationService;
	
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = "/upgrade/status", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public UserUpgradationResponse userUpgradeStatus(@RequestBody @Valid UserUpgradeByEmailRequest request, 
			BindingResult results,
			HttpServletRequest httpRequest) {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while upgrading user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEmailId(EmailUtils.toLowerCaseEmail(request.getEmailId()));
		return userMigrationService.getUserUpgradeStatus(request, true );
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = "/upgrade", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public UpgradeUserResponse upgradeUser(@RequestBody @Valid UserUpgradeRequest userUpgradeRequest,
			BindingResult results,
			HttpServletRequest httpRequest) 
			throws ValidationException, AuthorizationException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while upgrading user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		userUpgradeRequest.setEmailId(EmailUtils.toLowerCaseEmail(userUpgradeRequest.getEmailId()));
      return userMigrationService.upgradeUser(userUpgradeRequest);
   }
	
	
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = "/upgrade/verify", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public VerifyUpgradeUserResponse verifyUpgradeUser(@RequestBody @Valid VerifyUserUpgradeRequest verifyUserUpgradeRequest,
			BindingResult results,
			HttpServletRequest httpRequest) 
			throws ValidationException, AuthorizationException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while verifying the upgrade user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		verifyUserUpgradeRequest.setEmailId(EmailUtils.toLowerCaseEmail(verifyUserUpgradeRequest.getEmailId()));
		return userMigrationService.verifyUpgradeUser(verifyUserUpgradeRequest);
	}
	
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = "/upgrade/linkedverify", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public VerifyUserWithLinkedStateResponse verifyUserWithLinkedState(@RequestBody @Valid VerifyUserWithLinkedStateRequest request, 
			BindingResult results,
			HttpServletRequest httpRequest) {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while upgrading with linking user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		return userMigrationService.verifyUserWithLinkedState(request);
	}
}