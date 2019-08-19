package com.snapdeal.ims.otp.service.impl;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.enums.SendOTPByEnum;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.dao.PersistenceManager;
import com.snapdeal.ims.otp.entity.FreezeAccountEntity;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.GetFrozenAccount;
import com.snapdeal.ims.otp.internal.request.UpdateOTPStatusRequest;
import com.snapdeal.ims.otp.internal.response.FrozenAccountResponse;
import com.snapdeal.ims.otp.service.INotifier;
import com.snapdeal.ims.otp.service.IOTPGenerator;
import com.snapdeal.ims.otp.service.IOtp;
import com.snapdeal.ims.otp.types.OTPState;
import com.snapdeal.ims.otp.types.OTPStatus;
import com.snapdeal.ims.otp.util.EmailUtility;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.otp.util.OtpEncryptionUtility;
import com.snapdeal.ims.otp.util.RandomNumberGenerator;
import com.snapdeal.ims.request.AbstractOTPServiceRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;

@Slf4j
@Service
public class OTPGeneratorImpl implements IOTPGenerator {

	@Autowired
	private RandomNumberGenerator randomNumberGenerator;

	@Autowired
	private PersistenceManager persistenceManager;

	@Autowired(required = false)
	private INotifier notifier;

	@Autowired
	private OTPUtility otpUtility;

	@Override
	public void blockUser(UserOTPEntity otp, String reason) {
		FreezeAccountEntity frozenAccountInfo = new FreezeAccountEntity();
		frozenAccountInfo.setExpiryTime(new DateTime().plusMinutes(
				otpUtility.getBlockDurationInMins()).toDate());
		frozenAccountInfo.setOtpType(otp.getOtpType());
		frozenAccountInfo.setFreezeReason(reason);
		frozenAccountInfo.setUserId(otp.getUserId());
		frozenAccountInfo.setIsdeleted("false");
		persistenceManager.freezeUser(frozenAccountInfo);
	}

	@Override
	public IOtp generate(GenerateOTPServiceRequest request) {
		Optional<UserOTPEntity> otpOption = persistenceManager
				.getLatestOTP(request);

		if (otpOption.isPresent()) {
			UserOTPEntity otpinfo = otpOption.get();
			boolean isOTPEncryptionEnabled = Boolean
					.valueOf(Configuration
							.getGlobalProperty(ConfigurationConstants.OTP_ENCRYPTION_ENABLED));
			if(otpinfo.getOtp().length()>4 && isOTPEncryptionEnabled) {
				otpinfo.setOtp(OtpEncryptionUtility.decryptOTP(otpinfo.getOtp()));
			}
			OTPState currentOtpState = otpUtility.getOTPState(otpOption);
			switch (currentOtpState) {
			case VERIFIED:
			case NOT_ACTIVE:
				updateOTPStatus(otpinfo);
				return toOTP(generateNewOTPInfo(request));
			case IN_EXPIRY:
				val otp = toOTP(updateResendAttempts(otpOption, request));
				otp.incrementSendCount();
				return otp;
			default:
				throw new InternalServerException(
						IMSServiceExceptionCodes.INVALID_PATH.errCode(),
						IMSServiceExceptionCodes.INVALID_PATH.errMsg());
			}
		} else {
			return toOTP(generateNewOTPInfo(request));
		}

	}

	// called by resend attempt
	@Override
	public FrozenAccountResponse getFreezdUser(UserOTPEntity otp) {
		GetFrozenAccount getFrozenAccount = new GetFrozenAccount();
		getFrozenAccount.setUserId(otp.getUserId());
		getFrozenAccount.setOtpType(OTPPurpose.valueOf(otp.getOtpType()));
		FrozenAccountResponse frozenAccountResponse = getFrozenOTPAccount(getFrozenAccount);

		return frozenAccountResponse;
	}

