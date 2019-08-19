package com.snapdeal.ims.otp.service.impl;

import com.google.common.base.Optional;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.OTPRequestChannel;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.dao.PersistenceManager;
import com.snapdeal.ims.otp.email.IEmailSender;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.FetchLatestOTPRequest;
import com.snapdeal.ims.otp.internal.request.IsOTPVerifiedRequest;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPGenerator;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.otp.service.IOTPValidator;
import com.snapdeal.ims.otp.service.IOtp;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.otp.util.OtpEncryptionUtility;
import com.snapdeal.ims.otp.validators.GenericValidator;
import com.snapdeal.ims.request.AbstractOTPServiceRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.IsOTPVerifiedServiceRequest;
import com.snapdeal.ims.request.ResendOTPServiceRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.response.IsOTPValidResponse;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.EnumSet;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for {@link IOTPService}
 * 
 * @author Abhishek
 *
 */
@Slf4j
@Service
public class OTPServiceImpl implements IOTPService {

	@Autowired
	private IEmailSender sender;

	@Autowired
	private PersistenceManager persistenceManager;

	@Autowired
	private IOTPGenerator otpGenerator;

	@Autowired
	private IOTPValidator otpValidator;

	@Autowired
	private OTPUtility otpUtility;

	@Autowired
	GenericValidator<AbstractOTPServiceRequest> validator;


	/**
	 * List of OTP's where token validation needs to be skipped.
	 */
	private final static EnumSet<OTPPurpose> SKIP_TOKEN_FOR_OTP_PURPOSE_LIST =  EnumSet.of(OTPPurpose.FORGOT_PASSWORD, 
			OTPPurpose.USER_SIGNUP, 
			OTPPurpose.UPGRADE_USER, 
			OTPPurpose.ONECHECK_SOCIAL_SIGNUP,
			OTPPurpose.WALLET_PAY,
			OTPPurpose.WALLET_LOAD,
			OTPPurpose.WALLET_ENQUIRY,
			OTPPurpose.SIGNUP_WITH_OTP,
			OTPPurpose.LOGIN_WITH_MOBILE_OTP,
			OTPPurpose.LOGIN_WITH_EMAIL_OTP);

	/**
	 * List of otp, where email is not sent.
	 */
	private final static EnumSet<OTPPurpose> OTP_PURPOSE_LIST_EMAIL_SKIP_LIST = EnumSet.of(
			OTPPurpose.UPDATE_MOBILE, 
			OTPPurpose.MOBILE_VERIFICATION, 
			OTPPurpose.UPGRADE_USER,
			OTPPurpose.USER_SIGNUP,
			OTPPurpose.LOGIN_WITH_MOBILE_OTP,
			OTPPurpose.SIGNUP_WITH_OTP);

	@Override
	@Timed
	@Marked
	@Logged
	public OTPServiceResponse generateOTP(GenerateOTPServiceRequest request) {

		validator.validate(request);

		otpGenerator.validateUserFreeze(request);
		/*
		 * generate OTP
		 */
		val otp = otpGenerator.generate(request);
		/*
		 * send otp to user
		 */
		sendOTP(otp,request.getMerchant(),request.getName(),OTPRequestChannel.THROUGH_SMS);
		return prepareResponse(otp.getOtpInfo());
	}

