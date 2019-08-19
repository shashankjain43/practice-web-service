package com.snapdeal.ims.service.impl;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.constants.DummyMigrationEmails;
import com.snapdeal.ims.dto.UpgradationInformationDTO;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.Action;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Skip;
import com.snapdeal.ims.enums.State;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.enums.Wallet;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.migration.exception.IMSMigrationHardDeclinedException;
import com.snapdeal.ims.migration.model.entity.UpgradeStatus;
import com.snapdeal.ims.otp.dao.PersistenceManager;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.FetchLatestOTPRequest;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.request.UserUpgradeRequest;
import com.snapdeal.ims.request.VerifyUserWithLinkedStateRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;
import com.snapdeal.ims.request.VerifyUserUpgradeRequest;
import com.snapdeal.ims.response.UpgradeUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.response.VerifyUserWithLinkedStateResponse;
import com.snapdeal.ims.response.VerifyUpgradeUserResponse;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service("dummyMigrationService")
public class DummyMigrationServiceImpl implements IUserMigrationService {
	@Autowired
	private PersistenceManager persistenceManager;
	@Autowired
	private OTPUtility otpUtility;

	@Autowired
	private AuthorizationContext context;

	private Client getClientDetails() {
		return ClientConfiguration.getClientById(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
	}
	
	/**
	 * Utility method used temporarily in pass-through system.
	 */
	private UserUpgradationResponse createUpgradationInformationDTO() {
		UserUpgradationResponse response = new UserUpgradationResponse();
		UpgradationInformationDTO dto = new UpgradationInformationDTO();
		dto.setAction(Action.NO_ACTION_REQUIRED);
		dto.setSkip(Skip.SKIP_TRUE);
		dto.setState(State.SD_ACCOUNT_EXISTS_AND_ENABLED);
		dto.setUpgrade(Upgrade.NO_UPGRADE_REQRUIRED);
		response.setUpgradationInformation(dto);
		return response;
	}
	
	@Override
   @Timed
   @Marked
	public UserUpgradationResponse getUserUpgradeStatus(UserUpgradeByEmailRequest userUpgradeByEmailRequest, boolean isExternalCall) 
	         throws IMSMigrationHardDeclinedException {
		String emailId = userUpgradeByEmailRequest.getEmailId();
		if (!isValidDummyEmail(emailId)) {
			return createUpgradationInformationDTO();
		}
		UpgradationInformationDTO dto = new UpgradationInformationDTO();
		UserUpgradationResponse res = new UserUpgradationResponse();
		switch (getEnumFromDummyEmail(emailId)) {
		case SD_USER_MIGRATION_SUCCESS:
		case SD_USER_MIGRATION_FAILURE:
			dto = getMigrationStatusForSDUserMigration(emailId);
			break;
		case FC_USER_MIGRATION_SUCCESS:
		case FC_USER_MIGRATION_FAILURE:
			dto = getMigrationStatusForFCUserMigration(emailId);
			break;
		case SD_FC_USER_MIGRATION_SUCCESS:
		case SD_FC_USER_MIGRATION_FAILURE:
			dto = getMigrationStatusForSDFCUserMigration(emailId);
			break;
		case MIGRATED_SD_USER_MIGRATION_SUCCESS:
		case MIGRATED_SD_USER_MIGRATION_FAILURE:
			dto = getMigrationStatusForSDUserAlreadyMigrated(emailId);
			break;
		case MIGRATED_FC_USER_MIGRATION_SUCCESS:
		case MIGRATED_FC_USER_MIGRATION_FAILURE:
			dto = getMigrationStatusForFCUserAlreadyMigrated(emailId);
			break;
		case MIGRATED_SD_FC_USER_MIGRATION_SUCCESS:
		case MIGRATED_SD_FC_USER_MIGRATION_FAILURE:
			dto = getMigrationStatusForSDFCUserAlreadyMigrated(emailId);
			break;
		default:
		}
		res.setUpgradationInformation(dto);
		return res;
	}
	
	@Override
   @Timed
   @Marked
	public UpgradeUserResponse upgradeUser(UserUpgradeRequest userUpgradeRequest) {
		if (!isValidDummyEmail(userUpgradeRequest.getEmailId())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		}
		UpgradeUserResponse res = new UpgradeUserResponse();
		res.setSuccess(isEmailForSuccess(userUpgradeRequest.getEmailId())); 
		return res;
	}
	
	@Override
   @Timed
   @Marked
	public VerifyUpgradeUserResponse verifyUpgradeUser(VerifyUserUpgradeRequest verifyUserUpgradeRequest) {
		VerifyUpgradeUserResponse res  = new VerifyUpgradeUserResponse();
		EnumSet<DummyMigrationEmails> intermediateUpgradeSuccess = EnumSet.of(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_SUCCESS,
																		 DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_SUCCESS);
		
		EnumSet<DummyMigrationEmails> intermediateUpgradeFailure = EnumSet.of(DummyMigrationEmails.MIGRATED_SD_USER_MIGRATION_FAILURE,
				 DummyMigrationEmails.MIGRATED_FC_USER_MIGRATION_FAILURE);
		
		if(intermediateUpgradeSuccess.contains(getEnumFromDummyEmail(verifyUserUpgradeRequest.getEmailId()))) {
			res.setSuccess(true);
			
		} else if (intermediateUpgradeFailure.contains(getEnumFromDummyEmail(verifyUserUpgradeRequest.getEmailId()))) {
			res.setSuccess(false);
		} else {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
					IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
		}
		
		return res;
	}
	
	private boolean isEmailForSuccess(String email) {
		if (email.contains("success")) {
			return true;
		}
		return false;
	}

	private boolean isValidDummyEmail(String emailId) {
		if (emailId == null)
			return false;
		boolean found = false;
		for (DummyMigrationEmails email : DummyMigrationEmails.values()) {
			if (emailId.equals(email.getEmail())) {
				found = true;
				break;
			}
		}
		return found;
	}

	private DummyMigrationEmails getEnumFromDummyEmail(String emailId) {
		for (DummyMigrationEmails email : DummyMigrationEmails.values()) {
			if (emailId.equals(email.getEmail())) {
				return email;
			}
		}
		return null;
	}

	private UpgradationInformationDTO getMigrationStatusForSDUserMigration(
			String emailId) {
		return createUpgradeResponse(Action.NO_ACTION_REQUIRED,
									 Skip.SKIP_TRUE, 
									 State.SD_ACCOUNT_EXISTS_AND_ENABLED,
									 Upgrade.UPGRADE_RECOMMENDED, 
									 Wallet.WALLET_UPGRADE_MANDATORY);
	}

	private UpgradationInformationDTO getMigrationStatusForFCUserMigration(
			String emailId) {
		return createUpgradeResponse(Action.NO_ACTION_REQUIRED,
									 Skip.SKIP_TRUE, 
									 State.FC_ACCOUNT_EXISTS_AND_ENABLED,
									 Upgrade.UPGRADE_RECOMMENDED, 
									 Wallet.WALLET_UPGRADE_MANDATORY);

	}

	private UpgradationInformationDTO getMigrationStatusForSDFCUserMigration(
			String emailId) {
		return createUpgradeResponse(Action.NO_ACTION_REQUIRED,
									 Skip.SKIP_TRUE, 
									 State.SD_FC_ACCOUNT_EXISTS_AND_ENABLED,
									 Upgrade.UPGRADE_RECOMMENDED, 
									 Wallet.WALLET_UPGRADE_MANDATORY);
	}

	private UpgradationInformationDTO getMigrationStatusForSDUserAlreadyMigrated(
			String emailId) {
		if (getClientDetails() != null && getClientDetails().getMerchant().equals(Merchant.SNAPDEAL)) {
			return createUpgradeResponse(Action.NO_ACTION_REQUIRED,
					 Skip.SKIP_TRUE, 
					 State.SD_ACCOUNT_MIGRATED,
					 Upgrade.UPGRADE_COMPLETED, 
					 Wallet.WALLET_UPGRADE_NOT_REQUIRED);
			
		} else if (getClientDetails() != null && getClientDetails().getMerchant().equals(Merchant.FREECHARGE)) {
			return createUpgradeResponse(Action.VERIFY_OC_PASSWORD,
					 Skip.SKIP_TRUE, 
					 State.SD_ACCOUNT_MIGRATED,
					 Upgrade.LINK_FC_ACCOUNT, 
					 Wallet.WALLET_UPGRADE_NOT_REQUIRED);

		}
		else {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errMsg());
		}
		
	}

