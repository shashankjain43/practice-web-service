package com.snapdeal.ims.token.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.client.service.IClientService;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dto.AuthCodeInformationDTO;
import com.snapdeal.ims.dto.OAuthTokenInformationDTO;
import com.snapdeal.ims.dto.TokenInformationDTO;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.token.dto.GlobalTokenDetailsDTO;
import com.snapdeal.ims.token.dto.RefreshTokenDetailsDTO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.entity.RefreshTokenDetailsEntity;
import com.snapdeal.ims.token.request.AuthCodeRequest;
import com.snapdeal.ims.token.request.LoginTokenRequest;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.IGlobalTokenService;
import com.snapdeal.ims.token.service.IIDGenerator;
import com.snapdeal.ims.token.service.ITokenGenerationService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.token.service.ITransferTokenService;
import com.snapdeal.ims.token.service.impl.TokenGenerationServiceFactory.TokenType;
import com.snapdeal.ims.utils.CipherServiceUtil;
import com.snapdeal.ims.utils.DateUtil;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Setter
public class GlobalTokenServiceImpl extends AbstractTokenService implements IGlobalTokenService {

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private ITransferTokenService transferTokenService;

	@Autowired
	private IIDGenerator idGenerator;

	@Autowired
	private IClientService clientService;

	@Autowired
	IUserIdCacheService userIdCacheService;

	@Autowired
	AuthorizationContext context;

	private Merchant getMerchant() {
		return ClientConfiguration.getMerchantById(context.get(IMSRequestHeaders.CLIENT_ID.toString()));
	}

	/**
	 * @see com.snapdeal.ims.token.service.IGlobalTokenService#createTokenOnLogin(LoginTokenRequest)
	 */

	@Override
	@Timed
	@Marked
	@Logged
	public TokenInformationDTO createTokenOnLogin(LoginTokenRequest loginTokenRequest) {
		// Get TokenService of particular version.
		String serviceVersion = Configuration
				.getGlobalProperty(ConfigurationConstants.DEFAULT_GLOBAL_TOKEN_GENERATION_SERVICE_VERSION);
		if (loginTokenRequest.isUpgradeFlow()) {
			serviceVersion = Configuration
					.getGlobalProperty(ConfigurationConstants.LINK_UPGRADE_GLOBAL_TOKEN_GENERATION_SERVICE_VERSION);
		}

		@SuppressWarnings("unchecked")
		ITokenGenerationService<GlobalTokenDetailsDTO> tokenVersionService = TokenGenerationServiceFactory
				.getTokenVersionService(serviceVersion, TokenType.GLOBAL_TOKEN);

		Date tokenExpiry = getTokenExpiryTime(loginTokenRequest.getClientId());

		// Get the id generated from id-generator.
		String globalTokenId = idGenerator.generateGlobalTokenId();

		GlobalTokenDetailsDTO globalTokenDetails = mapRequestToDTO(loginTokenRequest, serviceVersion, tokenExpiry,
				globalTokenId);

		// Generate plain token from login token request and encrypt using
		// cipherService.
		String plainToken = tokenVersionService.generatePlainToken(globalTokenDetails);
		String generatedToken;
		try {
			generatedToken = CipherServiceUtil.encrypt(plainToken);
		} catch (CipherException e) {
			throw new InternalServerException(e);
		}
		String token = tokenService.generateTokenForGlobalToken(globalTokenDetails, loginTokenRequest.getClientId(),
				loginTokenRequest.isUpgradeFlow());
		// Save in persistence layer.
		save(globalTokenDetails, generatedToken);
		userIdCacheService.putUserIdbyEmailId(loginTokenRequest.getUserId(), loginTokenRequest.getEmailId());
		return responseCreator(generatedToken, tokenExpiry, token, tokenExpiry);
	}