	@Override
	public IOtp reSendOTP(Optional<UserOTPEntity> optionalOtpInfo) {

		// check user is in frozen table or not
		UserOTPEntity otpinfo = optionalOtpInfo.get();
		if(otpinfo.getOtp().length()>4){
			otpinfo.setOtp(OtpEncryptionUtility.decryptOTP(otpinfo.getOtp()));
		}
		
		FrozenAccountResponse frozenAccountResponse = getFreezdUser(otpinfo);
		if (frozenAccountResponse.isStatus() == true) {

			if (frozenAccountResponse.getRequestType().equalsIgnoreCase(
					OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS)) {
				throw new IMSServiceException(
						IMSServiceExceptionCodes.VERIFY_OTP_LIMIT_BREACHED
						.errCode(),
						MessageFormat
						.format(IMSServiceExceptionCodes.VERIFY_OTP_LIMIT_BREACHED
								.errMsg(), frozenAccountResponse
								.getRemainingMinutes() / 1));
			} else {
				throw new IMSServiceException(
						IMSServiceExceptionCodes.OTP_LIMIT_BREACHED.errCode(),
						MessageFormat.format(
								IMSServiceExceptionCodes.OTP_LIMIT_BREACHED
								.errMsg(), frozenAccountResponse
								.getRemainingMinutes() / 1));
			}
		}

		OTPState currentOtpState = otpUtility.getOTPState(optionalOtpInfo);
		// log.debug(OtpConstants.OTP_STATE + "" + currentOtpState);

		switch (currentOtpState) {
		case DOES_NOT_EXIST:
			throw new IMSServiceException(
					IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
					IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg());
		case NOT_ACTIVE:
			updateOTPStatus(otpinfo);
			return toOTP(generateNewOTPInfo(generateGenerateOTPRequest(otpinfo)));
		case IN_EXPIRY:
			val otp = toOTP(updateResendAttempts(otpinfo));
			otp.incrementSendCount();
			return otp;
		default:
			throw new InternalServerException(
					IMSServiceExceptionCodes.INVALID_PATH.errCode(),
					IMSServiceExceptionCodes.INVALID_PATH.errMsg());
		}
	}

	// called by GenerateOTP
	@Override
	public void validateUserFreeze(GenerateOTPServiceRequest request) {
		GetFrozenAccount getFrozenAccount = new GetFrozenAccount();
		getFrozenAccount.setUserId(request.getUserId());
		getFrozenAccount.setOtpType(request.getOtpType());
		FrozenAccountResponse frozenAccountResponse = getFrozenOTPAccount(getFrozenAccount);
		if (frozenAccountResponse.isStatus() == true) {
			if (frozenAccountResponse.getRequestType().equalsIgnoreCase(
					OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS)) {
				throw new IMSServiceException(
						IMSServiceExceptionCodes.VERIFY_OTP_LIMIT_BREACHED
						.errCode(),
						MessageFormat
						.format(IMSServiceExceptionCodes.VERIFY_OTP_LIMIT_BREACHED
								.errMsg(), frozenAccountResponse
								.getRemainingMinutes() / 1));
			} else {
				throw new IMSServiceException(
						IMSServiceExceptionCodes.OTP_LIMIT_BREACHED.errCode(),
						MessageFormat.format(
								IMSServiceExceptionCodes.OTP_LIMIT_BREACHED
								.errMsg(), frozenAccountResponse
								.getRemainingMinutes() / 1));
			}

		}
	}

	private void updateOTPStatus(UserOTPEntity otpinfo) {
		UpdateOTPStatusRequest request = new UpdateOTPStatusRequest();
		request.setOtpId(otpinfo.getOtpId());
		request.setOtpStatusCurrent(OTPStatus.ACTIVE);
		request.setOtpStatusExpected(OTPStatus.DELETED);
		request.setOtpType(OTPPurpose.valueOf(otpinfo.getOtpType()));
		persistenceManager.updateCurrentOTPStatus(request);
	}

	private FrozenAccountResponse calculateFrozenAccountResponse(
			Optional<FreezeAccountEntity> outdao,
			GetFrozenAccount getFrozenAccount) {

		FrozenAccountResponse frozenAccountResponse = new FrozenAccountResponse();
		// if not present then return response with status as false;

		if (outdao.isPresent() == false) {
			frozenAccountResponse.setStatus(false);
			return frozenAccountResponse;
		} else {
			frozenAccountResponse
			.setRequestType(outdao.get().getFreezeReason());
			Date currentDate = new Date();
			// check request type is same and also active.set status
			// true///means present.
			if (currentDate.before((outdao.get()).getExpiryTime())) {

				long remainingTime = outdao.get().getExpiryTime().getTime()
						- Math.abs(System.currentTimeMillis());
				long remainingMintues = (remainingTime) / (1000 * 60) + 1;
				frozenAccountResponse.setRemainingMinutes(remainingMintues);
				frozenAccountResponse.setStatus(true);
				return frozenAccountResponse;
			}

		}
		frozenAccountResponse.setStatus(false);
		return frozenAccountResponse;
	}