	private UpgradationInformationDTO getMigrationStatusForFCUserAlreadyMigrated(
			String emailId) {
		if (getClientDetails() != null && getClientDetails().getMerchant().equals(Merchant.SNAPDEAL)) {
			return createUpgradeResponse(Action.VERIFY_OC_PASSWORD,
					 Skip.SKIP_TRUE, 
					 State.FC_ACCOUNT_MIGRATED,
					 Upgrade.LINK_SD_ACCOUNT, 
					 Wallet.WALLET_UPGRADE_NOT_REQUIRED);
			
		} else if (getClientDetails() != null && getClientDetails().getMerchant().equals(Merchant.FREECHARGE)) {
			return createUpgradeResponse(Action.NO_ACTION_REQUIRED,
					 Skip.SKIP_TRUE, 
					 State.FC_ACCOUNT_MIGRATED,
					 Upgrade.UPGRADE_COMPLETED, 
					 Wallet.WALLET_UPGRADE_NOT_REQUIRED);

		}
		else {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.CLIENT_NOT_EXIST.errMsg());
		}

	}

	private UpgradationInformationDTO getMigrationStatusForSDFCUserAlreadyMigrated(
			String emailId) {
		return createUpgradeResponse(Action.NO_ACTION_REQUIRED,
									 Skip.SKIP_TRUE, 
									 State.OC_ACCOUNT_EXISTS, 
									 Upgrade.UPGRADE_COMPLETED,
									 Wallet.WALLET_UPGRADE_NOT_REQUIRED);

	}

	private UpgradationInformationDTO createUpgradeResponse(Action action,
			Skip skip, State state, Upgrade upgrade, Wallet wallet) {
		UpgradationInformationDTO dto = new UpgradationInformationDTO();
		dto.setAction(action);
		dto.setSkip(skip);
		dto.setState(state);
		dto.setUpgrade(upgrade);
		return dto;
	}

	@Override
   @Timed
   @Marked
	public VerifyOTPServiceResponse dummyVerifyOTP(
			VerifyOTPServiceRequest request) {
		String verifyOTPStatus = null;
		String message = null;
		String userId = null;
		String mobileNumber = null;
		FetchLatestOTPRequest fetchLatestOTPRequest = new FetchLatestOTPRequest();
		fetchLatestOTPRequest.setOtpId(request.getOtpId());
		fetchLatestOTPRequest.setClientId(request.getClientId());
		Optional<UserOTPEntity> currentOtpInfo = persistenceManager
				.getOTPFromId(fetchLatestOTPRequest);
		if (currentOtpInfo.isPresent()) {
			verifyOTPStatus = otpUtility.getSuccessMessage();
			message = otpUtility.getSuccessMessage();
			userId = currentOtpInfo.get().getUserId();
			mobileNumber = currentOtpInfo.get().getMobileNumber();
		}

		return prepareResponse(verifyOTPStatus, message, userId, mobileNumber);

	}

	private VerifyOTPServiceResponse prepareResponse(String status,
			String message, String userId, String mobileNumber) {
		return VerifyOTPServiceResponse.builder().status(status)
				.message(message).userId(userId).mobileNumber(mobileNumber)
				.build();
	}

   @Override
   public UpgradeStatus getIMSUserUpgradeStatus(
            UserUpgradeByEmailRequest userUpgradeByEmailRequest) {
      // TODO Auto-generated method stub
      return null;
   }

	@Override
	public UserUpgradationResponse upgradeSocialUser(String emailId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean upgradeUserStatusViaResetPassword(String emailId, UserDetailsDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VerifyUserWithLinkedStateResponse verifyUserWithLinkedState(VerifyUserWithLinkedStateRequest verifyUserLinkedRequest) {
	   throw new IMSServiceException(
               IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errCode(),
               IMSServiceExceptionCodes.FEATURE_NOT_SUPPORTED.errMsg());
	}

}
