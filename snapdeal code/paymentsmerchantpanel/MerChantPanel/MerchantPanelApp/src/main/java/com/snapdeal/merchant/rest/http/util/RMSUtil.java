package com.snapdeal.merchant.rest.http.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantChangePasswordRequest;
import com.snapdeal.merchant.request.MerchantForgotPasswordRequest;
import com.snapdeal.merchant.request.MerchantGenerateOTPRequest;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantLogoutRequest;
import com.snapdeal.merchant.request.MerchantResendOTPRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.request.MerchantSetPasswordRequest;
import com.snapdeal.merchant.request.MerchantUserByTokenRequest;
import com.snapdeal.merchant.request.MerchantVerifyOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyUserRequest;
import com.snapdeal.merchant.response.MerchantChangePasswordResponse;
import com.snapdeal.merchant.response.MerchantSetPasswordResponse;
import com.snapdeal.merchant.response.MerchantVerifyOTPResponse;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.roleManagementClient.exceptions.HttpTransportException;
import com.snapdeal.payments.roleManagementModel.dto.Token;
import com.snapdeal.payments.roleManagementModel.exceptions.InvalidCodeException;
import com.snapdeal.payments.roleManagementModel.exceptions.NoSuchUserException;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.payments.roleManagementModel.request.ChangePasswordRequest;
import com.snapdeal.payments.roleManagementModel.request.ForgotPasswordNotifyRequest;
import com.snapdeal.payments.roleManagementModel.request.GenerateOTPRequest;
import com.snapdeal.payments.roleManagementModel.request.GetRolesByRoleNamesRequest;
import com.snapdeal.payments.roleManagementModel.request.GetUserByTokenRequest;
import com.snapdeal.payments.roleManagementModel.request.GetUserByUserNameRequest;
import com.snapdeal.payments.roleManagementModel.request.LoginUserRequest;
import com.snapdeal.payments.roleManagementModel.request.LogoutUserRequest;
import com.snapdeal.payments.roleManagementModel.request.ResendOTPRequest;
import com.snapdeal.payments.roleManagementModel.request.VerifyCodeRequest;
import com.snapdeal.payments.roleManagementModel.request.VerifyOTPRequest;
import com.snapdeal.payments.roleManagementModel.response.GenerateOTPResponse;
import com.snapdeal.payments.roleManagementModel.response.GetRolesByRoleNamesResponse;
import com.snapdeal.payments.roleManagementModel.response.GetUserByTokenResponse;
import com.snapdeal.payments.roleManagementModel.response.GetUserByUserNameResponse;
import com.snapdeal.payments.roleManagementModel.response.LoginUserResponse;
import com.snapdeal.payments.roleManagementModel.response.LogoutUserResponse;
import com.snapdeal.payments.roleManagementModel.services.RoleMgmtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RMSUtil {

	@Autowired
	private RoleMgmtService roleMgmtService;

	public LoginUserResponse login(MerchantLoginRequest request) throws MerchantException {

		LoginUserRequest rmsRequest = new LoginUserRequest();
		rmsRequest.setPassword(request.getPassword());
		rmsRequest.setUserName(request.getLoginName());

		LoginUserResponse response = null;

		try {

			log.info("RMS Request For login: {}", rmsRequest.getUserName());
			response = roleMgmtService.loginUser(rmsRequest);
			log.info("RSM Response For login: {} {} ", response);

		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from rms while logining user: {}", e);
			throw new MerchantException(e.getMessage());
		}
		return response;
	}

	public void setRoleMgmtService(RoleMgmtService roleMgmtService) {
		this.roleMgmtService = roleMgmtService;
	}

	public void logout(MerchantLogoutRequest request) throws MerchantException {

		LogoutUserRequest rmsRequest = new LogoutUserRequest();

		Token rmsToken = new Token();
		rmsToken.setToken(request.getToken());
		rmsRequest.setToken(rmsToken);
		rmsRequest.setUserId(null);

		LogoutUserResponse response = null;
		try {
			log.info("RMS Request For logout: {}",rmsRequest);
			response = roleMgmtService.logoutUser(rmsRequest);
			log.info("RMS Request For logout: {}",response);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from rms while logout: {}", e);
			throw new MerchantException(e.getMessage());
		}
		response.setSuccess(true);
		return;
	}

	public boolean forgotPassword(MerchantForgotPasswordRequest request) throws MerchantException {

		ForgotPasswordNotifyRequest rmsRequest = new ForgotPasswordNotifyRequest();

		rmsRequest.setUserName(request.getLoginName());
		rmsRequest.setUserAgent(null);
		rmsRequest.setUserMachineIdentifier(null);

		try {
			log.info("RMS Request For forgotPassword: {}",rmsRequest);
			roleMgmtService.forgotPasswordNotify(rmsRequest);
			log.info("Forgot Password Called.");
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from rms while forgot password: {}", e);
			throw new MerchantException(e.getMessage());
		}

		return true;

	}

	public MerchantChangePasswordResponse changePassword(MerchantChangePasswordRequest request)
			throws MerchantException {

		MerchantChangePasswordResponse response = new MerchantChangePasswordResponse();

		ChangePasswordRequest changePassword = new ChangePasswordRequest();
		changePassword.setNewPassword(request.getNewPassword());
		changePassword.setOldPassword(request.getOldPassword());
		changePassword.setToken(request.getToken());
		try {
			log.info("RMS Request For changePassword: {}",changePassword);
			roleMgmtService.changePassword(changePassword);
			log.info("PassWord Changed.");
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from RMS while Changing password : {}", e);
			throw new MerchantException(e.getMessage());

		}
		response.setSuccess(true);
		return response;
	}

	public GetUserByUserNameResponse getUserDetails(MerchantVerifyUserRequest request) throws MerchantException {

		GetUserByUserNameRequest rmsRequest = new GetUserByUserNameRequest();
		rmsRequest.setRequestToken(request.getToken());
		rmsRequest.setUserName(request.getLoginName());
		rmsRequest.setAppName(AppConstants.rmsAppName);
		GetUserByUserNameResponse rmsResponse = null;

		try {
			log.info("RMS Request For getUserDetails: {}",rmsRequest);
			rmsResponse = roleMgmtService.getUserByUserName(rmsRequest);
			log.info("RMS Response For getUserDetails: {}",rmsResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (NoSuchUserException e) {
			log.info("User doesnt exists with Login Name : {} {}", request.getLoginName(), e);
		} catch (RoleMgmtException e) {
			log.error("Exception from RMS while getting user info : {}", e);
			throw new MerchantException(e.getMessage());

		}

		return rmsResponse;
	}

	public GetRolesByRoleNamesResponse getRoles(MerchantRoleRequest request) throws MerchantException {

		GetRolesByRoleNamesRequest rmsRequest = new GetRolesByRoleNamesRequest();
		rmsRequest.setRequestToken(request.getToken());
		rmsRequest.setListRoleNames(request.getRoles());
		rmsRequest.setAppName(AppConstants.rmsAppName);

		GetRolesByRoleNamesResponse rmsResponse = null;

		try {
			log.info("RMS Request For getRoles: {}",rmsRequest);
			rmsResponse = roleMgmtService.getRolesByRoleNames(rmsRequest);
			log.info("RMS Response For getRoles: {}",rmsResponse);
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from RMS while getting user info : {}", e);
			throw new MerchantException(e.getMessage());

		}

		return rmsResponse;
	}

	public GetUserByTokenResponse getUserByToken(MerchantUserByTokenRequest request) throws MerchantException {

		GetUserByTokenRequest rmsRequest = new GetUserByTokenRequest();
		rmsRequest.setRequestToken(request.getToken());

		GetUserByTokenResponse rmsResponse = null;
		try {
			log.info("RMS Request For getUserByToken: {}",rmsRequest);
			rmsResponse = roleMgmtService.getUserByToken(rmsRequest);
			log.info("RMS response For getUserByToken: {}",rmsResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from RMS while getting user info from token : {}", e);
			throw new MerchantException(e.getMessage());
		}
		return rmsResponse;

	}

	@Logged
	public GenerateOTPResponse generateOTP(MerchantGenerateOTPRequest request) throws MerchantException {

		GenerateOTPRequest rmsRequest = new GenerateOTPRequest();
		rmsRequest.setUserName(request.getLoginName());

		GenerateOTPResponse rmsResponse = null;
		try {
			
			log.info("RMS Request For generateOTP: {}",rmsRequest);
			rmsResponse = roleMgmtService.generateOTP(rmsRequest);
			log.info("RMS Response For generateOTP: {}",rmsResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from RMS while generating OTP : {}", e);
			throw new MerchantException(e.getMessage());
		}

		return rmsResponse;
	}

	public MerchantVerifyOTPResponse verifyOTP(MerchantVerifyOTPRequest request) throws MerchantException {

		MerchantVerifyOTPResponse response = new MerchantVerifyOTPResponse();

		VerifyOTPRequest rmsRequest = new VerifyOTPRequest();
		rmsRequest.setOtpId(request.getOtpId());
		rmsRequest.setOtp(request.getOtp());
		rmsRequest.setNewPassword(request.getPassword());

		try {
			log.info("RMS Request For verifyOTP: {}",rmsRequest);
			roleMgmtService.verifyOTP(rmsRequest);
			log.info("OTP Verified.");
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from RMS while verifying OTP: {}", e);
			throw new MerchantException(e.getMessage());

		}
		response.setMessage(ErrorConstants.SUCCESS_MSG_FOR_FORGOT_PASSWORD);
		return response;

	}

	@Logged
	public GenerateOTPResponse resendOTP(MerchantResendOTPRequest request) throws MerchantException {

		ResendOTPRequest rmsRequest = new ResendOTPRequest();
		rmsRequest.setOtpId(request.getOtpId());

		GenerateOTPResponse rmsResponse = null;
		try {
			log.info("RMS Request For resendOTP: {}",rmsRequest);
			rmsResponse = roleMgmtService.resendOTP(rmsRequest);
			log.info("RMS Response For resendOTP: {}",rmsResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from RMS while generating OTP : {}", e);
			throw new MerchantException(e.getMessage());
		}

		return rmsResponse;
	}

	public MerchantSetPasswordResponse setPassword(MerchantSetPasswordRequest request) throws MerchantException {
		MerchantSetPasswordResponse response = new MerchantSetPasswordResponse();

		VerifyCodeRequest rmsRequest = new VerifyCodeRequest();
		rmsRequest.setPassword(request.getPassword());
		rmsRequest.setVerificationCode(request.getUserIdentifier());
		try {
			
			log.info("RMS Request For setPassword: {}",rmsRequest);
			roleMgmtService.verifyCodeAndSetPassword(rmsRequest);
			log.info("Password changed .");
			
		} catch (InvalidCodeException ice) {
			log.error("Getting InvalidCodeException while set Password : {} , {}", ice.getMessage(), ice);
			response.setSuccess(false);
			return response;

		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from RMS while generating OTP : {}", e);
			throw new MerchantException(e.getMessage());
		}
		response.setSuccess(true);

		return response;

	}

	public RoleMgmtService getRoleMgmtService() {
		return roleMgmtService;
	}

}
