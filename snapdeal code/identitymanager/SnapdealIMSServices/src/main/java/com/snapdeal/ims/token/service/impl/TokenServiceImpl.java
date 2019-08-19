package com.snapdeal.ims.token.service.impl;

import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.cache.service.IUserIdCacheService;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dto.AccessTokenInformationDTO;
import com.snapdeal.ims.dto.OAuthTokenInformationDTO;
import com.snapdeal.ims.dto.TransferTokenDTO;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.request.SignoutRequest;
import com.snapdeal.ims.response.GetTransferTokenResponse;
import com.snapdeal.ims.response.SignoutResponse;
import com.snapdeal.ims.token.dto.AccessTokenDetailsDTO;
import com.snapdeal.ims.token.dto.AuthCodeDetailsDTO;
import com.snapdeal.ims.token.dto.GlobalTokenDetailsDTO;
import com.snapdeal.ims.token.dto.TokenDetailsDTO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.entity.RefreshTokenDetailsEntity;
import com.snapdeal.ims.token.request.AccessTokenRequest;
import com.snapdeal.ims.token.request.TokenRequest;
import com.snapdeal.ims.token.service.IGlobalTokenService;
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
public class TokenServiceImpl extends AbstractTokenService implements ITokenService {

	@Autowired
	private IUserIdCacheService userIdCacheService;

	@Autowired
	private ITransferTokenService transferTokenService;

	@Autowired
	private IGlobalTokenService globalTokenService;

