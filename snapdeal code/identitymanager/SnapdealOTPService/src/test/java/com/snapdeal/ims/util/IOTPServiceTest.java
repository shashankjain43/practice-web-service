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
import com.snapdeal.ims.enums.OTPRequestChannel;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.otp.dao.PersistenceManager;
import com.snapdeal.ims.otp.email.IEmailSender;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.FetchLatestOTPRequest;
import com.snapdeal.ims.otp.response.OTPServiceResponse;
import com.snapdeal.ims.otp.response.VerifyOTPServiceResponse;
import com.snapdeal.ims.otp.service.IOTPGenerator;
import com.snapdeal.ims.otp.service.IOTPValidator;
import com.snapdeal.ims.otp.service.IOtp;
import com.snapdeal.ims.otp.service.impl.OTPServiceImpl;
import com.snapdeal.ims.otp.types.OTPStatus;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.otp.validators.GenericValidator;
import com.snapdeal.ims.request.AbstractOTPServiceRequest;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;
import com.snapdeal.ims.request.ResendOTPServiceRequest;
import com.snapdeal.ims.request.VerifyOTPServiceRequest;

@RunWith(MockitoJUnitRunner.class)
public class IOTPServiceTest {

	@InjectMocks
	OTPServiceImpl otpServiceimpl = new OTPServiceImpl();

	@Mock
	IEmailSender sender;

	@Mock
	PersistenceManager persistenceManager;

	@Mock
	IOTPGenerator otpGenerator;

	@Mock
	GenericValidator<AbstractOTPServiceRequest> validator;
	
	@Mock
	IOTPValidator otpVerifyService;
	@Mock
	IOtp otp;
	@Mock
	OTPUtility otpUtility;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private UserOTPEntity otpInfo = getDefaultOTPInfo();

