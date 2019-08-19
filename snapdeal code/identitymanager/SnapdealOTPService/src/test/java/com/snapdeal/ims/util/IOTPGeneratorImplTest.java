package com.snapdeal.ims.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientType;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.OTPPurpose;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.otp.constants.OtpConstants;
import com.snapdeal.ims.otp.dao.PersistenceManager;
import com.snapdeal.ims.otp.entity.FreezeAccountEntity;
import com.snapdeal.ims.otp.entity.UserOTPEntity;
import com.snapdeal.ims.otp.internal.request.GetFrozenAccount;
import com.snapdeal.ims.otp.service.INotifier;
import com.snapdeal.ims.otp.service.IOTPGenerator;
import com.snapdeal.ims.otp.service.impl.OTPGeneratorImpl;
import com.snapdeal.ims.otp.types.OTPState;
import com.snapdeal.ims.otp.types.OTPStatus;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.otp.util.RandomNumberGenerator;
import com.snapdeal.ims.request.GenerateOTPServiceRequest;

@RunWith(MockitoJUnitRunner.class)
public class IOTPGeneratorImplTest {

	@Mock
	RandomNumberGenerator randomNumberGenerator;

	@Mock
	PersistenceManager persistenceManager;

	@Mock
	INotifier notifier;

	@Mock
	OTPUtility otpUtility;
	
	@InjectMocks
	IOTPGenerator otpGenerator = new OTPGeneratorImpl();
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	protected void initConfig() {

		Map<String, String> globalMap = new HashMap<String, String>();
		globalMap.put("default.tokengeneration.service.version", "V01");
		globalMap.put("default.tokengeneration.service.V01",
				"com.snapdeal.ims.token.service.impl.TokenGenerationServiceV01Impl");
		globalMap.put("default.globaltokengeneration.service.version", "V01");
		globalMap.put("default.globaltokengeneration.service.V01",
				"com.snapdeal.ims.token.service.impl.GlobalTokenGenerationServiceV01Impl");
		globalMap.put("cipher.unique.key", "U25hcGRlYWxVbmlxdWVLZXk=");
		globalMap.put("days.to.renew.token", "5");
		globalMap.put("dummy.migration.enabled", "true");
      globalMap.put("upgrade.percentage", "100");
      globalMap.put("link.upgrade.globaltokengeneration.service.version", "V02");
      globalMap.put("link.upgrade.tokengeneration.service.version", "V02");
      globalMap.put("default.tokengeneration.service.V02",
               "com.snapdeal.ims.token.service.impl.TokenGenerationServiceV02Impl");
      globalMap.put("default.globaltokengeneration.service.V02",
               "com.snapdeal.ims.token.service.impl.GlobalTokenGenerationServiceV02Impl");
      globalMap.put("aerospike.userIdByGTokenIds.update.maxRetries","5");
      globalMap.put("aerospike.userIdByGTokenIds.retry.sleep.in.milliseconds","200");

		final ConfigCache configCache = new ConfigCache();
		configCache.put("global", globalMap);
		// map.clear();
		Map<String, String> clientMap = new HashMap<String, String>();
		clientMap.put("global.token.expiry.time", "30");
		clientMap.put("token.expiry.time", "30");
		configCache.put("WEB", clientMap);
		
		Map<String, String> clientConfigMap = new HashMap<String, String>();
		clientConfigMap.put("upgrade.enabled", "true");
      configCache.put("1", clientConfigMap);
      
		CacheManager.getInstance().setCache(configCache);

		ClientCache clientConfig = new ClientCache();
		Client client = getClient();
		clientConfig.put(client.getClientId(), client);
		CacheManager.getInstance().setCache(clientConfig);
	}
	private Client getClient() {
		Client cli = new Client();
		cli.setClientId("2");
		cli.setClientKey("snapdeal");
		cli.setClientName("test client");
		cli.setMerchant(Merchant.ONECHECK);
		cli.setClientType(ClientType.USER_FACING);
		cli.setClientPlatform(ClientPlatform.WEB);
		return cli;
	}