	@Override
	@Timed
	@Marked
	@Logged
	public OTPServiceResponse resendOTP(ResendOTPServiceRequest request) {

		validator.validate(request);
		// fetch OTP for this userId and otpId;
		FetchLatestOTPRequest fetchLatestOTPRequest = new FetchLatestOTPRequest();
		fetchLatestOTPRequest.setOtpId(request.getOtpId());
		fetchLatestOTPRequest.setToken(request.getToken());
		fetchLatestOTPRequest.setClientId(request.getClientId());

		Optional<UserOTPEntity> currentOtpInfo = persistenceManager
				.getOTPFromId(fetchLatestOTPRequest);
		if (currentOtpInfo.isPresent()) {
			/*
			 * UserOTPEntity otpinfo = currentOtpInfo.get(); validator =
			 * otpServiceFactory.getInstanceProfileRequest(otpinfo
			 * .getOtpType().toString());
			 */
			if(StringUtils.isNotBlank(request.getToken())) {
				validateToken(currentOtpInfo, request);
			}
			val otp = otpGenerator.reSendOTP(currentOtpInfo);
			sendOTP(otp,request.getMerchant(),request.getName(),request.getOtpChannel());

			return prepareResponse(otp.getOtpInfo());
		} else {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg());
		}
	}

	private void sendOTP(final com.snapdeal.ims.otp.service.IOtp otp,Merchant merchant,String name,OTPRequestChannel otpChannel) {
		if (StringUtils.isNotBlank(otp.getOtpInfo().getMobileNumber())) {    	  
			switch (otpChannel) {
			case THROUGH_SMS:
				otp.sendSMS(merchant);
				break;
			case THROUGH_CALL:
				otp.callNumber(merchant);
				break;
			}
		}

		if (StringUtils.isNotBlank(otp.getOtpInfo().getEmail()) && sendOtpOnEmail(otp)) {
			sendEmail(otp.getOtpInfo(),merchant,name);
		}
	}

	@Override
	@Timed
	@Marked
	@Logged
	public VerifyOTPServiceResponse verifyOTP(VerifyOTPServiceRequest request) {
		int status;
		String verifyOTPStatus;
		String message;
		String userId = null;
		String mobileNumber = null;
		validator.validate(request);
		FetchLatestOTPRequest fetchLatestOTPRequest = new FetchLatestOTPRequest();
		fetchLatestOTPRequest.setOtpId(request.getOtpId());
		fetchLatestOTPRequest.setToken(request.getToken());
		fetchLatestOTPRequest.setClientId(request.getClientId());
		Optional<UserOTPEntity> currentOtpInfo = persistenceManager
				.getOTPFromId(fetchLatestOTPRequest);

		if (currentOtpInfo.isPresent()) {
			validateToken(currentOtpInfo, request);
			validateOTPPurpose(request.getOtpPurpose(),currentOtpInfo.get().getOtpType()) ;
			status = otpValidator.verify(currentOtpInfo, request,false);
		} else {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg());
		}

		if (status == OtpConstants.STATUS) {
			log.info("OTP success for :" + request);
			verifyOTPStatus = otpUtility.getSuccessMessage();
			message = otpUtility.getSuccessMessage();
			userId = currentOtpInfo.get().getUserId();
			mobileNumber = currentOtpInfo.get().getMobileNumber();
		} else {
			log.info("OTP Failed for :" + request);
			verifyOTPStatus = otpUtility.getFailureMessage();
			message = otpUtility.getFailureMessage();
		}
		return prepareResponse(verifyOTPStatus, message, userId, mobileNumber);
	}

	public IsOTPValidResponse isOTPValid(IsOTPVerifiedServiceRequest request){

		validator.validate(request);
		IsOTPValidResponse isOTPValidResponse = new IsOTPValidResponse() ;
		IsOTPVerifiedRequest isOTPVerifiedRequest = new IsOTPVerifiedRequest() ;
		isOTPVerifiedRequest.setOtpId(request.getOtpId());
		isOTPVerifiedRequest.setClientId(request.getClientId());

		Optional<UserOTPEntity> currentOtpInfo = persistenceManager
				.isOTPVerified(isOTPVerifiedRequest) ;
		if(currentOtpInfo.isPresent()){
			VerifyOTPServiceRequest verifyOTPRequest = createVerifyOTPRequest(request);
			int status = otpValidator.verify(currentOtpInfo,verifyOTPRequest,true);
			if (status == OtpConstants.STATUS) {
				log.info("OTP is valid for : " + request);
				isOTPValidResponse.setStatus(true);
			} else {
				log.info("OTP is not valid for : " + request);
				isOTPValidResponse.setStatus(false);
			}
			/*
			UserOTPEntity otp = currentOtpInfo.get() ;
			boolean isOTPEncryptionEnabled = Boolean
					.valueOf(Configuration
							.getGlobalProperty(ConfigurationConstants.OTP_ENCRYPTION_ENABLED));
			if(otp.getOtp().length()>4 && isOTPEncryptionEnabled) {
				otp.setOtp(OtpEncryptionUtility.decryptOTP(otp.getOtp()));
			}
			if(otp.getOtp().equalsIgnoreCase(request.getOtp())){
				//flag will be true only when  otp is active.
				if(otp.getOtpStatus().toString().equalsIgnoreCase("ACTIVE")){
					isOTPValidResponse.setStatus(true);
				}else{
					isOTPValidResponse.setStatus(false);
				}
			}else{
				throw new IMSServiceException(
						IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(),
						IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg()) ;
			}
		*/}else{
			throw new IMSServiceException(
					IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg()) ;
		}
		return isOTPValidResponse ;
	}

	private VerifyOTPServiceRequest createVerifyOTPRequest(
			IsOTPVerifiedServiceRequest request) {
		VerifyOTPServiceRequest verifyOTPRequest = new VerifyOTPServiceRequest();
		verifyOTPRequest.setClientId(request.getClientId());
		verifyOTPRequest.setOtpId(request.getOtpId());
		verifyOTPRequest.setOtp(request.getOtp());
		return verifyOTPRequest;
	}

	@Async
	private void sendEmail(UserOTPEntity otp,Merchant merchant,String name) {
		sender.send(otp,merchant,name);
	}

	private VerifyOTPServiceResponse prepareResponse(String status,
			String message, String userId, String mobileNumber) {
		return VerifyOTPServiceResponse.builder().status(status)
				.message(message).userId(userId).mobileNumber(mobileNumber).build();
	}

	private OTPServiceResponse prepareResponse(UserOTPEntity currentOtpInfo) {
		return OTPServiceResponse.builder().otpId(currentOtpInfo.getOtpId())
				.build();
	}

	private void validateToken(Optional<UserOTPEntity> otpOptional,
			AbstractOTPServiceRequest request) {
		UserOTPEntity otpInfo = otpOptional.get();
		OTPPurpose otpType = OTPPurpose.valueOf(otpInfo.getOtpType());

		// Verify token if present or otpType is not in skip list.
		if (!SKIP_TOKEN_FOR_OTP_PURPOSE_LIST.contains(otpType) 
				|| org.apache.commons.lang.StringUtils.isNotBlank(request.getToken())) {

			if (StringUtils.isEmpty(request.getToken())) {
				throw new RequestParameterException(
						IMSRequestExceptionCodes.TOKEN_IS_BLANK.errCode(),
						IMSRequestExceptionCodes.TOKEN_IS_BLANK.errMsg());
			} else if (!request.getToken().equalsIgnoreCase(otpInfo.getToken())) {
				throw new ValidationException(
						IMSAuthenticationExceptionCodes.INVALID_TOKEN.errCode(),
						IMSAuthenticationExceptionCodes.INVALID_TOKEN.errMsg());
			}
		}

	}
	private void validateOTPPurpose(OTPPurpose purpose,String otpPurpose){
		OTPPurpose savedOTPPurpose = OTPPurpose.valueOf(otpPurpose) ;
		if(purpose != null){
			if(purpose != savedOTPPurpose){
				throw new IMSServiceException(
						IMSServiceExceptionCodes.INVALID_PURPOSE.errCode(),
						IMSServiceExceptionCodes.INVALID_PURPOSE.errMsg()) ;
			}
		}
	}

	private boolean sendOtpOnEmail(IOtp otp) {
		UserOTPEntity otpInfo = otp.getOtpInfo();
		OTPPurpose otpType = OTPPurpose.valueOf(otpInfo.getOtpType());
		if (OTP_PURPOSE_LIST_EMAIL_SKIP_LIST.contains(otpType)) {
			return false;
		}
		return true;
	}

	@Override
	public UserOTPEntity getOTPInfo(FetchLatestOTPRequest request){

		Optional<UserOTPEntity> currentOtpInfo = persistenceManager
				.getOTPFromId(request) ;

		if(currentOtpInfo.isPresent()){
			return currentOtpInfo.get() ;
		}
		else{
			throw new IMSServiceException(
					IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg());
		}
	}
}
