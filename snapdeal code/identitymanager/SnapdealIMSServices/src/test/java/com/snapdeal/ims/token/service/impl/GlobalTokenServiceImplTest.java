package com.snapdeal.ims.token.service.impl;

import static org.junit.Assert.fail;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.client.service.IClientService;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.exception.ValidationException;
import com.snapdeal.ims.request.AbstractRequest;
import com.snapdeal.ims.response.ClientDetails;
import com.snapdeal.ims.response.GetClientResponse;
import com.snapdeal.ims.token.dao.IGlobalTokenDetailsDAO;
import com.snapdeal.ims.token.dto.GlobalTokenDetailsDTO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.request.LoginTokenRequest;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.utils.CipherServiceUtil;

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

import java.util.Date;
import java.util.UUID;

@RunWith(MockitoJUnitRunner.class)
public class GlobalTokenServiceImplTest extends BaseTest {

   private static final String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0";
   private static final String clientId = "1";
   @InjectMocks
   private GlobalTokenServiceImpl globalTokenService;

   @Mock
   private ITokenService tokenService;

   @Mock
   private IGlobalTokenDetailsDAO globalTokenDetailsDAO;

   @Mock
   private IClientService clientService;
   
   @Mock
   IUserIdCacheService userIdCacheService;
   
   @Mock
   AuthorizationContext context;

   IDGeneratorImpl idGenerator = new IDGeneratorImpl();

   @Before
   public void init() {
      MockitoAnnotations.initMocks(this);
      super.initConfig();
      globalTokenService.setIdGenerator(idGenerator);
      // globalTokenService.setCipherService(cipherService);
      Mockito.when(clientService.getClientById(Mockito.anyString())).thenReturn(getValidClient());
      Mockito.when(context.get(Mockito.anyString())).thenReturn(getValidClient().getClientDetails().getClientId());
      
   }

   private GetClientResponse getValidClient() {
      GetClientResponse res = new GetClientResponse();
      ClientDetails clientDetails = new ClientDetails();
      clientDetails.setClientId(clientId);
      clientDetails.setClientPlatform(ClientPlatform.WEB);
      res.setClientDetails(clientDetails);
      return res;
   }

   /**
    * Test method for
    * {@link com.snapdeal.ims.token.service.impl.GlobalTokenServiceImpl#createTokenOnLogin(com.snapdeal.ims.token.request.LoginTokenRequest)}
    * .
    * 
    * @throws CipherException
    */
   @Test
   public void testCreateTokenOnLogin() throws CipherException {

      String token = CipherServiceUtil.encrypt(Configuration
               .getGlobalProperty(ConfigurationConstants.DEFAULT_TOKEN_GENERATION_SERVICE_VERSION)
      // + CommonConstants.TOKEN_DELIM
      // + clientId
               + ServiceCommonConstants.TOKEN_DELIM + UUID.randomUUID());
      Mockito.when(
               tokenService.generateTokenForGlobalToken(Mockito.any(GlobalTokenDetailsDTO.class),
                        Mockito.anyString(), Mockito.anyBoolean())).thenReturn(token);

      String userId = idGenerator.generateUserId();
      LoginTokenRequest loginTokenRequest = createLoginTokenRequest(userId);
      TokenInformationDTO loginTokenResponse = globalTokenService
               .createTokenOnLogin(loginTokenRequest);
      Assert.assertNotNull("Expected a proper response for loginToken.", loginTokenResponse);
   }

   private LoginTokenRequest createLoginTokenRequest(String userId) {
      LoginTokenRequest loginTokenRequest = new LoginTokenRequest();
      loginTokenRequest.setUserId(userId);
      loginTokenRequest.setClientId(clientId);
      updateBasicRequestDetails(loginTokenRequest);
      return loginTokenRequest;
   }

   private void updateBasicRequestDetails(AbstractRequest loginTokenRequest) {

      loginTokenRequest.setUserAgent(USER_AGENT);
      loginTokenRequest.setUserMachineIdentifier("34:e6:d7:13:89:d1");
   }