	/**
	 * Save {@link GlobalTokenDetailsEntity} into persistent layer.
	 * 
	 * @param globalTokenDetails
	 * @param generatedToken
	 */
	private void save(GlobalTokenDetailsDTO globalTokenDetails, String generatedToken) {

		GlobalTokenDetailsEntity entity = new GlobalTokenDetailsEntity();
		entity.setUserId(globalTokenDetails.getUserId());
		entity.setGlobalTokenId(globalTokenDetails.getGlobalTokenId());
		entity.setExpiryTime(globalTokenDetails.getExpiryTime());
		entity.setGlobalToken(generatedToken);
		entity.setMachineIdentifier(globalTokenDetails.getMachineID());
		entity.setUserAgent(globalTokenDetails.getUserAgent());
		entity.setMerchant(getMerchant());
		globalTokenDao.save(entity);
	}

	/**
	 * Save {@link GlobalTokenDetailsEntity} into persistent layer.
	 * 
	 * @param refreshTokenDetails
	 * @param generatedToken
	 */
	private void save(RefreshTokenDetailsDTO refreshTokenDetails, String generatedToken) {

		RefreshTokenDetailsEntity entity = new RefreshTokenDetailsEntity();
		entity.setUserId(refreshTokenDetails.getUserId());
		entity.setGlobalTokenId(refreshTokenDetails.getGlobalTokenId());
		entity.setExpiryTime(refreshTokenDetails.getExpiryTime());
		entity.setGlobalToken(generatedToken);
		entity.setMachineIdentifier(refreshTokenDetails.getMachineID());
		entity.setUserAgent(refreshTokenDetails.getUserAgent());
		entity.setMerchant(getMerchant());
		entity.setMerchantId(refreshTokenDetails.getMerchantId());
		globalTokenDao.save(entity);
	}

	/**
	 * Utility method to create DTO from request data.
	 * 
	 * @param loginTokenRequest
	 * @param serviceVersion
	 * @param tokenExpiry
	 * @param globalTokenId
	 * @return
	 */
	private GlobalTokenDetailsDTO mapRequestToDTO(LoginTokenRequest loginTokenRequest, String serviceVersion,
			Date tokenExpiry, String globalTokenId) {

		GlobalTokenDetailsDTO globalTokenDetails = new GlobalTokenDetailsDTO();
		globalTokenDetails.setExpiryTime(tokenExpiry);
		globalTokenDetails.setMachineID(loginTokenRequest.getUserMachineIdentifier());
		globalTokenDetails.setTokenGenerationServiceVersion(serviceVersion);
		globalTokenDetails.setUserAgent(loginTokenRequest.getUserAgent());
		globalTokenDetails.setUserId(loginTokenRequest.getUserId());
		globalTokenDetails.setGlobalTokenId(globalTokenId);
		return globalTokenDetails;
	}

	/**
	 * Utility to create response.
	 * 
	 * @param globalToken
	 * @param globalTokenExpiry
	 * @param token
	 * @param tokenExpiry
	 * @return {@link TokenInformationDTO}
	 */
	private TokenInformationDTO responseCreator(String globalToken, Date globalTokenExpiry, String token,
			Date tokenExpiry) {
		TokenInformationDTO response = new TokenInformationDTO();
		response.setGlobalToken(globalToken);
		response.setGlobalTokenExpiry(DateUtil.formatDate(globalTokenExpiry,
				com.snapdeal.ims.common.constant.CommonConstants.TIMESTAMP_FORMAT));
		response.setToken(token);
		response.setTokenExpiry(
				DateUtil.formatDate(tokenExpiry, com.snapdeal.ims.common.constant.CommonConstants.TIMESTAMP_FORMAT));
		return response;
	}

