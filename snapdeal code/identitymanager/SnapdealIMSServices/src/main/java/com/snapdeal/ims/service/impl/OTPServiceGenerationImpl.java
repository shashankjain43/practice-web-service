package com.snapdeal.ims.service.impl;

import java.text.MessageFormat;
import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.OTPRequestChannel;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.request.GenerateOTPRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsOTPValidRequest;
import com.snapdeal.ims.request.IsOTPVerifiedServiceRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.ResendOTPRequest;
import com.snapdeal.ims.request.ResendOTPServiceRequest;
import com.snapdeal.ims.request.VerifyOTPRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.response.GenerateOTPResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsOTPValidResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.VerifyOTPResponse;
import com.snapdeal.ims.service.IOTPServiceGeneration;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

/**
 * 
 * @author abhishek
 *
 */
@Slf4j
@Service
public class OTPServiceGenerationImpl implements IOTPServiceGeneration {

	private static final EnumSet<OTPPurpose> OTP_PURPOSE_DO_NOT_CHECK_MOBILE_NUMBER = EnumSet.of(
			OTPPurpose.UPGRADE_USER,
			OTPPurpose.UPDATE_MOBILE,
			OTPPurpose.WALLET_PAY,
			OTPPurpose.WALLET_LOAD,
			OTPPurpose.WALLET_ENQUIRY);

	private static final EnumSet<OTPPurpose> OTP_PURPOSE_SEND_SKIP_TOKEN_NULL = EnumSet.of(
			OTPPurpose.WALLET_PAY,
			OTPPurpose.WALLET_LOAD,
			OTPPurpose.WALLET_ENQUIRY,
			OTPPurpose.LOGIN_WITH_MOBILE_OTP);

	@Autowired
	private IOTPService service;

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private AuthorizationContext context;

	@Autowired
	IActivityDataService tokenValidationService;

	@Qualifier("IMSService")
	@Autowired
	private IUMSService imsService;

	@Autowired
	private IUserIdCacheService userIdCacheService ;

	@Autowired
	private UmsMerchantProvider merchantProvider;

	@Autowired
	private IUserService userService;


	@Override
	@Timed
	@Marked
	public GenerateOTPResponse generateAndSendOTP(GenerateOTPRequest request) {
		// in case of upgrade user, check if user already exists in OC with
		// verified mobile.
		if (request.getPurpose() == OTPPurpose.UPGRADE_USER
				|| request.getPurpose() == OTPPurpose.MOBILE_VERIFICATION
				|| request.getPurpose() == OTPPurpose.UPDATE_MOBILE) {
			IsVerifiedMobileExistRequest mobileRequest = new IsVerifiedMobileExistRequest();
			mobileRequest.setMobileNumber(request.getMobileNumber());
			IsVerifiedMobileExistResponse mobileExist = imsService
					.isMobileExist(mobileRequest);
			if (null != mobileExist && mobileExist.isExist()) {
				GetUserByMobileRequest getUserByMobilerequest = new GetUserByMobileRequest();
				getUserByMobilerequest.setMobileNumber(request
						.getMobileNumber());
				GetUserResponse response = imsService
						.getUserByMobile(getUserByMobilerequest);
				String email = response.getUserDetails().getEmailId();
				String maskedEmail = EmailUtils.maskEmail(email);
				throw new IMSServiceException(
						IMSMigrationExceptionCodes.MOBILE_ALREADY_REGISTERED
						.errCode(),
						MessageFormat
						.format(IMSMigrationExceptionCodes.MOBILE_ALREADY_REGISTERED
								.errMsg(), maskedEmail));
			}
		}

		final GenerateOTPServiceRequest generateOTPRequest = createGenerateOTPRequest(request);
		final OTPServiceResponse otpResponse = service
				.generateOTP(generateOTPRequest);
		return prepareResponse(otpResponse);
	}


	@Override
	@Timed
	@Marked
	public GenerateOTPResponse reSendOTP(ResendOTPRequest request) {

		if(request.getOtpChannel()==null||StringUtils.isBlank(request.getOtpChannel().getOTPRequestChannel()))
		{
			request.setOtpChannel(OTPRequestChannel.THROUGH_SMS);
		}
		final ResendOTPServiceRequest resendRequest = createResendOTPRequest(request);
		final OTPServiceResponse otpResponse = service.resendOTP(resendRequest);
		return prepareResponse(otpResponse);
	}


	@Override
	@Timed
	@Marked
	public IsOTPValidResponse isOTPValid(IsOTPValidRequest isOTPValidRequest) {

		IsOTPVerifiedServiceRequest request = new IsOTPVerifiedServiceRequest();
		request.setClientId(context.get(IMSRequestHeaders.CLIENT_ID
				.toString()));
		request.setOtp(isOTPValidRequest.getOtp());
		request.setOtpId(isOTPValidRequest.getOtpId());

		return service.isOTPValid(request) ;
	}

