package com.snapdeal.ims.otp.service.impl;

import com.google.common.base.Optional;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.dao.PersistenceManager;
import com.snapdeal.ims.otp.entity.FreezeAccountEntity;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.DropUserFromFreezeRequest;
import com.snapdeal.ims.otp.internal.request.UpdateInvalidAttemptsRequest;
import com.snapdeal.ims.otp.internal.request.UpdateOTPStatusRequest;
import com.snapdeal.ims.otp.internal.response.FrozenAccountResponse;
import com.snapdeal.ims.otp.service.IOTPGenerator;
import com.snapdeal.ims.otp.service.IOTPValidator;
import com.snapdeal.ims.otp.types.OTPStatus;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.otp.util.OtpEncryptionUtility;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OTPValidatorImpl implements IOTPValidator {
	
	@Autowired
	private PersistenceManager persistenceManager;

	@Autowired
	private OTPUtility otpUtility;

	@Autowired
	private IOTPGenerator otpGenerator;

	@Override
	public int verify(Optional<UserOTPEntity> currentOtpInfo,
			VerifyOTPServiceRequest request,boolean validOTPCall) {
		int status;
		FrozenAccountResponse frozenAccountResponse = isUserFrozen(currentOtpInfo
				.get());
		if (activeOrNot(currentOtpInfo)) {
			UserOTPEntity otpinfo = currentOtpInfo.get();
			boolean isOTPEncryptionEnabled = Boolean
					.valueOf(Configuration
							.getGlobalProperty(ConfigurationConstants.OTP_ENCRYPTION_ENABLED));
			if(otpinfo.getOtp().length()>4 && isOTPEncryptionEnabled) {
				otpinfo.setOtp(OtpEncryptionUtility.decryptOTP(otpinfo.getOtp()));
			}
			if (currentOtpInfo.get().getOtp()
					.equalsIgnoreCase(request.getOtp())) {
				if(!validOTPCall){
					updateOTPStatus(currentOtpInfo);
				}
				if (frozenAccountResponse.isStatus() == true
						&& frozenAccountResponse.getRequestType().equals(
								OtpConstants.FROZEN_REASON_RESEND_ATTEMPTS)) {
					deleteFrozenUser(currentOtpInfo.get());
				}
				status = 200; // OTP matched
				return status;
			} else {
				// increase invalid attempts
				updateInvalidAttempts(currentOtpInfo);
				// if invlaid attempts crossess maximum
				if (currentOtpInfo.get().getInvalidAttempts() >= otpUtility
						.getInvalidAttemptsLimit()) {
					// if user is in already frozen table the change attempts to
					// invalid attempts.
					
					if (frozenAccountResponse.isStatus() == true
							&& frozenAccountResponse.getRequestType().equals(
									OtpConstants.FROZEN_REASON_RESEND_ATTEMPTS)) {
						updateBlockUser(currentOtpInfo.get());
					} else {
						// if not then block him with reason invalid_attempts.
						blockUser(currentOtpInfo.get());
					}

				}
				log.error("otp number is wrong");
				status = 201;
				if(validOTPCall){
					return status;
				}
				throw new IMSServiceException(
						IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errCode(),
						IMSServiceExceptionCodes.INVALID_OTP_ENTERED.errMsg());
			}
		} else {
			// throwing exception in case of OTP is not in ACTIVE state
			status=201;
			if(validOTPCall){
				return status;
			}
		   throw new IMSServiceException(
		            IMSServiceExceptionCodes.INVALID_OTP_ID.errCode(),
		            IMSServiceExceptionCodes.INVALID_OTP_ID.errMsg());
		}
	}

	private boolean activeOrNot(Optional<UserOTPEntity> currentOtpInfo) {
		String state = otpUtility.getOTPState(currentOtpInfo).toString();
		if (state.equalsIgnoreCase("ACTIVE")
				|| state.toString().equalsIgnoreCase("IN_THRESHOLD")
				|| state.equalsIgnoreCase("IN_EXPIRY")) {
			return true;
		}

		return false;
	}

	private void updateBlockUser(UserOTPEntity otp) {
		FreezeAccountEntity frozenAccountInfo = new FreezeAccountEntity();
		frozenAccountInfo.setUserId(otp.getUserId());
		frozenAccountInfo.setExpiryTime(new DateTime().plusMinutes(
				otpUtility.getBlockDurationInMins()).toDate());
		frozenAccountInfo.setOtpType(otp.getOtpType());
		frozenAccountInfo
				.setFreezeReason(OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS);
		persistenceManager.updateFreezeUser(frozenAccountInfo);
	}

	private void deleteFrozenUser(UserOTPEntity otp) {
		DropUserFromFreezeRequest dropUserFromFreezeRequest = new DropUserFromFreezeRequest();
		dropUserFromFreezeRequest.setUserId(otp.getUserId());
		dropUserFromFreezeRequest.setOtpType(OTPPurpose.valueOf(otp.getOtpType()));
		dropUserFromFreezeRequest.setCurrentTime(new Date());
		persistenceManager.dropFreezedUser(dropUserFromFreezeRequest);

	}

	private void updateInvalidAttempts(Optional<UserOTPEntity> currentOtpInfo) {
		UserOTPEntity otpInfo = currentOtpInfo.get();
		UpdateInvalidAttemptsRequest updateInvalidAttemptsRequest = new UpdateInvalidAttemptsRequest();
		updateInvalidAttemptsRequest.setOtpId(otpInfo.getOtpId());
		updateInvalidAttemptsRequest.setOtpStatus(otpInfo.getOtpStatus());
		updateInvalidAttemptsRequest.setClientId(otpInfo.getClientId());
		updateInvalidAttemptsRequest.setInvalidAttempts(otpInfo
				.getInvalidAttempts());
		updateInvalidAttemptsRequest
				.setReason(OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS);
		persistenceManager
				.incrementInvalidAttempts(updateInvalidAttemptsRequest);
	}

   private void updateOTPStatus(Optional<UserOTPEntity> currentOtpInfo) {
      UserOTPEntity otpInfo = currentOtpInfo.get();
      UpdateOTPStatusRequest otpStatusRequest = new UpdateOTPStatusRequest();
      otpStatusRequest.setOtpStatusCurrent(otpInfo.getOtpStatus());
      otpStatusRequest.setOtpId(otpInfo.getOtpId());
      otpStatusRequest.setOtpType(OTPPurpose.valueOf(otpInfo.getOtpType()));
      if (otpStatusRequest.getOtpStatusCurrent() == OTPStatus.ACTIVE) {
            otpStatusRequest.setOtpStatusExpected(OTPStatus.VERIFIED);
      }
      /*if (!otpStatusRequest.getOtpType().equals(OTPPurpose.LINK_ACCOUNT)
               && otpStatusRequest.getOtpStatusCurrent() == OTPStatus.ACTIVE) {
         otpStatusRequest.setOtpStatusExpected(OTPStatus.VERIFIED);
      } else if (otpStatusRequest.getOtpType().equals(OTPPurpose.LINK_ACCOUNT)
               && otpStatusRequest.getOtpStatusCurrent() == OTPStatus.ACTIVE) {
         otpStatusRequest.setOtpType(OTPPurpose.FORGOT_PASSWORD);
      }*/
      persistenceManager.updateCurrentOTPStatus(otpStatusRequest);
   }

	private FrozenAccountResponse isUserFrozen(UserOTPEntity otp) {

		FrozenAccountResponse frozenAccountResponse = otpGenerator
				.getFreezdUser(otp);
		if (frozenAccountResponse.isStatus() == true
				&& frozenAccountResponse.getRequestType().equalsIgnoreCase(
						OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS)) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.VERIFY_OTP_LIMIT_BREACHED.errCode(),
                  MessageFormat.format(IMSServiceExceptionCodes.VERIFY_OTP_LIMIT_BREACHED.errMsg(),
                           frozenAccountResponse.getRemainingMinutes() / 1));
		}
		return frozenAccountResponse;
	}

	private void blockUser(UserOTPEntity otp) {
		otpGenerator
				.blockUser(otp, OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS);
	}
}
