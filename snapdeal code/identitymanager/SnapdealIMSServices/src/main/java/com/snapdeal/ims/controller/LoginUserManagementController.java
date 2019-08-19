package com.snapdeal.ims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ims.activity.annotations.CollectActivity;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.request.LoginUserRequest;
import com.snapdeal.ims.request.LoginWithTokenRequest;
import com.snapdeal.ims.request.LoginWithTransferTokenRequest;
import com.snapdeal.ims.request.SendForgotPasswordLinkRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.request.VerifyAndResetPasswordRequest;
import com.snapdeal.ims.response.GetTransferTokenResponse;
import com.snapdeal.ims.response.LoginUserResponse;
import com.snapdeal.ims.response.SendForgotPasswordLinkResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.ILoginUserService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.token.service.ITransferTokenService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

@RestController		
@Slf4j
@SuppressWarnings("deprecation")
public class LoginUserManagementController extends AbstractController {
	@Autowired
	ILoginUserService loginUserService;
	@Autowired
	private AuthorizationContext context;
	@Autowired
	private ITokenService tokenService;
   @Autowired
   private ITransferTokenService transferTokenService;

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.SIGNIN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public @ResponseBody LoginUserResponse signIn(
			@RequestBody @Valid LoginUserRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException, AuthorizationException,
			TransportException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while sign in of user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		//  Putting check that userAgent and machineIdentifier are not null.
		if (StringUtils.isBlank(context.get(IMSRequestHeaders.USER_AGENT
				.toString()))
				|| (StringUtils.isBlank(context
						.get(IMSRequestHeaders.USER_MACHINE_IDENTIFIER
								.toString())))) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.MACHINE_IDENTIFIER_OR_USER_AGENT_IS_BLANK;
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEmailId(EmailUtils.toLowerCaseEmail(request.getEmailId()));
		LoginUserResponse response = loginUserService.loginUser(request);
		return response;
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.SIGNIN_TOKEN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public @ResponseBody LoginUserResponse signInWithToken(
			@RequestBody @Valid LoginWithTokenRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException, AuthorizationException,
			TransportException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while sign in using token");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		LoginUserResponse response = loginUserService
				.loginUserWithToken(request);
		return response;
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.SIGNOUT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public @ResponseBody SignoutResponse signout(
			@RequestBody @Valid SignoutRequest request, BindingResult results,
			HttpServletRequest httpRequest) throws ValidationException,
			AuthorizationException, TransportException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while sign out of a user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		SignoutResponse response = loginUserService.signout(request, null);
		return response;
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@RequestMapping(value = RestURIConstants.FORGOT_PASSWORD, produces = RestURIConstants.APPLICATION_JSON, method = {RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody SendForgotPasswordLinkResponse sendForgotPasswordLink(@RequestBody @Valid SendForgotPasswordLinkRequest request,
								BindingResult results,
								HttpServletRequest httpRequest) {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while sending email verification link.");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEmail(EmailUtils.toLowerCaseEmail(request.getEmail()));
		SendForgotPasswordLinkResponse forgotPasswordEmailResponse = loginUserService.sendForgotPasswordLink(request.getEmail());
		return forgotPasswordEmailResponse;
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.FORGOT_PASSWORD_VERIFY, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public @ResponseBody VerifyUserResponse verifyUserAndResetPassword(@RequestBody @Valid VerifyAndResetPasswordRequest request,
								BindingResult results,
								HttpServletRequest httpRequest) {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while reset password.");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		VerifyUserResponse verifyUserResponse = loginUserService.verifyUserAndResetPassword(request);
		return verifyUserResponse;
	}
	@Timed
	@Marked
	@RequestAware
   @CollectActivity
   @AuthorizeRequest
   @ExceptionMetered
   @RequestMapping(value = RestURIConstants.GET_TRANSFER_TOKEN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
   public @ResponseBody GetTransferTokenResponse getTransferToken(
            @RequestBody @Valid GetTransferTokenRequest request, BindingResult results,
            HttpServletRequest httpRequest) throws AuthorizationException, ValidationException {

      if (results.hasErrors() && null != results.getAllErrors()) {
         IMSRequestExceptionCodes code = IMSRequestExceptionCodes.valueOf(results.getAllErrors()
                  .get(0).getDefaultMessage());
         throw new RequestParameterException(code.errCode(), code.errMsg());
      }
      
      String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());

      GetTransferTokenResponse response = transferTokenService.getTransferToken(request, clientId);
      return response;
   }
   @Timed
	@Marked
	@RequestAware
   @AuthorizeRequest
   @CollectActivity
   @ExceptionMetered
   @RequestMapping(value = RestURIConstants.SIGNIN_TRANSFER_TOKEN, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
   public @ResponseBody LoginUserResponse signInWithTransferToken(
            @RequestBody @Valid LoginWithTransferTokenRequest request, BindingResult results,
            HttpServletRequest httpRequest) throws ValidationException, AuthorizationException,
            TransportException {
      if (results.hasErrors() && null != results.getAllErrors()) {
         IMSRequestExceptionCodes code = IMSRequestExceptionCodes.valueOf(results.getAllErrors()
                  .get(0).getDefaultMessage());
         log.error("Invalid Request Error occured while sign in using transfer token");
         throw new RequestParameterException(code.errCode(), code.errMsg());
      }
      LoginUserResponse response = loginUserService.loginUserWithTransferToken(request);
      return response;
   }
	
}