	@Timed
	@Marked
	public VerifyOTPResponse verifyOTP(VerifyOTPRequest request) {
		VerifyOTPServiceRequest verifyOTPServiceRequest = createVerifyOTPServiceRequest(request) ;
		VerifyOTPServiceResponse response = service.verifyOTP(verifyOTPServiceRequest) ;
		VerifyOTPResponse verifyOTPResponse = new VerifyOTPResponse();
		if (response.getStatus().equalsIgnoreCase(OtpConstants.STATUS_SUCCESS)) {
			verifyOTPResponse.setOtpVerified(true);
		}else{
			verifyOTPResponse.setOtpVerified(false) ;
		}
		log.debug(verifyOTPResponse.toString());
		return verifyOTPResponse ;
	}

	private VerifyOTPServiceRequest createVerifyOTPServiceRequest(VerifyOTPRequest request){
		VerifyOTPServiceRequest verifyOTPServiceRequest = new VerifyOTPServiceRequest();
		verifyOTPServiceRequest.setOtp(request.getOtp());
		verifyOTPServiceRequest.setOtpId(request.getOtpId());
		verifyOTPServiceRequest.setOtpPurpose(request.getOtpPurpose());
		verifyOTPServiceRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID
				.toString()));

		if(OTP_PURPOSE_SEND_SKIP_TOKEN_NULL.contains(request.getOtpPurpose())){
			verifyOTPServiceRequest.setToken(null);
		}else{
			verifyOTPServiceRequest.setToken(request.getToken());
		}

		return verifyOTPServiceRequest ;
	}

	private GenerateOTPServiceRequest createGenerateOTPRequest(
			GenerateOTPRequest request) {

		GenerateOTPServiceRequest generateOTPRequest = new GenerateOTPServiceRequest();
		generateOTPRequest.setMerchant(getMerchantEnum());
		generateOTPRequest.setOtpType(request.getPurpose());
		generateOTPRequest.setEmailId(request.getEmailId());
		generateOTPRequest.setSendOtpBy(SendOTPByEnum.FREECHARGE);
		generateOTPRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID.toString()));

		if(OTP_PURPOSE_SEND_SKIP_TOKEN_NULL.contains(request.getPurpose())){
			generateOTPRequest.setToken(null);
		}else{
			generateOTPRequest.setToken(request.getToken());
		}

      // Not setting mobile number when otp purpose is LINK_ACCOUNT.
      // This purpose is used, for email verification so otp should not be sent
      // to mobile.
      // For validate token, setting migration flow as true to skip fraud check.
		if (OTPPurpose.LINK_ACCOUNT != request.getPurpose()) {
			generateOTPRequest.setMobileNumber(request.getMobileNumber());
		}
		String userId = getUserId(request);
		UserDetailsDTO dto = null;
		if (!(OTPPurpose.LOGIN_WITH_EMAIL_OTP == request.getPurpose()
				|| OTPPurpose.LOGIN_WITH_MOBILE_OTP == request.getPurpose()
				|| OTPPurpose.SIGNUP_WITH_OTP == request.getPurpose())) {
			dto = getUSerDetailsDTO(request, userId);
			if (OTPPurpose.LINK_ACCOUNT == request.getPurpose()) {
				// setting for link account.
				generateOTPRequest.setEmailId(dto.getEmailId());
			}
		} else {
			GetUserByIdRequest getUserbyIdRequest = new GetUserByIdRequest();
			getUserbyIdRequest.setUserId(userId);
			GetUserResponse response = imsService.getUser(getUserbyIdRequest);
			if (response != null && response.getUserDetails() != null) {
				dto = response.getUserDetails();
			} else {
				throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
			}
		}
		isMobileAndEmailMatches(request, dto);
		generateOTPRequest.setUserId(userId);
		
      String displayName = StringUtils.isNotBlank(dto.getFirstName()) ? dto.getFirstName() : dto.getDisplayName() ; 
      generateOTPRequest.setName(StringUtils.capitalize(StringUtils.lowerCase(displayName)));
      return generateOTPRequest;
	}

	private String getUserId(GenerateOTPRequest request){
		if (OTPPurpose.LINK_ACCOUNT == request.getPurpose()) {
			tokenValidationService.validateToken(request.getToken(), true);
			return  tokenService.getUserIdByToken(request.getToken(), true);
		}else if(OTP_PURPOSE_SEND_SKIP_TOKEN_NULL.contains(request.getPurpose())){
			GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest() ;
			getUserByMobileRequest.setMobileNumber(request.getMobileNumber());
			GetUserResponse getUserResponseByMobile = imsService.getUserByMobile(getUserByMobileRequest);
			return getUserResponseByMobile.getUserDetails().getUserId() ;
		}else if(OTPPurpose.LOGIN_WITH_EMAIL_OTP.equals(request.getPurpose())){
			GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
			getUserByEmailRequest.setEmailId(request.getEmailId());
			GetUserResponse getUserResponseByEmail = imsService.getUserByEmail(getUserByEmailRequest);
			return getUserResponseByEmail.getUserDetails().getUserId();
		}else {
			tokenValidationService.validateToken(request.getToken());
			return tokenService.getUserIdByToken(request.getToken());
		}
	}

	private UserDetailsDTO getUSerDetailsDTO(GenerateOTPRequest request,String userId){
		GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest() ;
		getUserByIdRequest.setUserId(userId);
		GetUserResponse getUserResponse =  null;
		// If otp purpose is LINK_ACCOUNT, fetching from IMS Service impl.
		if (OTPPurpose.LINK_ACCOUNT == request.getPurpose()) {
			getUserResponse = userService
					.getUserFromIMSInCaseOfLinkUserWithOcPassword(getUserByIdRequest);
		} else {
			getUserResponse = userService.getUser(getUserByIdRequest) ;
		}
		return  getUserResponse.getUserDetails() ;
	}
	private void isMobileAndEmailMatches(GenerateOTPRequest request,UserDetailsDTO dto){
		if( StringUtils.isNotBlank(request.getMobileNumber()) 
				&& StringUtils.isNotBlank(dto.getMobileNumber())){

			if( !OTP_PURPOSE_DO_NOT_CHECK_MOBILE_NUMBER.contains(request.getPurpose())
					&&	!request.getMobileNumber().equals(dto.getMobileNumber())){

				throw new IMSServiceException(
						IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errCode(),
						IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errMsg()) ;
			}
		}
		
		if (StringUtils.isNotBlank(request.getEmailId())
				&& StringUtils.isNotBlank(dto.getEmailId())) {
			if (!dto.getEmailId().equalsIgnoreCase(request.getEmailId())) {
				throw new IMSServiceException(
						IMSServiceExceptionCodes.WRONG_EMAIL_ID.errCode(),
						IMSServiceExceptionCodes.WRONG_EMAIL_ID.errMsg());

			}
		}
		if(OTPPurpose.UPGRADE_USER == request.getPurpose() 
				&& StringUtils.isNotBlank(dto.getEmailId())){
			IsEmailExistRequest isEmailExist = new IsEmailExistRequest()  ;
			isEmailExist.setEmailId(dto.getEmailId());
			IsEmailExistResponse response = imsService.isEmailExist(isEmailExist) ;
			if(response.isExist()){
				throw new IMSServiceException(
						IMSServiceExceptionCodes.EMAIL_IS_ALREADY_UPGRRADED.errCode(),
						IMSServiceExceptionCodes.EMAIL_IS_ALREADY_UPGRRADED.errMsg());
			}
		}
	}
	private ResendOTPServiceRequest createResendOTPRequest(
			ResendOTPRequest request) {

		ResendOTPServiceRequest reSendRequest = new ResendOTPServiceRequest();
		String userId  = null;
		reSendRequest.setOtpId(request.getOtpId());
		reSendRequest.setOtpChannel(request.getOtpChannel());
		if(StringUtils.isNotBlank(request.getToken())) {
			reSendRequest.setToken(request.getToken());
			String tokenVersion = tokenService.getTokenVersion(request.getToken());

			boolean isV2Token = StringUtils
					.equals(tokenVersion,
							Configuration
							.getGlobalProperty(ConfigurationConstants.LINK_UPGRADE_TOKEN_GENERATION_SERVICE_VERSION));
			tokenValidationService.validateToken(request.getToken(), isV2Token);

			userId = tokenService.getUserIdByToken(request.getToken(), isV2Token);
			GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest() ;	
			getUserByIdRequest.setUserId(userId);
			GetUserResponse getUserResponse = null;
			if (isV2Token) {
				getUserResponse = userService
						.getUserFromIMSInCaseOfLinkUserWithOcPassword(getUserByIdRequest);
			} else {
				getUserResponse = userService.getUser(getUserByIdRequest);
			}
			UserDetailsDTO dto = getUserResponse.getUserDetails() ;
			String displayName = StringUtils.isNotBlank(dto.getFirstName()) ? dto.getFirstName() : dto.getDisplayName() ; 
			reSendRequest.setName(StringUtils.capitalize(StringUtils.lowerCase(displayName)));
		}
		reSendRequest.setClientId(context.get(IMSRequestHeaders.CLIENT_ID
				.toString()));
		reSendRequest.setMerchant(getMerchantEnum());

		return reSendRequest;
	}

	private GenerateOTPResponse prepareResponse(OTPServiceResponse response) {
		GenerateOTPResponse res = new GenerateOTPResponse();
		res.setOtpId(response.getOtpId().toString());
		return res;
	}

	protected Merchant getMerchantEnum() {

		String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
		return ClientConfiguration.getMerchantById(clientId);
	}

}