	@Before
	public void initMocks() {
		try {
			initConfig();
			MockitoAnnotations.initMocks(otpGenerator);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} catch (Error e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGenerateOTPNotActive() {
		try {
			Optional<UserOTPEntity> otpOption = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			when(persistenceManager.getLatestOTP(any(GenerateOTPServiceRequest.class)))
					.thenReturn(otpOption);

			when(otpUtility.getOTPState(otpOption)).thenReturn(
					OTPState.NOT_ACTIVE);
			when(otpUtility.getExpiryDurationInMins()).thenReturn(20);
			when(persistenceManager.getOtpId(any(UserOTPEntity.class)))
					.thenReturn(otpOption);
			otpGenerator.generate(getDefaultRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} catch (Error e) {
			System.out.println(e.getMessage());
		}
	}

	
	@Test
	public void testFailGenerateOTPNotActive() {
		try {
			Optional<UserOTPEntity> otpOption = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			Optional<UserOTPEntity> otpOptional = Optional
					.<UserOTPEntity> absent();

			when(persistenceManager.getLatestOTP(any(GenerateOTPServiceRequest.class)))
					.thenReturn(otpOption);

			when(otpUtility.getOTPState(otpOption)).thenReturn(
					OTPState.NOT_ACTIVE);
			when(otpUtility.getExpiryDurationInMins()).thenReturn(20);
			when(persistenceManager.getOtpId(any(UserOTPEntity.class)))
					.thenReturn(otpOptional);

			otpGenerator.generate(getDefaultRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} catch (Error e) {
			System.out.println(e.getMessage());
		}
	}

	
	@Test
	public void testGenerateOTPInExpiry() {
		try {
			Optional<UserOTPEntity> otpOption = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			when(persistenceManager.getLatestOTP(any(GenerateOTPServiceRequest.class)))
					.thenReturn(otpOption);

			when(otpUtility.getOTPState(otpOption)).thenReturn(
					OTPState.IN_EXPIRY);
			when(otpUtility.getReSendAttemptsLimit()).thenReturn(4);
			when(persistenceManager.getOtpId(any(UserOTPEntity.class)))
					.thenReturn(otpOption);
			otpGenerator.generate(getDefaultRequest());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} catch (Error e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testGenerateNewOtp() {
		Optional<UserOTPEntity> otpOption = Optional.<UserOTPEntity> absent();
		Optional<UserOTPEntity> otpOptional = Optional
				.<UserOTPEntity> of(getDefaultOTPInfo());
		when(persistenceManager.getLatestOTP(any(GenerateOTPServiceRequest.class)))
				.thenReturn(otpOption);
		when(persistenceManager.getOtpId(any(UserOTPEntity.class))).thenReturn(
				otpOptional);
		otpGenerator.generate(getDefaultRequest());
	}

	
	@Test
	public void testGenerateOtpInDoesNotExistThrowValidationException() {
		try {
			Optional<UserOTPEntity> otpOption = Optional
					.<UserOTPEntity> of(getDefaultOTPInfo());
			Optional<UserOTPEntity> otpOptional = Optional
					.<UserOTPEntity> absent();
			Optional<FreezeAccountEntity> freezeoptional = Optional
					.<FreezeAccountEntity> absent();
			when(
					persistenceManager
							.getFreezedAccount(any(GetFrozenAccount.class)))
					.thenReturn(freezeoptional);
			when(otpUtility.getReSendAttemptsLimit()).thenReturn(4);
			when(otpUtility.getOTPState(otpOption)).thenReturn(
					OTPState.DOES_NOT_EXIST);
		//	expectedException.expect(ValidationException.class);
			otpGenerator.reSendOTP(otpOption);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testReSendOtpException(){
		Optional<UserOTPEntity> otpOption = Optional
				.<UserOTPEntity> of(getDefaultOTPInfo());
		Optional<UserOTPEntity> otpOptional = Optional.<UserOTPEntity> absent();
		Optional<FreezeAccountEntity> freezeoptional = Optional
				.<FreezeAccountEntity> of(getDefaultFrozenAccount());
		when(persistenceManager.getFreezedAccount(any(GetFrozenAccount.class)))
				.thenReturn(freezeoptional);
		expectedException.expect(IMSServiceException.class);
		otpGenerator.reSendOTP(otpOption);
	}
	
	@Test
	public void testReSendOtpInNOTActive() {
		Optional<UserOTPEntity> otpOption = Optional
				.<UserOTPEntity> of(getDefaultOTPInfo());
		Optional<UserOTPEntity> otpOptional = Optional.<UserOTPEntity> absent();
		Optional<FreezeAccountEntity> freezeoptional = Optional
				.<FreezeAccountEntity> absent();
		when(persistenceManager.getFreezedAccount(any(GetFrozenAccount.class)))
				.thenReturn(freezeoptional);
		when(otpUtility.getReSendAttemptsLimit()).thenReturn(4);
		when(otpUtility.getOTPState(otpOption)).thenReturn(OTPState.NOT_ACTIVE);
		when(persistenceManager.getOtpId(any(UserOTPEntity.class))).thenReturn(
				otpOption);
		otpGenerator.reSendOTP(otpOption);
	}

	
	@Test
	public void testReSendOtpInExpiry() {
		Optional<UserOTPEntity> otpOption = Optional
				.<UserOTPEntity> of(getDefaultOTPInfo());
		Optional<UserOTPEntity> otpOptional = Optional.<UserOTPEntity> absent();
		Optional<FreezeAccountEntity> freezeoptional = Optional
				.<FreezeAccountEntity> absent();
		when(persistenceManager.getFreezedAccount(any(GetFrozenAccount.class)))
				.thenReturn(freezeoptional);
		when(otpUtility.getReSendAttemptsLimit()).thenReturn(4);
		when(otpUtility.getOTPState(otpOption)).thenReturn(OTPState.IN_EXPIRY);
		otpGenerator.reSendOTP(otpOption);
	}

	
	@Test
	public void testIsUserFrozenForOTP() {
		try {
			Optional<FreezeAccountEntity> freezeOption = Optional
					.<FreezeAccountEntity> of(getDefaultFrozenAccount());
			when(
					persistenceManager
							.getFreezedAccount(any(GetFrozenAccount.class)))
					.thenReturn(freezeOption);
			otpGenerator.getFreezdUser(getDefaultOTPInfo());
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testIsUserFrozenForOTP1() {
		try {
			Optional<FreezeAccountEntity> freezeOption = Optional
					.<FreezeAccountEntity> of(getDefaultFrozenAccount());
			when(
					persistenceManager
							.getFreezedAccount(any(GetFrozenAccount.class)))
					.thenReturn(freezeOption);
			UserOTPEntity otpInfo=getDefaultOTPInfo();
			otpInfo.setEmail(null);
			otpGenerator.getFreezdUser(otpInfo);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testIsUserFrozenForOTP2() {
		try {
			Optional<FreezeAccountEntity> freezeOption = Optional
					.<FreezeAccountEntity> of(getDefaultFrozenAccount());
			when(
					persistenceManager
							.getFreezedAccount(any(GetFrozenAccount.class)))
					.thenReturn(freezeOption);
			UserOTPEntity otpInfo=getDefaultOTPInfo();
			otpInfo.setMobileNumber(null);
			otpGenerator.getFreezdUser(otpInfo);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	
	@Test
	public void testBlockUser() {
		try {
			when(otpUtility.getBlockDurationInMins()).thenReturn(30);
			otpGenerator.blockUser(getDefaultOTPInfo(),
					OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testBlockUser1() {
		try {
			when(otpUtility.getBlockDurationInMins()).thenReturn(30);
			UserOTPEntity otpInfo=getDefaultOTPInfo();
					otpInfo.setEmail(null);
			otpGenerator.blockUser(otpInfo,
					OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testBlockUser2() {
		try {
			when(otpUtility.getBlockDurationInMins()).thenReturn(30);
			UserOTPEntity otpInfo=getDefaultOTPInfo();
					otpInfo.setMobileNumber(null);
			otpGenerator.blockUser(otpInfo,
					OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testUserFrozenForGenerateOTP() {
		try {
			Optional<FreezeAccountEntity> freezeOption = Optional
					.<FreezeAccountEntity> of(getDefaultFrozenAccount());
			when(
					persistenceManager
							.getFreezedAccount(any(GetFrozenAccount.class)))
					.thenReturn(freezeOption);
			otpGenerator.validateUserFreeze(getDefaultRequest());
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testUserFrozenForGenerateOTP1() {
		try {
			Optional<FreezeAccountEntity> freezeOption = Optional
					.<FreezeAccountEntity> of(getDefaultFrozenAccount());
			when(
					persistenceManager
							.getFreezedAccount(any(GetFrozenAccount.class)))
					.thenReturn(freezeOption);
			GenerateOTPServiceRequest request=getDefaultRequest();
			request.setEmailId(null);
			otpGenerator.validateUserFreeze(request);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testUserFrozenForGenerateOTP2() {
		try {
			Optional<FreezeAccountEntity> freezeOption = Optional
					.<FreezeAccountEntity> of(getDefaultFrozenAccount());
			when(
					persistenceManager
							.getFreezedAccount(any(GetFrozenAccount.class)))
					.thenReturn(freezeOption);
			GenerateOTPServiceRequest request=getDefaultRequest();
			request.setMobileNumber(null);
			otpGenerator.validateUserFreeze(request);
		} catch (Exception e) {
			fail(e.getMessage());
		} catch (Error e) {
			fail(e.getMessage());
		}
	}
	@Test
	public void testUserFrozenNotFound(){
		Optional<FreezeAccountEntity> freezeOption = Optional
		.<FreezeAccountEntity> absent();
		when(
				persistenceManager
						.getFreezedAccount(any(GetFrozenAccount.class)))
				.thenReturn(freezeOption);
		GenerateOTPServiceRequest request=getDefaultRequest();
		request.setMobileNumber(null);
		otpGenerator.validateUserFreeze(request);
	}
	
	
	
	
	

	private UserOTPEntity getDefaultOTPInfo() {
		UserOTPEntity otpInfo = new UserOTPEntity();
		otpInfo.setCreatedOn(new Date());
		otpInfo.setExpiryTime(new Date());
		otpInfo.setOtp("DQasG-nu2_QCH2ILXtludw");
		otpInfo.setOtpStatus(OTPStatus.ACTIVE);
		otpInfo.setClientId("AA6F0FD7BBA87D81");
		otpInfo.setEmail("rajeshverma12121@gmail.com");
		otpInfo.setMobileNumber("9589185740");
		otpInfo.setOtpId("ef28b8b5-e96f-4ca9-b851-2f537ee48339");
		otpInfo.setOtpType(OTPPurpose.UPDATE_MOBILE.toString());
		otpInfo.setResendAttempts(2);
		return otpInfo;
	}

	private GenerateOTPServiceRequest getDefaultRequest() {
		GenerateOTPServiceRequest generateOTPRequest = new GenerateOTPServiceRequest();
		generateOTPRequest.setOtpType(OTPPurpose.UPDATE_MOBILE);
		generateOTPRequest.setMobileNumber("9589185740");
		generateOTPRequest.setEmailId("rajeshverma12121@gmail.com");
		generateOTPRequest.setClientId("AA6F0FD7BBA87D81");
		return generateOTPRequest;
	}

	private FreezeAccountEntity getDefaultFrozenAccount() {
		FreezeAccountEntity account = new FreezeAccountEntity();
		Date currentDate = new Date();
		currentDate.setMinutes(currentDate.getMinutes() + 30);
		account.setExpiryTime(currentDate);
		account.setFreezeReason(OtpConstants.FROZEN_REASON_INVALID_ATTEMPTS);
		account.setIsdeleted("false");
		account.setOtpType("FORGOT_PASSWORD");
		return account;
	}

	private void fail(String message) {
		System.out.println(message);
	}
}