	private GenerateOTPServiceRequest generateGenerateOTPRequest(
			UserOTPEntity otp) {
		GenerateOTPServiceRequest generateOTPRequest = new GenerateOTPServiceRequest();
		generateOTPRequest.setClientId(otp.getClientId());
		generateOTPRequest.setEmailId(otp.getEmail());
		generateOTPRequest.setMobileNumber(otp.getMobileNumber());
		generateOTPRequest.setUserId(otp.getUserId());
		generateOTPRequest.setOtpType(OTPPurpose.valueOf(otp.getOtpType()));
		generateOTPRequest.setToken(otp.getToken());
		generateOTPRequest.setSendOtpBy(SendOTPByEnum.forValue(otp
				.getSendOTPBy()));
		// generateOTPRequest.setToekn(otp.getToken())
		return generateOTPRequest;
	}

	// used by verify otp service and otp Generation
	private FrozenAccountResponse getFrozenOTPAccount(
			GetFrozenAccount getGrozenAccount) {

		Optional<FreezeAccountEntity> outdao = persistenceManager
				.getFreezedAccount(getGrozenAccount);

		return calculateFrozenAccountResponse(outdao, getGrozenAccount);
	}

	private UserOTPEntity updateResendAttempts(
			Optional<UserOTPEntity> currentOtpInfo,
			AbstractOTPServiceRequest request) {
		UserOTPEntity otp = currentOtpInfo.get();
		if (request instanceof GenerateOTPServiceRequest) {
			otp.setMobileNumber(((GenerateOTPServiceRequest) request)
					.getMobileNumber());
			otp.setEmail(((GenerateOTPServiceRequest) request).getEmailId());
		}
		updateResendAttempts(otp);
		return otp;
	}

	private IOtp toOTP(UserOTPEntity generateNewOTP) {
		return new OTPImpl(generateNewOTP, persistenceManager, notifier);
	}

	private UserOTPEntity updateResendAttempts(UserOTPEntity otp) {
		if (otp.getResendAttempts() >= otpUtility.getReSendAttemptsLimit()) {
			blockUser(otp, OtpConstants.FROZEN_REASON_RESEND_ATTEMPTS);
		}

		return otp;
	}

	private UserOTPEntity generateNewOTPInfo(GenerateOTPServiceRequest request) {

		// TODO -ANAND : Need to change this logic and apply encryption
		String otpStr = randomNumberGenerator.get();
		UserOTPEntity otpInfo = new UserOTPEntity();
		String otpId = UUID.randomUUID().toString();
		otpInfo.setOtpId(otpId);
		otpInfo.setUserId(request.getUserId());
		otpInfo.setOtpStatus(OTPStatus.ACTIVE);
		otpInfo.setClientId(request.getClientId());
		otpInfo.setCreatedOn(new Date());
		otpInfo.setEmail(EmailUtility.toLowerCaseEmail(request.getEmailId()));
		otpInfo.setExpiryTime(new DateTime().plusMinutes(
				otpUtility.getExpiryDurationInMins()).toDate());
		otpInfo.setMobileNumber(request.getMobileNumber());
		otpInfo.setOtp(otpStr);
		otpInfo.setOtpStatus(OTPStatus.ACTIVE);
		otpInfo.setOtpType(request.getOtpType().toString());
		otpInfo.setToken(request.getToken());
		otpInfo.setOtp(otpStr);
		if (request.getSendOtpBy() != null) {
			otpInfo.setSendOTPBy(request.getSendOtpBy().toString());
		}

		persistenceManager.saveOTP(otpInfo);
		// otpInfo.setOtpId(getOTPId(otpInfo));
		return otpInfo;
	}

	private String getOTPId(UserOTPEntity otp) {
		Optional<UserOTPEntity> otpOptional = persistenceManager.getOtpId(otp);
		if (otpOptional.isPresent()) {
			return otpOptional.get().getOtpId() + "";
		}
		log.error("otp not found for this information");
		throw new IMSServiceException(
				IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
				IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg());

	}
}