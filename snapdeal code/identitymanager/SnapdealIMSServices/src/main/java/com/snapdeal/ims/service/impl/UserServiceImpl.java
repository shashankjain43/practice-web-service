package com.snapdeal.ims.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.entity.SdFcPasswordEntity;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.ChangePasswordRequest;
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
import com.snapdeal.ims.request.ForgotPasswordRequest;
import com.snapdeal.ims.request.GetIMSUserVerificationUrlRequest;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.request.GetUserByTokenRequest;
import com.snapdeal.ims.request.IsEmailExistRequest;
import com.snapdeal.ims.request.IsUserExistRequest;
import com.snapdeal.ims.request.IsVerifiedMobileExistRequest;
import com.snapdeal.ims.request.MobileOnlyRequest;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.ResendEmailVerificationLinkRequest;
import com.snapdeal.ims.request.ResetPasswordRequest;
import com.snapdeal.ims.request.UpdateMobileNumberRequest;
import com.snapdeal.ims.request.UpdateUserByIdRequest;
import com.snapdeal.ims.request.UpdateUserByTokenRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.VerifyUserRequest;
import com.snapdeal.ims.response.ChangePasswordResponse;
import com.snapdeal.ims.response.ChangePasswordWithLoginResponse;
import com.snapdeal.ims.response.CloseAccountResponse;
import com.snapdeal.ims.response.ConfigureUserStateResponse;
import com.snapdeal.ims.response.CreateGuestUserResponse;
import com.snapdeal.ims.response.CreateSocialUserWithMobileResponse;
import com.snapdeal.ims.response.CreateUserResponse;
import com.snapdeal.ims.response.ForgotPasswordResponse;
import com.snapdeal.ims.response.GetIMSUserVerificationUrlResponse;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.response.IsEmailExistResponse;
import com.snapdeal.ims.response.IsUserExistResponse;
import com.snapdeal.ims.response.IsVerifiedMobileExistResponse;
import com.snapdeal.ims.response.MobileOnlyResponse;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.OTPResponse;
import com.snapdeal.ims.response.ResendEmailVerificationLinkResponse;
import com.snapdeal.ims.response.ResetPasswordResponse;
import com.snapdeal.ims.response.ResetPasswordWithLoginResponse;
import com.snapdeal.ims.response.SocialUserResponse;
import com.snapdeal.ims.response.UpdateMobileNumberResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserResponse;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.service.IUserService;
import com.snapdeal.ims.service.provider.UmsMerchantProvider;
import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.ims.utils.PasswordHashServiceUtil;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	@Qualifier("IMSService")
	private IUMSService imsService;

	@Autowired
	private UmsServiceProvider serviceProvider;

	@Autowired
	IPasswordUpgradeCacheService passwordCacheService;

	@Autowired
	UmsMerchantProvider merchantProvider;

	@Autowired
	IUserMigrationService userMigrationService;

	@Override
	@Timed
	@Marked
	public GetUserResponse updateUser(UpdateUserByIdRequest request) {
		GetUserResponse getUserResponse =serviceProvider.getUMSServiceById(request.getUserId())
				.updateUser(request);
		return getUserResponse;
	}


	@Override
	@Timed
	@Marked
	public GetUserResponse getUserByEmail(GetUserByEmailRequest request) {

		return serviceProvider.getUMSServiceByEmail(request.getEmailId())
				.getUserByEmail(request);
	}


	@Override
	@Timed
	@Marked
	public GetUserResponse getUserByMobile(GetUserByMobileRequest request) {

		return serviceProvider.getUMSServiceByMobile(request.getMobileNumber())
				.getUserByMobile(request);
	}


	@Override
	@Timed
	@Marked
	public GetUserResponse getUserByToken(GetUserByTokenRequest request) {

		return serviceProvider.getUMSServiceByToken(request.getToken())
				.getUserByToken(request);
	}


	@Override
	@Timed
	@Marked
	public GetUserResponse getUser(GetUserByIdRequest request) {

		return serviceProvider.getUMSServiceById(request.getUserId()).getUser(
				request);
	}


	@Override
	@Timed
	@Marked
	public CreateUserResponse createUserByEmail(CreateUserEmailRequest request) {
		if(isEmailExist(request.getUserDetailsByEmailDto().getEmailId())){
			throw new IMSServiceException(
					IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errCode(),
					IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errMsg());
		};
		CreateUserResponse response = serviceProvider.getUMSService().createUserByEmail(request);
		if (response != null && response.getUserDetails() != null
				&& merchantProvider.getMerchant() != Merchant.ONECHECK) {
			SdFcPasswordEntity entity = serviceProvider.getUMSService()
					.putSdFcHashedPasswordByEmailId(request.getUserDetailsByEmailDto().getEmailId(),
							request.getUserDetailsByEmailDto().getPassword());
			passwordCacheService.createSdFcPasswordbyEmailId(request
					.getUserDetailsByEmailDto().getEmailId(), entity);
		}
		return response;

	}


	@Override
	@Timed
	@Marked
	@Logged
	public CreateUserResponse createUserByEmailAndMobile(CreateUserEmailMobileRequest request) {
		if(isEmailExist(request.getUserRequestDto().getEmailId())){
			throw new IMSServiceException(
					IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errCode(),
					IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errMsg());
		};
		return serviceProvider.getUMSService().createUserByEmailAndMobile(request);
	}


	@Override
	@Timed
	@Marked
	public IsVerifiedMobileExistResponse isMobileExist(
			IsVerifiedMobileExistRequest request) {
		return serviceProvider.getUMSServiceByMobile(request.getMobileNumber())
				.isMobileExist(request);
	}


	@Override
	@Timed
	@Marked
	public MobileVerificationStatusResponse getMobileVerificationStatus(
			MobileVerificationStatusRequest request) {
		return serviceProvider.getUMSServiceById(request.getUserId())
				.isMobileVerified(request);
	}


	@Override
	@Timed
	@Marked
	//TODO : we are not supprting this Api .. need to confirm
	public SocialUserResponse createSocialUser(CreateSocialUserRequest request)
			throws IMSServiceException {
		// check if one check user exists, in case one onecheck user not exists check
		// in respective impl
		return serviceProvider.getUMSServiceByEmailWithoutMigration(request.getSocialUserDto().getEmailId())
				.createSocialUser(request);
	}


	@Override
	@Timed
	@Marked
	public GetUserResponse updateUserByToken(UpdateUserByTokenRequest request) {

		return serviceProvider.getUMSServiceByToken(request.getToken())
				.updateUserByToken(request);
	}


	@Override
	@Timed
	@Marked
	public UpdateMobileNumberResponse updateMobileNumber(

			UpdateMobileNumberRequest request) {
		return serviceProvider.getUMSServiceByMobile(request.getMobileNumber())
				.updateMobileNumber(request);

	}


	@Override
	@Timed
	@Marked
	@Logged
	public OTPResponse createUserWithMobile(CreateUserMobileGenerateRequest request)
			throws IMSServiceException {

		OTPResponse response = serviceProvider.getIMSService().createUserByMobile(request);
		if (response != null && response.getOtpId() != null) {
			if ((merchantProvider.getMerchant() == Merchant.ONECHECK)) {
				String password = PasswordHashServiceUtil.getSdHashedPassword(request
						.getUserRequestDto().getPassword());
				passwordCacheService.createImsSdHashed(request.getUserRequestDto().getEmailId(),
						password);
			} else {
				SdFcPasswordEntity entity = serviceProvider.getMigrationEnabledUMSService()
						.putSdFcHashedPasswordByEmailId(request.getUserRequestDto().getEmailId(),
								request.getUserRequestDto().getPassword());
				passwordCacheService.createSdFcPasswordbyEmailId(request.getUserRequestDto()
						.getEmailId(), entity);
			}
		}
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public OTPResponse createUserWithMobileOnly(CreateUserWithMobileOnlyRequest request)
			throws IMSServiceException {

		OTPResponse response = serviceProvider.getUMSService().createUserByMobileOnly(request);
		return response;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public CreateUserResponse verifyUserWithMobileOnly(
			CreateUserMobileVerifyRequest request) throws IMSServiceException {

		return serviceProvider.getIMSService().verifyUserWithMobileOnly(request);
	}

	@Override
	@Timed
	@Marked
	@Logged
	public CreateUserResponse verifyUserWithMobile(
			CreateUserMobileVerifyRequest request) throws IMSServiceException {

		return serviceProvider.getIMSService().verifyUserWithMobile(request);
	}


	@Override
	@Timed
	@Marked
	public CreateGuestUserResponse createGuestUserByEmail(
			CreateGuestUserEmailRequest createGuestUserByEmailRequest) {
		if(isEmailExist(createGuestUserByEmailRequest.getEmailId())){
			throw new IMSServiceException(
					IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errCode(),
					IMSServiceExceptionCodes.EMAIL_ALREADY_REGISTERED.errMsg());
		};
		return serviceProvider.getUMSService().createGuestUserByEmail(
				createGuestUserByEmailRequest);
	}


	@Override
	@Timed
	@Marked
	@Logged

	public VerifyUserResponse verifyUser(VerifyUserRequest verifyUserRequest) {
		return serviceProvider.getUMSServiceForVerifyUser(verifyUserRequest).verifyGuestUser(
				verifyUserRequest);
	}


	@Override
	@Timed
	@Marked
	public IsUserExistResponse isUserExist(IsUserExistRequest request) {
		return serviceProvider.getUMSServiceById(request.getUserId())
				.isUserExist(request);
	}


	@Override
	@Timed
	@Marked
	public IsEmailExistResponse isEmailExist(IsEmailExistRequest request) {
		return serviceProvider.getUMSServiceByEmail(request.getEmailId())
				.isEmailExist(request);
	}


	@Override
	@Timed
	@Marked
	@Logged
	public ConfigureUserStateResponse configureUserState(ConfigureUserStateRequest request){
		return serviceProvider.getUMSServiceForConfigureUserState(request).configureUserState(request);
	}


	@Override
	@Timed
	@Marked
	public ChangePasswordResponse changePassword(ChangePasswordRequest request) {
		return serviceProvider.getUMSServiceByToken(request.getToken())
				.changePassword(request);
	}


	@Override
	@Timed
	@Marked
	public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {
		if(StringUtils.isBlank(request.getEmailId()) && StringUtils.isNotBlank(request.getMobileNumber())){
			return serviceProvider.getUMSServiceByMobile(request.getMobileNumber()).forgotPassword(request);
		}else{
			return serviceProvider.getUMSServiceByEmail(request.getEmailId())
					.forgotPassword(request);
		}
	}


	@Override
	@Timed
	@Marked
	public ResetPasswordResponse resetPassword(ResetPasswordRequest request) {
		return serviceProvider.getUMSServiceById(request.getUserId())
				.resetPassword(request);
	}


	@Override
	@Timed
	@Marked
	public ResendEmailVerificationLinkResponse resendEmailVerificationLink(
			ResendEmailVerificationLinkRequest request) {
		return serviceProvider.getUMSServiceByEmail(request.getEmailId())
				.resendEmailVerificationLink(request);
	}


	@Override
	@Timed
	@Marked
	public ResetPasswordWithLoginResponse resetPasswordAndLogin(ResetPasswordRequest request)
			throws ValidationException, IMSServiceException {
		return serviceProvider.getUMSServiceById(request.getUserId()).resetPasswordAndLogin(
				request);
	}


	@Override
	@Timed
	@Marked
	public ChangePasswordWithLoginResponse changePasswordWithLogin(
			ChangePasswordRequest request) throws ValidationException,
			IMSServiceException {
		return serviceProvider.getUMSServiceByToken(request.getToken())
				.changePasswordWithLogin(request);
	}


	@Override
	@Timed
	@Marked
	@Logged
	public CreateSocialUserWithMobileResponse createSocialUserWithMobile(
			CreateSocialUserWithMobileRequest request) {
		return serviceProvider.getIMSService().createSocialUserWithMobile(request);
	}


	@Override
	@Timed
	@Marked
	public GetUserResponse getUserFromIMSInCaseOfLinkUserWithOcPassword(GetUserByIdRequest request) {
		return serviceProvider.getIMSService().getUser(request);
	}


	@Override
	@Timed
	@Marked
	@Logged
	public CreateUserResponse verifySocialUserWithMobile(CreateUserMobileVerifyRequest request) {
		return serviceProvider.getIMSService().verifySocialUserWithMobile(request);
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetIMSUserVerificationUrlResponse getIMSUserVerificationUrl(
			GetIMSUserVerificationUrlRequest request) {
		UserUpgradeByEmailRequest userUpgradeByEmailRequest = new UserUpgradeByEmailRequest();
		userUpgradeByEmailRequest.setEmailId(request.getEmail());
		UserUpgradationResponse userUpgradationResponse= userMigrationService.getUserUpgradeStatus(userUpgradeByEmailRequest,true);
		Upgrade upgrade = userUpgradationResponse.getUpgradationInformation().getUpgrade();
		return serviceProvider.getIMSService().getIMSUserVerificationUrl(
				request, upgrade, null);
	}

	private boolean isEmailExist(String emailId) {
		IsEmailExistRequest request = new IsEmailExistRequest();
		request.setEmailId(emailId);
		IsEmailExistResponse response = imsService.isEmailExist(request);
		if (response != null && response.isExist()) {
			return true;
		}
		return false;
	}


	@Override
	@Timed
	@Marked
	@Logged

	public CloseAccountResponse closeAccount(CloseAccountByEmailRequest request) {
		return serviceProvider.getIMSService().closeAccount(request);
	}

	@Timed
	@Marked
	@Logged
	@Override
	public GtokenSizeResponse getGlobalTokenSizeByEmailId(
			GetUserByEmailRequest getUserByEmailRequest) {
		return serviceProvider.getIMSService().getGlobalTokenSizeByEmailId(
				getUserByEmailRequest);
	}


	@Override
	public MobileOnlyResponse isMobileOnly(MobileOnlyRequest mobileOnlyRequest) {
		return serviceProvider.getIMSService().isMobileOnly(mobileOnlyRequest);
	}


}