   /**
    * Test method for
    * {@link com.snapdeal.ims.token.service.impl.GlobalTokenServiceImpl#getUserIdByGlobalToken(java.lang.String, String)}
    * .
    * 
    * @throws CipherException
    */
   @Ignore
   @Test
   public void testGetUserIdByGlobalToken() throws CipherException {
      String userId = idGenerator.generateUserId();
      GlobalTokenDetailsEntity globakTokenDetailsEntity = getValidGlobalTokenDetailsEntity(userId);
      String token = CipherServiceUtil.encrypt(Configuration
               .getGlobalProperty(ConfigurationConstants.DEFAULT_TOKEN_GENERATION_SERVICE_VERSION)
      // + CommonConstants.TOKEN_DELIM
      // + clientId
               + ServiceCommonConstants.TOKEN_DELIM + globakTokenDetailsEntity.getGlobalTokenId());
      Mockito.when(
               userIdCacheService.getActualUserIdForTokenUserId(userId, getValidClient()
                        .getClientDetails().getMerchant())).thenReturn(userId);
      Mockito.when(
               tokenService.generateTokenForGlobalToken(Mockito.any(GlobalTokenDetailsDTO.class),
                        Mockito.anyString(), Mockito.anyBoolean())).thenReturn(token);
      Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString())).thenReturn(
               globakTokenDetailsEntity);
      LoginTokenRequest loginTokenRequest = createLoginTokenRequest(userId);
      TokenInformationDTO loginTokenResponse = globalTokenService
               .createTokenOnLogin(loginTokenRequest);
      Assert.assertNotNull("Expected valid response for create login token request.",
               loginTokenResponse);

      String globalToken = loginTokenResponse.getGlobalToken();
      String userId2 = globalTokenService.getUserIdByGlobalToken(globalToken, clientId);
      Assert.assertEquals("User Id should be same: testGetUserIdByGlobalToken() failed.", userId,
               userId2);
   }

   /**
    * Test method for
    * {@link com.snapdeal.ims.token.service.impl.GlobalTokenServiceImpl#isTokenValid(com.snapdeal.payments.ims.request.TokenRequest)}
    * .
    * 
    * @throws CipherException
    */
   @Test
   public void testIsTokenValid() throws CipherException {
      String userId = idGenerator.generateUserId();
      GlobalTokenDetailsEntity validGlobalTokenDetailsEntity = getValidGlobalTokenDetailsEntity(userId);
      Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString())).thenReturn(
               validGlobalTokenDetailsEntity);
      String token = CipherServiceUtil
               .encrypt(Configuration
                        .getGlobalProperty(ConfigurationConstants.DEFAULT_GLOBAL_TOKEN_GENERATION_SERVICE_VERSION)
                        + ServiceCommonConstants.TOKEN_DELIM
                        + validGlobalTokenDetailsEntity.getGlobalTokenId());

      try {
         TokenRequest request = createTokeRequest(token);
         Assert.assertTrue("Token invalid.", globalTokenService.isTokenValid(request));
      } catch (ValidationException e) {
         fail("Token Invalid" + e.getMessage());
      }
   }

   private GlobalTokenDetailsEntity getValidGlobalTokenDetailsEntity(String userId)
            throws CipherException {
      GlobalTokenDetailsEntity globakTokenDetailsEntity = new GlobalTokenDetailsEntity();
      globakTokenDetailsEntity.setExpiryTime(DateUtils.addDays(new Date(), 1));
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

   /**
    * 
    * Test method for
    * {@link com.snapdeal.ims.token.service.impl.GlobalTokenServiceImpl#isTokenValid(com.snapdeal.payments.ims.request.TokenRequest)}
    * .
    * 
    * @throws CipherException
    */
   @Test(expected = InternalServerException.class)
   // @Ignore
   public void testIsTokenValid_InvalidVersion() throws CipherException {
      String userId = idGenerator.generateUserId();
      GlobalTokenDetailsEntity globakTokenDetailsEntity = new GlobalTokenDetailsEntity();
      globakTokenDetailsEntity.setExpiryTime(DateUtils.addDays(new Date(), 1));
      globakTokenDetailsEntity.setUserId(userId);
      String globalTokenId = idGenerator.generateGlobalTokenId();
      globakTokenDetailsEntity.setGlobalTokenId(globalTokenId);
      Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString())).thenReturn(
               globakTokenDetailsEntity);
      String token = CipherServiceUtil.encrypt("V0" + ServiceCommonConstants.TOKEN_DELIM
               + globalTokenId);

      TokenRequest request = createTokeRequest(token);
      globalTokenService.isTokenValid(request);
   }

   private TokenRequest createTokeRequest(String token) {
      TokenRequest req = new TokenRequest();
      req.setClientId(clientId);
      req.setToken(token);
      updateBasicRequestDetails(req);
      return req;
   }

   /**
    * Test method for
    * {@link com.snapdeal.ims.token.service.impl.GlobalTokenServiceImpl#getTokenFromGlobalToken(com.snapdeal.payments.ims.request.TokenRequest)}
    * .
    * 
    * @throws CipherException
    */
   @Test
   public void testGetTokenFromGlobalToken() throws CipherException {
      String userId = idGenerator.generateUserId();
      GlobalTokenDetailsEntity globakTokenDetailsEntity = new GlobalTokenDetailsEntity();
      globakTokenDetailsEntity.setExpiryTime(DateUtils.addDays(new Date(), 1));
      globakTokenDetailsEntity.setUserId(userId);
      String globalTokenId = idGenerator.generateGlobalTokenId();
      globakTokenDetailsEntity.setGlobalTokenId(globalTokenId);
      Mockito.when(globalTokenDetailsDAO.getGlobalTokenById(Mockito.anyString())).thenReturn(
               globakTokenDetailsEntity);
      String globalToken = CipherServiceUtil
               .encrypt(Configuration
                        .getGlobalProperty(ConfigurationConstants.DEFAULT_GLOBAL_TOKEN_GENERATION_SERVICE_VERSION)
                        + ServiceCommonConstants.TOKEN_DELIM + globalTokenId);
      String token = CipherServiceUtil
               .encrypt(Configuration
                        .getGlobalProperty(ConfigurationConstants.DEFAULT_GLOBAL_TOKEN_GENERATION_SERVICE_VERSION)
                        + ServiceCommonConstants.TOKEN_DELIM + globalTokenId);
      globakTokenDetailsEntity.setGlobalToken(globalToken);
      Mockito.when(
               tokenService.generateTokenForGlobalToken(Mockito.any(GlobalTokenDetailsDTO.class),
                        Mockito.anyString(), Mockito.anyBoolean())).thenReturn(token);
      TokenRequest request = createTokeRequest(globalToken);
      TokenInformationDTO tokenFromGlobalToken = globalTokenService
               .getTokenFromGlobalToken(request);
      Assert.assertNotNull("Token response should be returned.", tokenFromGlobalToken);
      Assert.assertEquals("Global token miss-match.", tokenFromGlobalToken.getGlobalToken(),
               globalToken);
   }
}