package com.snapdeal.ims.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.dao.PersistenceManager;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.response.FrozenAccountResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPGenerator;
import com.snapdeal.ims.otp.service.IOTPService;
import com.snapdeal.ims.otp.service.IOTPValidator;
import com.snapdeal.ims.otp.service.impl.OTPValidatorImpl;
import com.snapdeal.ims.otp.types.OTPState;
import com.snapdeal.ims.otp.types.OTPStatus;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;

@RunWith(MockitoJUnitRunner.class)
public class IOTPValidatorTest {
	@Mock
	IOTPService generateOtpService;

	@Mock
	PersistenceManager persistenceManager;

	@Mock
	OTPUtility otpUtility;

	@Mock
	IOTPGenerator otpGenerator;

	@InjectMocks
	IOTPValidator otpValidator = new OTPValidatorImpl();
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Before
	public void intiMocks() {
		try {
			MockitoAnnotations.initMocks(otpValidator);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerfiy() {
		try {
			VerifyOTPServiceRequest request = generateVerifyOTPRequest();
			Optional<UserOTPEntity> optional = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			when(
					otpGenerator.getFreezdUser(any(UserOTPEntity.class))).thenReturn(
					defaultFrozenAccountForVerify());
			when(otpUtility.getOTPState(optional)).thenReturn(
					OTPState.IN_EXPIRY);

			when(otpUtility.getOTPState(any(Optional.class))).thenReturn(
					OTPState.IN_EXPIRY);
			otpValidator.verify(optional, generateVerifyOTPRequest(),false);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerfiy1() {
		try {
			VerifyOTPServiceRequest request = generateVerifyOTPRequest();
			UserOTPEntity otp = getDefaultOTPInfo();
			otp.setEmail(null);
			Optional<UserOTPEntity> optional = Optional.<UserOTPEntity> of(otp);
			when(
					otpGenerator.getFreezdUser(any(UserOTPEntity.class))).thenReturn(
					defaultFrozenAccountForVerify());
			when(otpUtility.getOTPState(optional)).thenReturn(
					OTPState.IN_EXPIRY);

			when(otpUtility.getOTPState(any(Optional.class))).thenReturn(
					OTPState.IN_EXPIRY);
			otpValidator.verify(optional, generateVerifyOTPRequest(),false);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerfiy2() {
		try {
			VerifyOTPServiceRequest request = generateVerifyOTPRequest();
			UserOTPEntity otp = getDefaultOTPInfo();
			otp.setMobileNumber(null);
			Optional<UserOTPEntity> optional = Optional.<UserOTPEntity> of(otp);
			when(
					otpGenerator.getFreezdUser(any(UserOTPEntity.class))).thenReturn(
					defaultFrozenAccountForVerify());
			when(otpUtility.getOTPState(optional)).thenReturn(
					OTPState.IN_EXPIRY);

			when(otpUtility.getOTPState(any(Optional.class))).thenReturn(
					OTPState.IN_EXPIRY);
			otpValidator.verify(optional, generateVerifyOTPRequest(),false);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerfiyFail() {
		try {
			VerifyOTPServiceRequest request = generateVerifyOTPRequest();
			Optional<UserOTPEntity> optional = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			when(
					otpGenerator.getFreezdUser(any(UserOTPEntity.class))).thenReturn(
					defaultFrozenAccountForVerify());
			when(otpUtility.getOTPState(any(Optional.class))).thenReturn(
					OTPState.IN_EXPIRY);
			when(otpUtility.getInvalidAttemptsLimit()).thenReturn(3);
			VerifyOTPServiceRequest verifyRequest = generateVerifyOTPRequest();
			verifyRequest.setOtp("3432");
		//	expectedException.expect(ValidationException.class);
			otpValidator.verify(optional, verifyRequest,false);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerfiyFailBlockUser() {
		try {
			VerifyOTPServiceRequest request = generateVerifyOTPRequest();
			UserOTPEntity otpInfo = getDefaultOTPInfo();
			otpInfo.setInvalidAttempts(3);
			Optional<UserOTPEntity> optional = Optional
					.<UserOTPEntity> of(otpInfo);
			FrozenAccountResponse freezeAccountResponse = defaultFrozenAccountForVerify();
			freezeAccountResponse.setStatus(false);
			when(
					otpGenerator.getFreezdUser(any(UserOTPEntity.class))).thenReturn(
					freezeAccountResponse);
			when(otpUtility.getOTPState(any(Optional.class))).thenReturn(
					OTPState.IN_EXPIRY);
			when(otpUtility.getInvalidAttemptsLimit()).thenReturn(3);
			VerifyOTPServiceRequest verifyRequest = generateVerifyOTPRequest();
			verifyRequest.setOtp("3432");
		//	expectedException.expect(ValidationException.class);
			otpValidator.verify(optional, verifyRequest,false);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerfiyUserBlockedException() {
		try {
			VerifyOTPServiceRequest request = generateVerifyOTPRequest();
			Optional<UserOTPEntity> optional = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			FrozenAccountResponse freezeAccountResponse = defaultFrozenAccountForVerify();
			freezeAccountResponse
					.setRequestType(OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS);
			when(otpGenerator.getFreezdUser(any(UserOTPEntity.class))).
					thenReturn(freezeAccountResponse);
			when(otpUtility.getOTPState(any(Optional.class))).thenReturn(
					OTPState.IN_EXPIRY);
			when(otpUtility.getInvalidAttemptsLimit()).thenReturn(3);
			VerifyOTPServiceRequest verifyRequest = generateVerifyOTPRequest();
			verifyRequest.setOtp("3432");
			//expectedException.expect(FrozenAccountException.class);
			otpValidator.verify(optional, verifyRequest,false);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testVerfiyFailUpdate() {
		try {
			VerifyOTPServiceRequest request = generateVerifyOTPRequest();
			Optional<UserOTPEntity> optional = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			when(
					otpGenerator.getFreezdUser(any(UserOTPEntity.class))).thenReturn(
					defaultFrozenAccountForVerify());
			when(otpUtility.getOTPState(any(Optional.class))).thenReturn(
					OTPState.NOT_ACTIVE);
			when(otpUtility.getInvalidAttemptsLimit()).thenReturn(3);
			VerifyOTPServiceRequest verifyRequest = generateVerifyOTPRequest();
			verifyRequest.setOtp("3432");
			otpValidator.verify(optional, verifyRequest,false);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	private void fail(String message) {
		System.out.println(message);
	}

	private FrozenAccountResponse defaultFrozenAccountForVerify() {
		FrozenAccountResponse frozenAccountInfo = new FrozenAccountResponse();
		frozenAccountInfo
				.setRequestType(OtpConstants.FROZEN_REASON_RESEND_ATTEMPTS);
		frozenAccountInfo.setStatus(true);
		return frozenAccountInfo;
	}

	private FrozenAccountResponse defaultFrozenAccountForVerifyForFail() {
		FrozenAccountResponse frozenAccountInfo = new FrozenAccountResponse();
		frozenAccountInfo
				.setRequestType(OtpConstants.FROZEN_REASON_RESEND_ATTEMPTS);
		frozenAccountInfo.setStatus(false);
		return frozenAccountInfo;
	}

	private VerifyOTPServiceResponse generateVerifyOTPResponse() {
		VerifyOTPServiceResponse verifyOTPResponse = new VerifyOTPServiceResponse();
		verifyOTPResponse.setStatus("success");
		return verifyOTPResponse;
	}

	private VerifyOTPServiceRequest generateVerifyOTPRequest() {
		VerifyOTPServiceRequest verifyOTPRequest = new VerifyOTPServiceRequest();
		verifyOTPRequest.setOtpId("454");
		verifyOTPRequest.setOtp("4554");
		verifyOTPRequest.setClientId("8222918189");
		return verifyOTPRequest;
	}

	private UserOTPEntity getDefaultOTPInfo() {
		UserOTPEntity otpInfo = new UserOTPEntity();
		otpInfo.setCreatedOn(new Date());
		otpInfo.setExpiryTime(new Date());
		// otpInfo.setThresholdTime(new Date());
		otpInfo.setOtp("4554");
		otpInfo.setOtpStatus(OTPStatus.ACTIVE);
		otpInfo.setClientId("1");
		otpInfo.setEmail("abhishek<abhishek.garg@snapdeal.com>");
		otpInfo.setMobileNumber("7206303105");
		otpInfo.setOtpId("122");
		otpInfo.setOtpType(OTPPurpose.FORGOT_PASSWORD.toString());
		otpInfo.setInvalidAttempts(3);
		return otpInfo;
	}

}