	/**
	 * @see com.snapdeal.ims.token.service.IGlobalTokenService#getUserIdByGlobalToken(String,
	 *      String)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public String getUserIdByGlobalToken(String globalToken, String clientId) {

		String plainToken = decryptToken(globalToken, TokenType.GLOBAL_TOKEN);
		ITokenGenerationService<GlobalTokenDetailsDTO> tokenVersionService = getTokenGenerationService(plainToken,
				TokenType.GLOBAL_TOKEN);
		GlobalTokenDetailsDTO globalTokenDetailsDTO = tokenVersionService.getTokenDataFromDecryptedToken(plainToken);
		if (null == globalTokenDetailsDTO || null == globalTokenDetailsDTO.getGlobalTokenId()) {
			if (log.isDebugEnabled())
				log.debug("Could not parse to token with token generation service : " + globalToken);
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN.errCode(),
					IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN.errMsg());
		}
		GlobalTokenDetailsEntity globalTokenDetails = globalTokenDao
				.getGlobalTokenById(globalTokenDetailsDTO.getGlobalTokenId());
		validateToken(globalTokenDetailsDTO, globalTokenDetails);
		renewTokenExpiry(globalTokenDetails, clientId);
		String userId = userIdCacheService.getActualUserIdForTokenUserId(globalTokenDetails.getUserId(),
				globalTokenDetails.getMerchant());
		return userId;
	}

	/**
	 * @see com.snapdeal.ims.token.service.IGlobalTokenService#isTokenValid(TokenRequest)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public boolean isTokenValid(TokenRequest request) {
		String globalToken = request.getToken();
		String plainToken = decryptToken(globalToken, TokenType.GLOBAL_TOKEN);
		ITokenGenerationService<GlobalTokenDetailsDTO> tokenVersionService = getTokenGenerationService(plainToken,
				TokenType.GLOBAL_TOKEN);
		GlobalTokenDetailsDTO globalTokenDetailsDTO = tokenVersionService.getTokenDataFromDecryptedToken(plainToken);
		if (null == globalTokenDetailsDTO || null == globalTokenDetailsDTO.getGlobalTokenId()) {
			if (log.isDebugEnabled())
				log.debug("Could not parse to token with token generation service : " + globalToken);
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN.errCode(),
					IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN.errMsg());

		}
		GlobalTokenDetailsEntity globalTokenDetails = globalTokenDao
				.getGlobalTokenById(globalTokenDetailsDTO.getGlobalTokenId());
		return validateToken(globalTokenDetailsDTO, globalTokenDetails);
	}

	/**
	 * Token validation logic.
	 * 
	 * @param globalTokenDetailsDTO
	 * @return
	 */
	private boolean validateToken(GlobalTokenDetailsDTO globalTokenDetailsDTO,
			GlobalTokenDetailsEntity globalTokenDetails) {
		// Currently we dont validate user-identifier and user-agent
		if (null != globalTokenDetails) {
			if (!isValid(globalTokenDetails.getGlobalToken())) {
				log.warn("Token expired for : " + globalTokenDetailsDTO);
				log.warn("*** Probable FRAUD user which is not migrated. ****");
				throw new AuthenticationException(IMSAuthenticationExceptionCodes.GLOBAL_TOKEN_EXPIRED.errCode(),
						IMSAuthenticationExceptionCodes.GLOBAL_TOKEN_EXPIRED.errMsg());
			}
			return true;
		} else {
			if (log.isWarnEnabled())
				log.warn("Token expired for : " + globalTokenDetailsDTO);
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.GLOBAL_TOKEN_EXPIRED.errCode(),
					IMSAuthenticationExceptionCodes.GLOBAL_TOKEN_EXPIRED.errMsg());
		}
	}

	/**
	 * @see com.snapdeal.ims.token.service.IGlobalTokenService#getTokenFromGlobalToken(TokenRequest)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public TokenInformationDTO getTokenFromGlobalToken(TokenRequest tokenRequest) {

		String globalToken = tokenRequest.getToken();
		String plainToken = decryptToken(globalToken, TokenType.GLOBAL_TOKEN);
		String serviceVersion = getVersionFromToken(plainToken);
		@SuppressWarnings("unchecked")
		ITokenGenerationService<GlobalTokenDetailsDTO> tokenVersionService = TokenGenerationServiceFactory
				.getTokenVersionService(serviceVersion, TokenType.GLOBAL_TOKEN);
		GlobalTokenDetailsDTO globalTokenDetailsDTO = tokenVersionService.getTokenDataFromDecryptedToken(plainToken);
		GlobalTokenDetailsEntity globalTokenDetails = globalTokenDao
				.getGlobalTokenById(globalTokenDetailsDTO.getGlobalTokenId());
		// Validate the token.
		validateToken(globalTokenDetailsDTO, globalTokenDetails);
		renewTokenExpiry(globalTokenDetails, tokenRequest.getClientId());
		String token = tokenService.generateTokenForGlobalToken(globalTokenDetailsDTO, tokenRequest.getClientId(),
				tokenRequest.isLinkUpgradeFlow());
		globalTokenDetailsDTO.setExpiryTime(globalTokenDetails.getExpiryTime());
		Date tokenExpiry = globalTokenDetailsDTO.getExpiryTime();
		return responseCreator(globalToken, tokenExpiry, token, tokenExpiry);
	}

	@Override
	@Timed
	@Marked
	@Logged
	public AuthCodeInformationDTO createAuthCode(AuthCodeRequest authCodeRequest) {
		// Get TokenService of particular version.
		String serviceVersion = Configuration
				.getGlobalProperty(ConfigurationConstants.OAUTH_REFRESH_TOKEN_GENERATION_SERVICE_VERSION);

		@SuppressWarnings("unchecked")
		ITokenGenerationService<RefreshTokenDetailsDTO> tokenVersionService = TokenGenerationServiceFactory
				.getTokenVersionService(serviceVersion, TokenType.GLOBAL_TOKEN);

		Date tokenExpiry = getExpiryTimeForOAuthToken(authCodeRequest.getMerchantId(),TokenType.AUTH_CODE);
		
		// Get the id generated from id-generator.
		String globalTokenId = idGenerator.generateGlobalTokenId();

		RefreshTokenDetailsDTO globalTokenDetails = mapRequestToDTO(authCodeRequest, serviceVersion, tokenExpiry,
				globalTokenId);

		// Generate plain token from login token request and encrypt using
		// cipherService.
		String plainToken = tokenVersionService.generatePlainToken(globalTokenDetails);
		String generatedToken;
		try {
			generatedToken = CipherServiceUtil.encrypt(plainToken);
		} catch (CipherException e) {
			throw new InternalServerException(e);
		}
		String token = transferTokenService.generateAuthCodeDetails(globalTokenId, authCodeRequest.getMerchantId());
		// Save in persistence layer.
		save(globalTokenDetails, generatedToken);
		// userIdCacheService.putUserIdbyEmailId(authCodeRequest.getUserId(),
		// loginTokenRequest.getEmailId());
		return responseCreator(generatedToken, tokenExpiry, token);

	}

	/**
	 * Utility to create response.
	 * 
	 * @param globalToken
	 * @param globalTokenExpiry
	 * @param token
	 * @param tokenExpiry
	 * @return {@link TokenInformationDTO}
	 */
	private AuthCodeInformationDTO responseCreator(String globalToken, Date globalTokenExpiry, String token) {
		AuthCodeInformationDTO response = new AuthCodeInformationDTO();
		response.setGlobalToken(globalToken);
		response.setGlobalTokenExpiry(DateUtil.formatDate(globalTokenExpiry,
				com.snapdeal.ims.common.constant.CommonConstants.TIMESTAMP_FORMAT));
		response.setAuthCode(token);
		return response;
	}

	/**
	 * Utility method to create DTO from request data.
	 * 
	 * @param authCodeRequest
	 * @param serviceVersion
	 * @param tokenExpiry
	 * @param globalTokenId
	 * @return
	 */
	private RefreshTokenDetailsDTO mapRequestToDTO(AuthCodeRequest authCodeRequest, String serviceVersion,
			Date tokenExpiry, String globalTokenId) {

		RefreshTokenDetailsDTO globalTokenDetails = new RefreshTokenDetailsDTO();
		globalTokenDetails.setExpiryTime(tokenExpiry);
		globalTokenDetails.setTokenGenerationServiceVersion(serviceVersion);
		globalTokenDetails.setUserId(authCodeRequest.getUserId());
		globalTokenDetails.setGlobalTokenId(globalTokenId);
		globalTokenDetails.setMerchantId(authCodeRequest.getMerchantId());
		return globalTokenDetails;
	}

	@Override
	public OAuthTokenInformationDTO getOAuthTokenInfo(String token) {
		return getOAuthTokenDetails(token, TokenType.REFRESH_TOKEN);
	}
}