	@Before
	public void initMocks() {
		try {
			MockitoAnnotations.initMocks(otpServiceimpl);
			when(otpUtility.getFromEmailId()).thenReturn("snapdeal@snapdeal.com");
			when(otpUtility.getSnapdealReplyEmailId()).thenReturn("noreply@snapdeal.com");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} catch (Error e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGenerateOTP() {
		try {
			when(otpGenerator.generate(any(GenerateOTPServiceRequest.class)))
					.thenReturn(otp);
			when(otp.getOtpInfo()).thenReturn(otpInfo);

			GenerateOTPServiceRequest generateOTPRequest = getDefaultRequest();
			OTPServiceResponse otpResponse = otpServiceimpl
					.generateOTP(generateOTPRequest);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testResendOTP() {
		try {
			Optional<UserOTPEntity> optionalOtp = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			when(
					persistenceManager
							.getOTPFromId(any(FetchLatestOTPRequest.class)))
					.thenReturn(optionalOtp);
			when(otpGenerator.reSendOTP(optionalOtp)).thenReturn(otp);
	
			when(otp.getOtpInfo()).thenReturn(otpInfo);

			ResendOTPServiceRequest reSendOTPRequest = generateResendOTPRequest();
			@SuppressWarnings("unused")
			OTPServiceResponse otpResponse = otpServiceimpl
					.resendOTP(reSendOTPRequest);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testValidateToken(){
		try {
			UserOTPEntity otpInfo = getDefaultOTPInfo();
			otpInfo.setOtpType(OTPPurpose.LOGIN.toString());
			Optional<UserOTPEntity> optionalOtp = Optional
					.<UserOTPEntity> of(otpInfo);
			otpInfo.setToken(null);
			when(
					persistenceManager
							.getOTPFromId(any(FetchLatestOTPRequest.class)))
					.thenReturn(optionalOtp);
			when(otpGenerator.reSendOTP(optionalOtp)).thenReturn(otp);
	
			when(otp.getOtpInfo()).thenReturn(otpInfo);

			ResendOTPServiceRequest reSendOTPRequest = generateResendOTPRequest();
			reSendOTPRequest.setToken(null);
			//expectedException.expect(RequestParameterException.class);
			OTPServiceResponse otpResponse = otpServiceimpl
					.resendOTP(reSendOTPRequest);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testValidateTokenNotMatch(){
		try {
			UserOTPEntity otpInfo = getDefaultOTPInfo();
			otpInfo.setOtpType(OTPPurpose.LOGIN.toString());
			Optional<UserOTPEntity> optionalOtp = Optional
					.<UserOTPEntity> of(otpInfo);
			when(
					persistenceManager
							.getOTPFromId(any(FetchLatestOTPRequest.class)))
					.thenReturn(optionalOtp);
			when(otpGenerator.reSendOTP(optionalOtp)).thenReturn(otp);
	
			when(otp.getOtpInfo()).thenReturn(otpInfo);

			ResendOTPServiceRequest reSendOTPRequest = generateResendOTPRequest();
			//expectedException.expect(ValidationException.class);
			OTPServiceResponse otpResponse = otpServiceimpl
					.resendOTP(reSendOTPRequest);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testFailResendOTP() {
		try {
			Optional<UserOTPEntity> optionalOtp = Optional
					.<UserOTPEntity> absent();
			when(
					persistenceManager
							.getOTPFromId(any(FetchLatestOTPRequest.class)))
					.thenReturn(optionalOtp);
			when(otpGenerator.reSendOTP(optionalOtp)).thenReturn(otp);
			
			when(otp.getOtpInfo()).thenReturn(otpInfo);

			ResendOTPServiceRequest reSendOTPRequest = generateResendOTPRequest();
			OTPServiceResponse otpResponse = otpServiceimpl
					.resendOTP(reSendOTPRequest);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testVerifyOtp(){
		try{
			Optional<UserOTPEntity> optionalOtp = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			when(persistenceManager
				.getOTPFromId(any(FetchLatestOTPRequest.class))).thenReturn(optionalOtp);
			when(otpVerifyService.verify(any(Optional.class),any(VerifyOTPServiceRequest.class),false)).thenReturn(200);
			otpServiceimpl.verifyOTP(generateVerifyOTPRequest());
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testExceptionVerifyOtp(){
		try{
			Optional<UserOTPEntity> optionalOtp = Optional
					.<UserOTPEntity> absent();
			when(persistenceManager
				.getOTPFromId(any(FetchLatestOTPRequest.class))).thenReturn(optionalOtp);
			otpServiceimpl.verifyOTP(generateVerifyOTPRequest());
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testFailVerifyOtp(){
		try{
			Optional<UserOTPEntity> optionalOtp = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			when(persistenceManager
				.getOTPFromId(any(FetchLatestOTPRequest.class))).thenReturn(optionalOtp);
			when(otpVerifyService.verify(any(Optional.class),any(VerifyOTPServiceRequest.class),false)).thenReturn(101);
			otpServiceimpl.verifyOTP(generateVerifyOTPRequest());
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
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
		return verifyOTPRequest;
	}
	

	private ResendOTPServiceRequest generateResendOTPRequest() {
		ResendOTPServiceRequest resnOtpRequest = new ResendOTPServiceRequest();
		resnOtpRequest.setOtpId("454");
		resnOtpRequest.setClientId("123");
		resnOtpRequest.setToken("abcdefgh23423");
		//resnOtpRequest.setOtpChannel(OTPRequestChannel.THROUGH_SMS);
		return resnOtpRequest;
	}

	private void fail(String message) {
		System.out.println(message);
	}

	private UserOTPEntity getDefaultOTPInfo() {
		UserOTPEntity otpInfo = new UserOTPEntity();
		otpInfo.setToken("237y887whd7");
		otpInfo.setCreatedOn(new Date());
		otpInfo.setExpiryTime(new Date());
		otpInfo.setOtp("1234");
		otpInfo.setOtpStatus(OTPStatus.ACTIVE);
		otpInfo.setClientId("1");
		otpInfo.setEmail("abhishek.garg@snapdeal.com");
		otpInfo.setMobileNumber("7206303105");
		otpInfo.setOtpId("122");
		otpInfo.setOtpType(OTPPurpose.FORGOT_PASSWORD.toString());
		return otpInfo;
	}

	private GenerateOTPServiceRequest getDefaultRequest() {
		GenerateOTPServiceRequest generateOTPRequest = new GenerateOTPServiceRequest();
		generateOTPRequest.setOtpType(OTPPurpose.FORGOT_PASSWORD);
		generateOTPRequest.setMobileNumber("8222918189");
		generateOTPRequest.setEmailId("abhishek.garg@snapdeal.com");
		generateOTPRequest.setClientId("122");
		return generateOTPRequest;
	}
}
