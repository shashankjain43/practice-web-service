
package com.snapdeal.ims.controller;

import com.snapdeal.ims.activity.annotations.CollectActivity;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.authorize.annotations.AuthorizeRequest;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.constants.RestURIConstants;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.CloseAccountByEmailRequest;
import com.snapdeal.ims.request.ConfigureUserStateRequest;
import com.snapdeal.ims.request.CreateGuestUserEmailRequest;
import com.snapdeal.ims.request.CreateSocialUserRequest;
import com.snapdeal.ims.request.CreateSocialUserWithMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailMobileRequest;
import com.snapdeal.ims.request.CreateUserEmailRequest;
import com.snapdeal.ims.request.CreateUserMobileGenerateRequest;
import com.snapdeal.ims.request.CreateUserMobileVerifyRequest;
import com.snapdeal.ims.request.CreateUserWithMobileOnlyRequest;
import com.snapdeal.ims.request.GetIMSUserVerificationUrlRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.MobileOnlyRequest;
import com.snapdeal.ims.request.ResendEmailVerificationLinkRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateSocialUserWithMobileResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.GetIMSUserVerificationUrlResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsTokenValidResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.MobileOnlyResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.ResendEmailVerificationLinkResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UpdateMobileNumberResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.ims.utils.MobileNumberUtils;
import com.snapdeal.ims.utils.UserPlatformResourceUtil;
import com.snapdeal.ims.validator.SocialUserDetailsDtoValidator;
import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.commons.codec.binary.Base64;

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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(RestURIConstants.USER)
public class UserManagementController extends AbstractController {

	@Autowired
	private IUserService userService;

	@Autowired
	private UmsMerchantProvider umsMerchantProvider;

	@Autowired
	private ITokenService tokenService;
	@Autowired

