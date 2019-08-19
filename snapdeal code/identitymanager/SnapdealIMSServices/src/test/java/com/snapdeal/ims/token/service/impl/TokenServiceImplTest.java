package com.snapdeal.ims.token.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.AbstractRequest;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.response.GetTransferTokenResponse;
import com.snapdeal.ims.token.dao.IGlobalTokenDetailsDAO;
import com.snapdeal.ims.token.dto.GlobalTokenDetailsDTO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.utils.CipherServiceUtil;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceImplTest extends BaseTest {

	private static final String MACHINE_IDENTIFIER = "34:e6:d7:13:89:d1";
	private static final String CLIENT_ID = "1";
	private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0";

	@InjectMocks
	private TokenServiceImpl tokenService = new TokenServiceImpl();

	@Mock
	private IGlobalTokenDetailsDAO globalTokenDetailsDAO;

	@Mock
	IUserIdCacheService userIdCacheService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		super.initConfig();
	}

	/**
	 * Positive test for:
	 * {@link com.snapdeal.ims.token.service.impl.TokenServiceImpl#isTokenValid(TokenRequest)}
	 * @throws CipherException 
	 */
	@Test
	public void testIsTokenValid() throws CipherException {
		try {
			Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString()))
					.thenReturn(
							getValidGlobalTokenDetailsEntity(UUID.randomUUID()
									.toString()));
			String token = getValidToken(UUID.randomUUID().toString());
			TokenRequest request = createTokenRequest(token);
			boolean isValidToken = tokenService.isTokenValid(request);
			Assert.assertTrue("Token shoud be valid.", isValidToken);
		} catch (ValidationException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Negative test for:
	 * {@link com.snapdeal.ims.token.service.impl.TokenServiceImpl#isTokenValid(TokenRequest)}
	 * @throws CipherException 
	 */
	@Test
	public void testIsTokenValid_invalid() throws CipherException {
		try {
			GlobalTokenDetailsEntity inValidGlobalTokenDetailsEntity = getValidGlobalTokenDetailsEntity(UUID
					.randomUUID().toString());
			inValidGlobalTokenDetailsEntity.setExpiryTime(DateUtils.addDays(
					new Date(), -1));
			Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString()))
					.thenReturn(inValidGlobalTokenDetailsEntity);
			String token = getValidToken(UUID.randomUUID().toString());
			TokenRequest request = createTokenRequest(token);
			boolean isValidToken = tokenService.isTokenValid(request);
			fail("Token shoud be in-valid.");
		} catch (AuthenticationException e) {
			assertTrue(e.getMessage(), true);
		}
	}

	private GlobalTokenDetailsEntity getValidGlobalTokenDetailsEntity(
String userId)
            throws CipherException {
		GlobalTokenDetailsEntity globakTokenDetailsEntity = new GlobalTokenDetailsEntity();
		globakTokenDetailsEntity
				.setExpiryTime(DateUtils.addDays(new Date(), 1));
		globakTokenDetailsEntity.setUserId(userId);
      String globalTokenId = UUID.randomUUID().toString();
      globakTokenDetailsEntity.setGlobalTokenId(globalTokenId);
		globakTokenDetailsEntity.setMerchant(Merchant.SNAPDEAL);
      globakTokenDetailsEntity.setGlobalToken(getValidGToken(globalTokenId));
		return globakTokenDetailsEntity;
	}

   private String getValidGToken(String globalTokenId) throws CipherException {
      String token = CipherServiceUtil.encrypt(Configuration
               .getGlobalProperty(ConfigurationConstants.DEFAULT_TOKEN_GENERATION_SERVICE_VERSION)
               + ServiceCommonConstants.TOKEN_DELIM + globalTokenId);
      return token;
   }

	private String getValidToken(String globalTokenId) throws CipherException {
		String token = CipherServiceUtil
				.encrypt(Configuration
						.getGlobalProperty(ConfigurationConstants.DEFAULT_TOKEN_GENERATION_SERVICE_VERSION)
						+ ServiceCommonConstants.TOKEN_DELIM
						+ CLIENT_ID
						+ ServiceCommonConstants.TOKEN_DELIM
						+ globalTokenId
						+ ServiceCommonConstants.TOKEN_DELIM + System.nanoTime());
		return token;
	}

	private TokenRequest createTokenRequest(String token) {
		TokenRequest req = new TokenRequest();
		req.setToken(token);
		req.setClientId(CLIENT_ID);
		setClientDetails(req);
		return req;
	}

	private void setClientDetails(AbstractRequest req) {
		req.setUserMachineIdentifier(MACHINE_IDENTIFIER);
		req.setUserAgent(USER_AGENT);
	}

	@Test
	public void testSignOut() throws CipherException {
		String userId = UUID.randomUUID().toString();

		GlobalTokenDetailsEntity validGlobalTokenDetailsEntity = getValidGlobalTokenDetailsEntity(userId);
		Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString()))
				.thenReturn(validGlobalTokenDetailsEntity);

		String token = getValidToken(validGlobalTokenDetailsEntity
				.getGlobalTokenId());
		SignoutRequest request = createSignOutRequest(token, Boolean.FALSE);
		tokenService.signOut(request);
	}

	@Test
	public void testSignOutHardSignout() throws CipherException {
		String userId = UUID.randomUUID().toString();

		GlobalTokenDetailsEntity validGlobalTokenDetailsEntity = getValidGlobalTokenDetailsEntity(userId);
		Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString()))
				.thenReturn(validGlobalTokenDetailsEntity);

		String token = getValidToken(validGlobalTokenDetailsEntity
				.getGlobalTokenId());
		SignoutRequest request = createSignOutRequest(token, Boolean.TRUE);
		tokenService.signOut(request);
	}
	
	
	private SignoutRequest createSignOutRequest(String token, boolean hardSignout) {
		SignoutRequest req = new SignoutRequest();
		req.setToken(token);
		req.setHardSignout(hardSignout);
		setClientDetails(req);
		return req;
	}

	@Test
	public void testGenerateTokenForGlobalToken() {
		String userId = RandomStringUtils.randomAlphabetic(20);
		String globalTokenId = null;
		String clientId = null;
		GlobalTokenDetailsDTO globalTokenDetails = createGlobalTokenDTO(userId,
				globalTokenId);
      String token = tokenService.generateTokenForGlobalToken(globalTokenDetails, clientId, true);
		Assert.assertNotNull(token);
	}

	private GlobalTokenDetailsDTO createGlobalTokenDTO(String userId,
			String globalTokenId) {
		GlobalTokenDetailsDTO globalTokenDetails = new GlobalTokenDetailsDTO();
		globalTokenDetails.setUserId(userId);
		globalTokenDetails.setUserAgent(USER_AGENT);
		globalTokenDetails.setMachineID(MACHINE_IDENTIFIER);
		globalTokenDetails.setGlobalTokenId(globalTokenId);
		return globalTokenDetails;
	}

	@Ignore
	@Test
	public void testGetUserIdByToken() throws CipherException {
		String userId = UUID.randomUUID().toString();

		GlobalTokenDetailsEntity validGlobalTokenDetailsEntity = getValidGlobalTokenDetailsEntity(userId);
		Mockito.when(
               userIdCacheService.getActualUserIdForTokenUserId(userId,Merchant.SNAPDEAL)).thenReturn(userId);
		Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString()))
				.thenReturn(validGlobalTokenDetailsEntity);

		String token = getValidToken(validGlobalTokenDetailsEntity
				.getGlobalTokenId());

		String userIdByToken = tokenService.getUserIdByToken(token);
		assertEquals(userId, userIdByToken);

	}
	
	
	@Test(expected = IMSServiceException.class)
   public void testGetTransferToken_IvnvalidTargetConsumer() throws CipherException {
	   String userId = UUID.randomUUID().toString();
      GlobalTokenDetailsEntity validGlobalTokenDetailsEntity = getValidGlobalTokenDetailsEntity(userId);

      String testToken = "gvP7CYJZtPQKMwl7Zbmf6tMH0EtB-vcz3BOZathpKOIEUJIZRiX6vvEU4Y46ALVIgg_5sxF3TVitTZgboLxpoA";
      GetTransferTokenRequest request = new GetTransferTokenRequest();
      request.setToken(testToken);
      request.setTargetImsConsumer("INVALID CONSUMER");
      Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString())).thenReturn(
               validGlobalTokenDetailsEntity);

      GetTransferTokenResponse response = tokenService.getTransferToken(request);
      Assert.assertNotNull("Unable to fetch transfer token correctly",
               response.getTransferTokenDto());
	   
	}
	
	@Test
   public void testGetTransferTokenSuccess() throws CipherException {

	   String userId = UUID.randomUUID().toString();
      GlobalTokenDetailsEntity validGlobalTokenDetailsEntity = getValidGlobalTokenDetailsEntity(userId);

      String testToken = "gvP7CYJZtPQKMwl7Zbmf6tMH0EtB-vcz3BOZathpKOIEUJIZRiX6vvEU4Y46ALVIgg_5sxF3TVitTZgboLxpoA";
      GetTransferTokenRequest request = new GetTransferTokenRequest();
      request.setToken(testToken);
      request.setTargetImsConsumer("TESTALIAS");
      Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString())).thenReturn(
               validGlobalTokenDetailsEntity);

      GetTransferTokenResponse response = tokenService.getTransferToken(request);
      Assert.assertNotNull("Unable to fetch transfer token correctly",
               response.getTransferTokenDto());
	   
	}
	
   @Test(expected = AuthenticationException.class)
   public void testGetTransferTokenWithWrongToken() throws CipherException {

      String userId = UUID.randomUUID().toString();
      GlobalTokenDetailsEntity validGlobalTokenDetailsEntity = getValidGlobalTokenDetailsEntity(userId);
   
      String testToken = "ZathpKOIEUJIZRiX6vvEU4Y4";
      GetTransferTokenRequest request = new GetTransferTokenRequest();
      request.setToken(testToken);
      Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString()))
      .thenReturn(validGlobalTokenDetailsEntity);

      tokenService.getTransferToken(request);
      
   }
   
   @Test(expected = AuthenticationException.class)
   public void testGetTransferTokenWithInvalidGlobalToken() throws CipherException {

      String testToken = "gvP7CYJZtPQKMwl7Zbmf6tMH0EtB-vcz3BOZathpKOIEUJIZRiX6vvEU4Y46ALVIgg_5sxF3TVitTZgboLxpoA";

      GetTransferTokenRequest request = new GetTransferTokenRequest();
      request.setToken(testToken);
      Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString())).thenReturn(null);

      tokenService.getTransferToken(request);
   }
}