	/**
	 * @see com.snapdeal.ims.token.service.ITokenService#isTokenValid(TokenRequest)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public boolean isTokenValid(TokenRequest request) {

		String plainTokenString = decryptToken(request.getToken(), TokenType.LOCAL_TOKEN);
		ITokenGenerationService<TokenDetailsDTO> tokenVersionService = getTokenGenerationService(plainTokenString,
				TokenType.LOCAL_TOKEN);
		TokenDetailsDTO tokenData = tokenVersionService.getTokenDataFromDecryptedToken(plainTokenString);

		GlobalTokenDetailsEntity globalTokenByTokenDetails = getGlobalTokenByTokenDetails(tokenData);

		boolean isValid = request.isLinkUpgradeFlow() ? true : isValid(globalTokenByTokenDetails.getGlobalToken());
		return globalTokenByTokenDetails != null && isValid;
	}

	/**
	 * Utility method to validate the token detail and return associated
	 * GlobalToken from token. TODO: Re-visit validation logic.
	 * 
	 * @param tokenDetailsDTO
	 * @return
	 */
	private GlobalTokenDetailsEntity getGlobalTokenByTokenDetails(TokenDetailsDTO tokenDetailsDTO) {
		if (null == tokenDetailsDTO || null == tokenDetailsDTO.getGlobalTokenId()) {
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN.errCode(),
					IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN.errMsg());
		}
		GlobalTokenDetailsEntity entity = globalTokenDao.getGlobalTokenById(tokenDetailsDTO.getGlobalTokenId());
		if (null == entity || entity.getExpiryTime().getTime() <= System.currentTimeMillis()) {
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.GLOBAL_TOKEN_EXPIRED.errCode(),
					IMSAuthenticationExceptionCodes.GLOBAL_TOKEN_EXPIRED.errMsg());
		}
		return entity;
	}

	/**
	 * @see com.snapdeal.ims.token.service.ITokenService#signOut(java.lang.String,
	 *      boolean)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public SignoutResponse signOut(SignoutRequest request) {

		String plainToken = decryptToken(request.getToken(), TokenType.LOCAL_TOKEN);

		ITokenGenerationService<TokenDetailsDTO> tokenGenerationService = getTokenGenerationService(plainToken,
				TokenType.LOCAL_TOKEN);

		TokenDetailsDTO tokenData = tokenGenerationService.getTokenDataFromDecryptedToken(plainToken);
		String globalTokenId = tokenData.getGlobalTokenId();
		if (request.isHardSignout()) {
			// For hard sign-out, remove all the global token for associated
			// user.
			GlobalTokenDetailsEntity globalTokenDetailsEntity = globalTokenDao.getGlobalTokenById(globalTokenId);
			if (null != globalTokenDetailsEntity) {
				globalTokenDao.deleteAllTokenForUser(globalTokenDetailsEntity.getUserId());
				if (log.isDebugEnabled()) {
					log.debug("Hard Signout for userId: " + globalTokenDetailsEntity.getUserId());
				}
			} else {
				IMSAuthenticationExceptionCodes code = IMSAuthenticationExceptionCodes.INVALID_TOKEN;
				throw new AuthenticationException(code.errCode(), code.errMsg());
			}
		} else {
			globalTokenDao.delete(globalTokenId);
		}
		SignoutResponse response = new SignoutResponse();
		response.setSuccess(true);
		return response;
	}

	/**
	 * @see com.snapdeal.ims.token.service.ITokenService#signOut(java.lang.String,
	 *      boolean)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public boolean signoutAllOtherTokens(String token) {
		boolean status;
		String plainToken = decryptToken(token, TokenType.LOCAL_TOKEN);

		ITokenGenerationService<TokenDetailsDTO> tokenGenerationService = getTokenGenerationService(plainToken,
				TokenType.LOCAL_TOKEN);

		TokenDetailsDTO tokenData = tokenGenerationService.getTokenDataFromDecryptedToken(plainToken);
		String globalTokenId = tokenData.getGlobalTokenId();

		GlobalTokenDetailsEntity globalTokenDetailsEntity = globalTokenDao.getGlobalTokenById(globalTokenId);
		if (null != globalTokenDetailsEntity) {
			status = globalTokenDao.deleteAllOtherToken(globalTokenDetailsEntity.getUserId(),
					globalTokenDetailsEntity.getGlobalTokenId());
			if (log.isDebugEnabled()) {
				log.debug("Hard Signout for userId: " + globalTokenDetailsEntity.getUserId());
			}
		} else {
			IMSAuthenticationExceptionCodes code = IMSAuthenticationExceptionCodes.INVALID_TOKEN;
			throw new AuthenticationException(code.errCode(), code.errMsg());
		}

		return status;
	}

	/**
	 * @see com.snapdeal.ims.token.service.ITokenService#generateTokenForGlobalToken(GlobalTokenDetailsDTO,
	 *      String)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public String generateTokenForGlobalToken(GlobalTokenDetailsDTO globalTokenDetails, String clientId,
			boolean isLinkUpgradeFlow) {
		// Get TokenService of particular version.
		String serviceVersion = Configuration
				.getGlobalProperty(ConfigurationConstants.DEFAULT_TOKEN_GENERATION_SERVICE_VERSION);
		if (isLinkUpgradeFlow) {
			serviceVersion = Configuration
					.getGlobalProperty(ConfigurationConstants.LINK_UPGRADE_TOKEN_GENERATION_SERVICE_VERSION);
		}
		@SuppressWarnings("unchecked")
		ITokenGenerationService<TokenDetailsDTO> tokenVersionService = TokenGenerationServiceFactory
				.getTokenVersionService(serviceVersion, TokenType.LOCAL_TOKEN);
		// Generate plain token from login token request and encrypt using
		// cipherService.
		TokenDetailsDTO tokenDetails = mapRequestToDTO(globalTokenDetails, clientId, serviceVersion);
		String plainToken = tokenVersionService.generatePlainToken(tokenDetails);
		String token;
		try {
			token = CipherServiceUtil.encrypt(plainToken);
		} catch (CipherException e) {
			throw new InternalServerException(e);
		}
		return token;
	}

	/**
	 * Utility method to create {@link TokenDetailsDTO} from request.
	 * 
	 * @param globalTokenDetails
	 * @param loginTokenRequest
	 * @param tokenGenerationServiceVersion
	 * @return
	 */
	private TokenDetailsDTO mapRequestToDTO(GlobalTokenDetailsDTO globalTokenDetails, String clientId,
			String tokenGenerationServiceVersion) {
		TokenDetailsDTO tokenDetails = new TokenDetailsDTO();
		tokenDetails.setTokenGenerationServiceVersion(tokenGenerationServiceVersion);
		tokenDetails.setGlobalTokenId(globalTokenDetails.getGlobalTokenId());
		tokenDetails.setClientId(clientId);
		tokenDetails.setExpiryTime(globalTokenDetails.getExpiryTime());
		return tokenDetails;
	}

	/**
	 * @see com.snapdeal.ims.token.service.ITokenService#getUserIdByToken(String)
	 */
	@Override
	@Timed
	@Marked
	@Logged
	public String getUserIdByToken(String token) {
		return getUserIdByToken(token, false);
	}

	@Override
	public String getUserIdByToken(String token, boolean isOCLinkFlow) {
		String plainToken = decryptToken(token, TokenType.LOCAL_TOKEN);

		ITokenGenerationService<TokenDetailsDTO> tokenVersionService = getTokenGenerationService(plainToken,
				TokenType.LOCAL_TOKEN);
		TokenDetailsDTO tokenData = tokenVersionService.getTokenDataFromDecryptedToken(plainToken);
		GlobalTokenDetailsEntity entity = getGlobalTokenByTokenDetails(tokenData);
		// if flow is oc link, then skip validation.
		if (!isOCLinkFlow && !isValid(entity.getGlobalToken())) {
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.GLOBAL_TOKEN_EXPIRED.errCode(),
					IMSAuthenticationExceptionCodes.GLOBAL_TOKEN_EXPIRED.errMsg());
		}
		renewToken(tokenData.getGlobalTokenId(), tokenData.getClientId());
		String userId = userIdCacheService.getActualUserIdForTokenUserId(entity.getUserId(), entity.getMerchant());
		return userId;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GlobalTokenDetailsEntity getGlobalTokenDetailsForToken(String token) {

		String plainTokenString = decryptToken(token, TokenType.LOCAL_TOKEN);
		ITokenGenerationService<TokenDetailsDTO> tokenVersionService = getTokenGenerationService(plainTokenString,
				TokenType.LOCAL_TOKEN);
		TokenDetailsDTO tokenData = tokenVersionService.getTokenDataFromDecryptedToken(plainTokenString);

		GlobalTokenDetailsEntity globalTokenByTokenDetails = getGlobalTokenByTokenDetails(tokenData);

		return globalTokenByTokenDetails;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public TokenDetailsDTO getTokenDetailsForToken(String token) {

		String plainTokenString = decryptToken(token, TokenType.LOCAL_TOKEN);
		ITokenGenerationService<TokenDetailsDTO> tokenVersionService = getTokenGenerationService(plainTokenString,
				TokenType.LOCAL_TOKEN);
		TokenDetailsDTO tokenData = tokenVersionService.getTokenDataFromDecryptedToken(plainTokenString);

		return tokenData;
	}

	@Override
	@Timed
	@Marked
	@Logged
	public GetTransferTokenResponse getTransferToken(GetTransferTokenRequest request) {

		GlobalTokenDetailsEntity globalTokenDetails = getGlobalTokenDetailsForToken(request.getToken());
		if (!isValidTransferToken(request.getTargetImsConsumer())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_IMS_TARGET_CONSUMER.errCode(),
					IMSServiceExceptionCodes.INVALID_IMS_TARGET_CONSUMER.errMsg());
		}
		GetTransferTokenResponse response = new GetTransferTokenResponse();
		TransferTokenDTO transferTokenDetails = new TransferTokenDTO();

		transferTokenDetails.setTransferToken(globalTokenDetails.getGlobalToken());
		transferTokenDetails.setTransferTokenExpiry(globalTokenDetails.getExpiryTime());

		response.setTransferTokenDto(transferTokenDetails);
		return response;
	}

	@Override
	public boolean signOutUser(String userId) {
		if (StringUtils.isNotBlank(userId)) {
			try {
				globalTokenDao.deleteAllTokenForUser(userId);
			} catch (IMSServiceException ex) {
				log.error("Error in signout user with id :" + userId + ". Exception: " + ex.getMessage());
				return false;
			}
		}
		return true;
	}

	private boolean isValidTransferToken(String imsConsumer) {
		final ClientCache clientCache = CacheManager.getInstance().getCache(ClientCache.class);
		Iterator<Client> it = clientCache.getCache().values().iterator();
		while (it.hasNext()) {
			if (imsConsumer.equals(it.next().getImsInternalAlias()))
				return true;
		}
		return false;
	}

	@Override
	public String getTokenVersion(String token) {
		String plainTokenString = decryptToken(token, TokenType.LOCAL_TOKEN);
		ITokenGenerationService<TokenDetailsDTO> tokenVersionService = getTokenGenerationService(plainTokenString,
				TokenType.LOCAL_TOKEN);
		TokenDetailsDTO tokenData = tokenVersionService.getTokenDataFromDecryptedToken(plainTokenString);
		return tokenData.getTokenGenerationServiceVersion();
	}

	@Override
	public int getGTokenIDSetSizeByUserId(String userId) {
		return globalTokenDao.getGTokenIDSetSizeByUserId(userId);
	}

	@Override
	public AccessTokenInformationDTO getAccessTokenFromAuthCode(AccessTokenRequest request) {
		String decryptedTransferToken = decryptToken(request.getAuthCode(), TokenType.AUTH_CODE);
		AuthCodeDetailsDTO authCodeDetailsDTO = null;
		RefreshTokenDetailsEntity globalTokenDetailsEntity = null;
		ITokenGenerationService<AuthCodeDetailsDTO> transferTokenVersionService = getTokenGenerationService(
				decryptedTransferToken, TokenType.TRANSFER_TOKEN);

		try {
			authCodeDetailsDTO = transferTokenVersionService.getTokenDataFromDecryptedToken(decryptedTransferToken);
			globalTokenDetailsEntity = (RefreshTokenDetailsEntity) globalTokenDao
					.getGlobalTokenById(authCodeDetailsDTO.getGlobalTokenId());
		} catch (ClassCastException e) {
			throw new IMSServiceException(IMSAuthenticationExceptionCodes.INVALID_AUTH_CODE.errCode(),
					IMSAuthenticationExceptionCodes.INVALID_AUTH_CODE.errMsg());
		}

		Date currentTime = new Date();
		if (currentTime.after(authCodeDetailsDTO.getExpiryTime())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.AUTH_CODE_EXPIRED.errCode(),
					IMSServiceExceptionCodes.AUTH_CODE_EXPIRED.errMsg());
		}

		if (null != globalTokenDetailsEntity) {

			if (!request.getMerchantId().equals(globalTokenDetailsEntity.getMerchantId())) {
				log.error("Merchant Id mismatched for access tokken generation . Excepted : {}, Received: {} ",
						request.getMerchantId(), globalTokenDetailsEntity.getMerchantId());
				throw new IMSServiceException(IMSServiceExceptionCodes.MERCHANT_MISMATCHED_FOR_ACCESS_TOKEN.errCode(),
						IMSServiceExceptionCodes.MERCHANT_MISMATCHED_FOR_ACCESS_TOKEN.errMsg());
			}

			String serviceVersion = Configuration
					.getGlobalProperty(ConfigurationConstants.OAUTH_TOKEN_GENERATION_SERVICE_VERSION);

			@SuppressWarnings("unchecked")
			ITokenGenerationService<AccessTokenDetailsDTO> tokenVersionService = TokenGenerationServiceFactory
					.getTokenVersionService(serviceVersion, TokenType.LOCAL_TOKEN);

			Date tokenExpiry = getExpiryTimeForOAuthToken(request.getMerchantId(),TokenType.ACCESS_TOKEN);

			AccessTokenDetailsDTO accessTokenDTO = mapRequestToDTO(request, serviceVersion, tokenExpiry,
					authCodeDetailsDTO.getGlobalTokenId());

			String plainToken = tokenVersionService.generatePlainToken(accessTokenDTO);

			String generatedToken;
			try {
				generatedToken = CipherServiceUtil.encrypt(plainToken);
			} catch (CipherException e) {
				log.error("Error while encrypting access token {}", e.getMessage());
				throw new InternalServerException(e);
			}

			return responseCreator(globalTokenDetailsEntity.getGlobalToken(), globalTokenDetailsEntity.getExpiryTime(),
					generatedToken, tokenExpiry);
		} else {
			log.error("no global token exist for global token id {} and get access code reqquest for same is {} ",
					authCodeDetailsDTO.getGlobalTokenId(), request);
			throw new IMSServiceException(IMSServiceExceptionCodes.AUTH_CODE_EXPIRED.errCode(),
					IMSServiceExceptionCodes.AUTH_CODE_EXPIRED.errCode());
		}
	}

	private AccessTokenDetailsDTO mapRequestToDTO(AccessTokenRequest accessTokenrequest, String serviceVersion,
			Date tokenExpiry, String globalTokenId) {
		AccessTokenDetailsDTO accessTokenDTO = new AccessTokenDetailsDTO();
		accessTokenDTO.setExpiryTime(tokenExpiry);
		accessTokenDTO.setGlobalTokenId(globalTokenId);
		if (null != accessTokenrequest)
			accessTokenDTO.setMerhchantId(accessTokenrequest.getMerchantId());
		accessTokenDTO.setTokenGenerationServiceVersion(serviceVersion);
		return accessTokenDTO;
	}

	private AccessTokenInformationDTO responseCreator(String globalToken, Date globalTokenExpiry, String token,
			Date tokenExpiry) {
		AccessTokenInformationDTO response = new AccessTokenInformationDTO();
		response.setGlobalToken(globalToken);
		response.setGlobalTokenExpiry(DateUtil.formatDate(globalTokenExpiry,
				com.snapdeal.ims.common.constant.CommonConstants.TIMESTAMP_FORMAT));
		response.setAccessTokenExpiry(
				DateUtil.formatDate(tokenExpiry, com.snapdeal.ims.common.constant.CommonConstants.TIMESTAMP_FORMAT));
		response.setAccessToken(token);
		return response;
	}

	@Override
	public OAuthTokenInformationDTO getOAuthTokenInfo(String token) {
		return getOAuthTokenDetails(token, TokenType.ACCESS_TOKEN);
	}

}