	private AuthorizationContext context;

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = "/email", 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public CreateUserResponse createUserWithEmail(
			@RequestBody @Valid CreateUserEmailRequest createUserByEmailRequest,
			BindingResult results, HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException {
		// to do -> handle creation of user by mobile or email
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while creating user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		createUserByEmailRequest.getUserDetailsByEmailDto().setEmailId(
				EmailUtils.toLowerCaseEmail(createUserByEmailRequest
						.getUserDetailsByEmailDto().getEmailId()));
		CreateUserResponse response = userService.createUserByEmail(createUserByEmailRequest);
		return response;
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = "/email/mobile", 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public CreateUserResponse createUserWithEmailAndMobile(
			@RequestBody @Valid CreateUserEmailMobileRequest createUserEmailMobileRequest,
			BindingResult results, HttpServletRequest httpRequest) throws ValidationException,
	AuthorizationException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = 
					IMSRequestExceptionCodes.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while creating user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		createUserEmailMobileRequest.getUserRequestDto().setEmailId(
				EmailUtils.toLowerCaseEmail(createUserEmailMobileRequest
						.getUserRequestDto().getEmailId()));
		CreateUserResponse response = 
				userService.createUserByEmailAndMobile(createUserEmailMobileRequest);
		return response;
	}

	@Timed
	@Marked
	@RequestAware
	@CollectActivity
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = "/guest/create", 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public CreateGuestUserResponse createGuestUserWithEmail(
			@RequestBody @Valid CreateGuestUserEmailRequest createGuestUserByEmailRequest,
			BindingResult results, 
			HttpServletRequest httpRequest)
					throws ValidationException, 
					AuthorizationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while creating guest user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		createGuestUserByEmailRequest.setEmailId(
				EmailUtils.toLowerCaseEmail(createGuestUserByEmailRequest.getEmailId()));
		CreateGuestUserResponse response = 
				userService.createGuestUserByEmail(createGuestUserByEmailRequest);
		return response;
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = "/guest/verify/{code}", 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.GET)
	public VerifyUserResponse verifyUser(
			@PathVariable("code") String code, 
			HttpServletRequest httpRequest)
					throws AuthorizationException {
		if (code == null || code.equalsIgnoreCase("")) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED.errCode(),
					IMSServiceExceptionCodes.CODE_IS_INVALID_OR_EXPIRED.errMsg());
		}
		VerifyUserRequest verifyUserRequest = new VerifyUserRequest();
		verifyUserRequest.setCode(code);
		VerifyUserResponse response = userService.verifyUser(verifyUserRequest);
		return response;
	}

	@CollectActivity
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = "/mobile/generate", 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public OTPResponse createUserWithMobile(
			@RequestBody @Valid CreateUserMobileGenerateRequest createUserMobileGenerateRequest,
			BindingResult results, 
			HttpServletRequest httpRequest)
					throws ValidationException, 
					AuthorizationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while verifying creating user by mobile");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		createUserMobileGenerateRequest=UserPlatformResourceUtil.getUserPlatformResource(createUserMobileGenerateRequest);
		createUserMobileGenerateRequest.getUserRequestDto().setEmailId(
				EmailUtils.toLowerCaseEmail(createUserMobileGenerateRequest
						.getUserRequestDto().getEmailId()));
		OTPResponse response = 
				userService.createUserWithMobile(createUserMobileGenerateRequest);
		return response;
	}

	@CollectActivity
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.IS_MOBILE_ONLY, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public MobileOnlyResponse mobileOnly(@PathVariable("mobileNumber") String mobileNumber,  
			HttpServletRequest httpRequest) throws ValidationException, AuthorizationException{
		MobileNumberUtils.mobileValidator(mobileNumber);
		MobileOnlyRequest mobileOnlyRequest = new MobileOnlyRequest();
		mobileOnlyRequest.setMobileNumber(mobileNumber);
		MobileOnlyResponse mobileOnlyResponse = userService.isMobileOnly(mobileOnlyRequest);
		return mobileOnlyResponse;
	}
	

	@CollectActivity
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GENERATE_OTP_MOBILE_ONLY, 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public OTPResponse createUserWithMobileOnly(
			@RequestBody @Valid CreateUserWithMobileOnlyRequest createUserWithMobileOnlyRequest,
			BindingResult results, 
			HttpServletRequest httpRequest)
					throws ValidationException, 
					AuthorizationException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while verifying creating user by mobile");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		createUserWithMobileOnlyRequest = UserPlatformResourceUtil.getUserPlatformResource(createUserWithMobileOnlyRequest);
		OTPResponse otpResponse = userService.createUserWithMobileOnly(createUserWithMobileOnlyRequest);
		return otpResponse;
	}




	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = "/mobile/verify", 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public CreateUserResponse verifyUserWithMobile(
			@RequestBody @Valid CreateUserMobileVerifyRequest createUserMobileVerifyRequest,
			BindingResult results, 
			HttpServletRequest httpRequest)
					throws ValidationException, 
					AuthorizationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while verifying user by mobile");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		CreateUserResponse response = 
				userService.verifyUserWithMobile(createUserMobileVerifyRequest);
		return response;
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.VERIFY_MOBILE_ONLY, 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public CreateUserResponse verifyUserWithMobileOnly(
			@RequestBody @Valid CreateUserMobileVerifyRequest createUserMobileVerifyRequest,
			BindingResult results, 
			HttpServletRequest httpRequest)
					throws ValidationException, 
					AuthorizationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while verifying user by mobile");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		CreateUserResponse response = 
				userService.verifyUserWithMobileOnly(createUserMobileVerifyRequest);
		return response;
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.SOCIAL, 
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.POST)
	public SocialUserResponse createOrLoginSocialUser(
			@RequestBody @Valid CreateSocialUserRequest request,
			BindingResult results, HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException {
		System.out.println(request);
				if (results.hasErrors() && null != results.getAllErrors()) {
		IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while creating user using social id");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request = UserPlatformResourceUtil.getUserPlatformResource(request);
      log.info("Social Source :" + request.getSocialUserDto().getSocialSrc() + ", for client Id: "
               + umsMerchantProvider.getClientId());
		SocialUserDetailsDtoValidator validator = new SocialUserDetailsDtoValidator();
		validator.validate(request);
		request.getSocialUserDto().setEmailId(
				EmailUtils.toLowerCaseEmail(request.getSocialUserDto().getEmailId()));
		SocialUserResponse response = userService.createSocialUser(request);
		return response;
	}

	@Timed
	@Marked
	@ExceptionMetered
	@AuthorizeRequest
	@CollectActivity
	@RequestMapping(value = RestURIConstants.UPDATE_USER, 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.PUT)
	public GetUserResponse updateUserById(
			@RequestBody @Valid UpdateUserByIdRequest request,
			BindingResult results, HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException,
					IMSServiceException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while updating user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		GetUserResponse response = userService.updateUser(request);
		return response;
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.UPDATE_USER_TOKEN, 
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.PUT)
	public GetUserResponse updateUserByToken(
			@RequestBody @Valid UpdateUserByTokenRequest request,
			BindingResult results, HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while updating user using token");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		GetUserResponse response = userService.updateUserByToken(request);
		return response;
	}

	@Timed
	@Marked
	@RequestAware
	@ExceptionMetered
	@AuthorizeRequest
	@RequestMapping(value = RestURIConstants.GET_USER_EMAIL, 
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.GET)
	public GetUserResponse getUserByEmail(
			@PathVariable("email") String encodeEmailId, HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException {

		String emailId = new String(Base64.decodeBase64(encodeEmailId.getBytes()));
		emailId=EmailUtils.toLowerCaseEmail(emailId);
		validateEmail(emailId);
		final GetUserByEmailRequest request = new GetUserByEmailRequest();
		request.setEmailId(emailId);
		return userService.getUserByEmail(request);
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GET_USER, 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.GET)
	public GetUserResponse getUser(
			@PathVariable("userId") String userId,
			HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException {

		if (StringUtils.isBlank(userId) ) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.USER_ID_IS_BLANK;
			log.error("user Id is blank or null");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}else if(StringUtils.length(userId) >= 127){
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.USER_ID_MAX_LENGTH;
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		final GetUserByIdRequest request = new GetUserByIdRequest();
		request.setUserId(userId);
		return userService.getUser(request);
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GET_USER_MOBILE,
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.GET)
	public GetUserResponse getUserByMobile(
			@PathVariable("mobileNumber") String mobileNumber,
			HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException,IMSServiceException {

		if (StringUtils.isBlank(mobileNumber)) {			 
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK;
			log.error("mobileNumber is blank or null");
			throw new RequestParameterException(code.errCode(), code.errMsg());

		}else if( StringUtils.length(mobileNumber) != CommonConstants.MOBILE_NUMBER_DIGIT){
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.INVALID_MOBILE_NUMBER;
			throw new RequestParameterException(code.errCode(), code.errMsg()); 
		}

		final GetUserByMobileRequest request = new GetUserByMobileRequest();
		request.setMobileNumber(mobileNumber);
		return userService.getUserByMobile(request);

	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GET_USER_TOKEN,
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.GET)
	public GetUserResponse getUserByToken(
			@PathVariable("token") String token, HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException {

		if (StringUtils.isBlank(token)) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.TOKEN_IS_BLANK;
			log.error("token is blank or null");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}else if(StringUtils.length(token) >= 255){
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.TOKEN_MAX_LENGTH;
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}else if(!token.matches("[a-zA-Z0-9-_]+")){
			IMSAuthenticationExceptionCodes code = IMSAuthenticationExceptionCodes.INVALID_TOKEN;
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		final GetUserByTokenRequest request = new GetUserByTokenRequest();
		request.setToken(token);
		return userService.getUserByToken(request);
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.UPDATE_MOBILE_NUMBER, 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.PUT)
	public UpdateMobileNumberResponse updateMobileNumber(
			@RequestBody @Valid UpdateMobileNumberRequest request,
			BindingResult results, HttpServletRequest httpRequest)
					throws Exception {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while upating mobile number of user");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		UpdateMobileNumberResponse response = userService.updateMobileNumber(request);
		return response;
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.IS_USER_EXIST,
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.GET)
	public IsUserExistResponse isUserExist(@PathVariable("userId") String userId,
			HttpServletRequest httpServletRequest)
					throws ValidationException, IMSServiceException {

		if (StringUtils.isBlank(userId)) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.USER_ID_IS_BLANK;
			log.error("userId is blank or null");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}else if(StringUtils.length(userId) >= 127){
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.USER_ID_MAX_LENGTH;
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		final IsUserExistRequest request = new IsUserExistRequest();
		request.setUserId(userId);

		return userService.isUserExist(request);
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.IS_MOBILE_EXIST,
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.GET)
	public IsVerifiedMobileExistResponse isVerifiedMobileExist(@PathVariable("mobileNumber") String mobileNumber,
			HttpServletRequest httpServletRequest)
					throws ValidationException, IMSServiceException {

		if (StringUtils.isBlank(mobileNumber)) {	  
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.MOBILE_NUMBER_IS_BLANK;
			log.error("mobile number is blank or null");
			throw new RequestParameterException(code.errCode(), code.errMsg());

		}else if( StringUtils.length(mobileNumber) != CommonConstants.MOBILE_NUMBER_DIGIT){
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.INVALID_MOBILE_NUMBER;
			throw new RequestParameterException(code.errCode(), code.errMsg()); 
		}

		IsVerifiedMobileExistRequest request = new IsVerifiedMobileExistRequest();
		request.setMobileNumber(mobileNumber);
		return userService.isMobileExist(request);
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.IS_EMAIL_EXIST,
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.GET)
	public IsEmailExistResponse isEmailExist(@PathVariable("emailId") String encodeEmailId,
			HttpServletRequest httpServletRequest)
					throws ValidationException, IMSServiceException {

		String emailId = new String(Base64.decodeBase64(encodeEmailId
				.getBytes()));
		emailId=EmailUtils.toLowerCaseEmail(emailId);
		validateEmail(emailId);
		final IsEmailExistRequest request = new IsEmailExistRequest();
		request.setEmailId(emailId);
		return userService.isEmailExist(request);
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.ACTIVATE_STATE,
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.PUT)
	public @ResponseBody ConfigureUserStateResponse configureUserState(
			@RequestBody  @Valid ConfigureUserStateRequest request,
			BindingResult results,
			HttpServletRequest httpServletRequest) throws ValidationException, IMSServiceException {

		if( results.hasErrors() && results.getAllErrors() != null ){		   
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error(" invalid request error in ChangeActivationStateRequest" );
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEmailId(EmailUtils.toLowerCaseEmail(request.getEmailId()));
		return userService.configureUserState(request);

	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.RESEND_VERIFY_EMAIL, 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public  ResendEmailVerificationLinkResponse resendEmailVerificationLink(
			@RequestBody @Valid ResendEmailVerificationLinkRequest request,
			BindingResult results, HttpServletRequest httpRequest)
					throws ValidationException, AuthorizationException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while Resending Verification Email Link");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request.setEmailId(EmailUtils.toLowerCaseEmail(request.getEmailId()));		   
		ResendEmailVerificationLinkResponse response = userService.resendEmailVerificationLink(request);
		return response;
	}
	@Timed
	@Marked
	@RequestAware	
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.CREATE_SOCIAL_USER_WITH_MOBILE, 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public CreateSocialUserWithMobileResponse createSocialUserWithMobile(
			@RequestBody @Valid CreateSocialUserWithMobileRequest request,
			BindingResult results, HttpServletRequest httpRequest) 
					throws ValidationException, AuthorizationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while Creating social  user with mobile");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		request = UserPlatformResourceUtil.getUserPlatformResource(request);
		request.getSocialUserDto().setEmailId(
				EmailUtils.toLowerCaseEmail(request.getSocialUserDto()
						.getEmailId()));
		return userService.createSocialUserWithMobile(request);
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.VERIFY_SOCIAL_USER_WITH_MOBILE, 
	produces = RestURIConstants.APPLICATION_JSON, 
	method = RequestMethod.POST)
	public CreateUserResponse verifySocialUserWithMobile(@RequestBody @Valid CreateUserMobileVerifyRequest request,
			BindingResult results, HttpServletRequest httpRequest) 
					throws ValidationException, AuthorizationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while verifying social  user with mobile");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		return userService.verifySocialUserWithMobile(request);  
	}

	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.VALIDATE_TOKEN,
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.GET)
	public IsTokenValidResponse validateToken(@PathVariable("token") String token,
			HttpServletRequest httpServletRequest)
					throws ValidationException, IMSServiceException {
		if (StringUtils.isBlank(token)) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.TOKEN_IS_BLANK;
			log.error("token is blank or null");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}else if(StringUtils.length(token) >= 255){
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.TOKEN_MAX_LENGTH;
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}else if(!token.matches("[a-zA-Z0-9-_]+")){
			IMSAuthenticationExceptionCodes code = IMSAuthenticationExceptionCodes.INVALID_TOKEN;
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		TokenRequest request = new TokenRequest() ;
		request.setToken(token);
		request.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
		IsTokenValidResponse response = new IsTokenValidResponse();
		response.setTokenValid(tokenService.isTokenValid(request));
		return response;	   
	}
	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@ExceptionMetered
	@RequestMapping(value = RestURIConstants.GET_IMS_USER_VERIFICATION_URL, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetIMSUserVerificationUrlResponse getIMSUserVerificationUrl(
			@RequestBody @Valid GetIMSUserVerificationUrlRequest getIMSUserVerificationUrlRequest,
			BindingResult results, HttpServletRequest httpRequest) throws ValidationException,
	AuthorizationException {
		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.valueOf(results.getAllErrors()
					.get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while getting user verification Url");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}
		getIMSUserVerificationUrlRequest.setEmail(EmailUtils
				.toLowerCaseEmail(getIMSUserVerificationUrlRequest.getEmail()));
		return userService.getIMSUserVerificationUrl(getIMSUserVerificationUrlRequest);
	}


	@Timed
	@Marked
	@RequestAware
	@AuthorizeRequest
	@CollectActivity
	@RequestMapping(value = RestURIConstants.GET_USER_EMAIL,
	produces = RestURIConstants.APPLICATION_JSON,
	method = RequestMethod.DELETE)
	public @ResponseBody CloseAccountResponse closeAccount(@PathVariable("email") String encodeEmailId,
			HttpServletRequest httpRequest) 
					throws ValidationException, AuthorizationException {

		String emailId = new String(Base64.decodeBase64(encodeEmailId.getBytes()));
		emailId = EmailUtils.toLowerCaseEmail(emailId);
		validateEmail(emailId);

		final CloseAccountByEmailRequest request = new CloseAccountByEmailRequest();
		request.setEmailId(emailId);
		return userService.closeAccount(request);
	}


	/**
	 * Validate email and also mandatory.
	 */
	private void validateEmail(String email) {

		if (StringUtils.isNotBlank(email)) {
			if (email.length() > 128) {
				IMSRequestExceptionCodes code = IMSRequestExceptionCodes.EMAIL_MAX_LENGTH;
				throw new RequestParameterException(code.errCode(),
						code.errMsg());
			}
			if (!email.matches(CommonConstants.EMAIL_REGEX)) {
				IMSRequestExceptionCodes code = IMSRequestExceptionCodes.EMAIL_FORMAT_INCORRECT;
				throw new RequestParameterException(code.errCode(),
						code.errMsg());
			}

		} else {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes.EMAIL_IS_BLANK;
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

	}
}
