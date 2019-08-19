package com.snapdeal.ims.token.service.impl;

import java.util.Date;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dto.OAuthTokenInformationDTO;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.request.GetUserByIdRequest;
import com.snapdeal.ims.request.UserUpgradeByEmailRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.UserUpgradationResponse;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.token.dao.IGlobalTokenDetailsDAO;
import com.snapdeal.ims.token.dto.AccessTokenDetailsDTO;
import com.snapdeal.ims.token.dto.AuthCodeDetailsDTO;
import com.snapdeal.ims.token.dto.GlobalTokenDetailsDTO;
import com.snapdeal.ims.token.dto.RefreshTokenDetailsDTO;
import com.snapdeal.ims.token.dto.TokenMetadata;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.entity.RefreshTokenDetailsEntity;
import com.snapdeal.ims.token.service.ITokenGenerationService;
import com.snapdeal.ims.token.service.impl.TokenGenerationServiceFactory.TokenType;
import com.snapdeal.ims.utils.CipherServiceUtil;

@Slf4j
@Setter
public abstract class AbstractTokenService {

	@Autowired
	protected IGlobalTokenDetailsDAO globalTokenDao;

	@Qualifier("userMigrationService")
	@Autowired
	private IUserMigrationService migrationService;

	@Qualifier("IMSService")
	@Autowired
	private IUMSService imsService;

	/**
	 * Utility method to extract {@link ITokenGenerationService} version used to
	 * generate the token.
	 * 
	 * @param plainToken
	 * @return
	 */
	protected String getVersionFromToken(String plainToken) {
		String version = StringUtils.substringBefore(plainToken, ServiceCommonConstants.TOKEN_DELIM);
		return version;
	}

	/**
	 * Get token expiry time based on client. Highest priority for expiry time
	 * is merchant level, then client id.
	 * 
	 * 
	 * @param clientId
	 * @return
	 */
	protected Date getTokenExpiryTime(String clientId) {

		Merchant merchantById = ClientConfiguration.getMerchantById(clientId);
		String expiryTime = Configuration.getClientProperty(merchantById.getMerchantName(),
				ConfigurationConstants.GLOBAL_TOKEN_EXPIRY_TIME);
		if (StringUtils.isBlank(expiryTime) || !StringUtils.isNumeric(expiryTime)) {
			expiryTime = Configuration.getClientProperty(ClientConfiguration.getClientPlatform(clientId).getValue(),
					ConfigurationConstants.GLOBAL_TOKEN_EXPIRY_TIME);
			if (StringUtils.isBlank(expiryTime) || !StringUtils.isNumeric(expiryTime)) {
				if (log.isWarnEnabled()) {
					log.warn("Token Expiry time is not configured for client ID: " + clientId);
				}
				expiryTime = Configuration.getGlobalProperty(ConfigurationConstants.DEFAULT_GLOBAL_TOKEN_EXPIRY_TIME);
			}
		}
		log.debug("Expiry time set is : " + expiryTime + ", for client : " + clientId);
		return DateUtils.addDays(new Date(), Integer.valueOf(expiryTime));
	}

	/**
	 * Get token expiry time based on client. Highest priority for expiry time
	 * is merchant level, then client id.
	 * 
	 * 
	 * @param clientId
	 * @return
	 */
	protected Date getExpiryTimeForOAuthToken(String merchantId, TokenType tokenType) {
		String expiryTime = null;
		String expriryTimeDefault = null;
		switch (tokenType) {
		case ACCESS_TOKEN:
			expiryTime = Configuration.getGlobalProperty(ConfigurationConstants.OAUTH_ACCESS_TOKEN_EXPIRY_TIME);
			expriryTimeDefault = Configuration
					.getGlobalProperty(ConfigurationConstants.OAUTH_DEFAULT_ACCESS_TOKEN_EXPIRY_TIME);
			break;
		case AUTH_CODE:
			expiryTime = Configuration.getGlobalProperty(ConfigurationConstants.OAUTH_REFRESH_TOKEN_EXPIRY_TIME);
			expriryTimeDefault = Configuration
					.getGlobalProperty(ConfigurationConstants.OAUTH_DEFAULT_REFRESH_TOKEN_EXPIRY_TIME);
			break;
		default:
			log.error("Invalid token type for getting expiry time {}", tokenType);
			throw new InternalServerException();
		}
		if (StringUtils.isBlank(expiryTime) || !StringUtils.isNumeric(expiryTime)) {
			if (log.isWarnEnabled()) {
				log.warn("Auth code Expiry time is not configured ");
			}
			expiryTime = expriryTimeDefault;
		}
		log.debug("Expiry time set is : " + expiryTime + ", for auth token");
		return DateUtils.addDays(new Date(), Integer.valueOf(expiryTime));
	}

	protected String decryptToken(String token, TokenType tokenType) {
		try {
			return CipherServiceUtil.decrypt(token);
		} catch (CipherException e) {
			IMSAuthenticationExceptionCodes errorCode = null;
			switch (tokenType) {
			case GLOBAL_TOKEN:
				errorCode = IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN;
				break;
			case LOCAL_TOKEN:
				errorCode = IMSAuthenticationExceptionCodes.INVALID_TOKEN;
				break;
			case ACCESS_TOKEN:
				errorCode = IMSAuthenticationExceptionCodes.INVALID_ACCESS_TOKEN;
				break;
			case AUTH_CODE:
				errorCode = IMSAuthenticationExceptionCodes.INVALID_AUTH_CODE;
				break;
			case REFRESH_TOKEN:
				errorCode = IMSAuthenticationExceptionCodes.INVALID_REFRESH_TOKEN;
				break;
			default:
				log.error("Invalid token type while decrypting token: ", tokenType);
				throw new InternalServerException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
						IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
			}
			throw new AuthenticationException(errorCode.errCode(), errorCode.errMsg());
		}
	}

	/**
	 * Overloaded method to renew token expiry.
	 * 
	 * @param globalTokenId
	 * @param clientId
	 */
	protected void renewToken(String globalTokenId, String clientId) {
		GlobalTokenDetailsEntity globalTokenDetails = globalTokenDao.getGlobalTokenById(globalTokenId);
		if (globalTokenDetails == null) {
			throw new IMSServiceException(IMSAuthenticationExceptionCodes.INVALID_TOKEN.errCode(),
					IMSAuthenticationExceptionCodes.INVALID_TOKEN.errMsg());
		}
		renewTokenExpiry(globalTokenDetails, clientId);
	}

	/**
	 * Renew token expiry.
	 * 
	 * @param globalTokenDetails
	 * @param clientId
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	protected void renewTokenExpiry(GlobalTokenDetailsEntity globalTokenDetails, String clientId) {

		// Get renew configuration based on client merchant.
		String daysToRenew = Configuration.getClientProperty(
				ClientConfiguration.getMerchantById(clientId).getMerchantName(),
				ConfigurationConstants.DAYS_TO_RENEW_TOKEN);
		if (StringUtils.isBlank(daysToRenew)) {
			daysToRenew = Configuration.getGlobalProperty(ConfigurationConstants.DAYS_TO_RENEW_TOKEN);
		}
		// If the days to expire is less then days to renew for an application,
		// new expiry time is set for global token.
		if (daysToExpire(globalTokenDetails.getExpiryTime()) < Integer.valueOf(daysToRenew)) {
			if (log.isDebugEnabled()) {
				log.debug("Renew expiry date for token");
			}
			globalTokenDetails.setExpiryTime(getTokenExpiryTime(clientId));
			globalTokenDao.updateExpiryDate(globalTokenDetails);
		}
	}

	/**
	 * Days To Expire in days.
	 * 
	 * @param expiryTime
	 * @return
	 */
	protected int daysToExpire(Date expiryTime) {
		long diff = Math.abs(System.currentTimeMillis() - expiryTime.getTime());
		return (int) (diff / (1000 * 60 * 60 * 24));
	}

	/**
	 * Utility method to
	 * 
	 * @param plainToken
	 * @param tokenType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T extends TokenMetadata> ITokenGenerationService<T> getTokenGenerationService(String plainToken,
			TokenType tokenType) {
		ITokenGenerationService<T> tokenVersionService = null;
		try {
			// Get TokenService of particular version.
			tokenVersionService = TokenGenerationServiceFactory.getTokenVersionService(getVersionFromToken(plainToken),
					tokenType);
		} catch (InternalServerException ex) {
			if (log.isWarnEnabled())
				log.warn("Exception occured trying to get token generation version for token", ex.getMessage());
			// Overriding exception with invalid global token exception.
			ex.setErrCode(IMSAuthenticationExceptionCodes.INVALID_TOKEN.errCode());
			ex.setErrMsg(IMSAuthenticationExceptionCodes.INVALID_TOKEN.errMsg());
			throw ex;
		}
		return tokenVersionService;
	}

	/**
	 * Validate if global token if for OC password verification.
	 * 
	 * @param globalToken
	 * @return
	 */
	protected boolean isValid(String globalToken) {
		String plainToken = decryptToken(globalToken, TokenType.GLOBAL_TOKEN);
		ITokenGenerationService<TokenMetadata> tokenGenerationService = getTokenGenerationService(plainToken,
				TokenType.GLOBAL_TOKEN);
		GlobalTokenDetailsDTO tokenDataFromDecryptedToken = (GlobalTokenDetailsDTO) tokenGenerationService
				.getTokenDataFromDecryptedToken(plainToken);
		String tokenVersionOCCheck = Configuration
				.getGlobalProperty(ConfigurationConstants.LINK_UPGRADE_GLOBAL_TOKEN_GENERATION_SERVICE_VERSION);
		boolean isValid = true;
		// if global token is for OC password verification in link migration
		// case.
		if (tokenVersionOCCheck.equals(tokenDataFromDecryptedToken.getTokenGenerationServiceVersion())) {
			isValid = false;
			GlobalTokenDetailsEntity globalTokenById = globalTokenDao
					.getGlobalTokenById(tokenDataFromDecryptedToken.getGlobalTokenId());
			if (null == globalTokenById) {
				throw new AuthorizationException();
			}
			GetUserByIdRequest req = new GetUserByIdRequest();
			req.setUserId(globalTokenById.getUserId());
			GetUserResponse userById = imsService.getUser(req);
			if (null == userById) {
				log.error("User doesn't exits for user id: " + tokenDataFromDecryptedToken.getUserId());
				log.error("Token used to access resource is of version : V02, and this could be fraud transaction.");
				throw new AuthorizationException();
			}
			UserUpgradeByEmailRequest userUpgradeByEmailRequest = new UserUpgradeByEmailRequest();
			userUpgradeByEmailRequest.setEmailId(userById.getUserDetails().getEmailId());
			UserUpgradationResponse userUpgradeStatus = migrationService.getUserUpgradeStatus(userUpgradeByEmailRequest,
					false);
			// if user upgrade is completed, then return true.
			if (userUpgradeStatus.getUpgradationInformation().getUpgrade() == Upgrade.UPGRADE_COMPLETED) {
				isValid = true;
			} else {
				throw new AuthorizationException();
			}
		}
		return isValid;
	}

	@SuppressWarnings("unchecked")
	protected OAuthTokenInformationDTO getOAuthTokenDetails(String token, TokenType tokenType) {
		OAuthTokenInformationDTO oauthTokenInfo = new OAuthTokenInformationDTO();
		String decryptedToken = decryptToken(token, tokenType);
		String globalTokenId = null;
		String serviceVersion = null;
		IMSAuthenticationExceptionCodes invalidTokenExcCode = null;
		RefreshTokenDetailsEntity refreshEntity = null;
		Date currentTime = new Date();

		if (log.isInfoEnabled())
			log.info("getting global token id for given token {} and token type {} : ", token, tokenType);

		switch (tokenType) {
		case REFRESH_TOKEN:
			serviceVersion = Configuration
					.getGlobalProperty(ConfigurationConstants.OAUTH_REFRESH_TOKEN_GENERATION_SERVICE_VERSION);
			ITokenGenerationService<RefreshTokenDetailsDTO> globalTokenService = TokenGenerationServiceFactory
					.getTokenVersionService(serviceVersion, TokenType.GLOBAL_TOKEN);
			RefreshTokenDetailsDTO refreshTokenDTO = globalTokenService.getTokenDataFromDecryptedToken(decryptedToken);
			invalidTokenExcCode = IMSAuthenticationExceptionCodes.INVALID_REFRESH_TOKEN;
			globalTokenId = refreshTokenDTO.getGlobalTokenId();
			break;
		case ACCESS_TOKEN:
			serviceVersion = Configuration
					.getGlobalProperty(ConfigurationConstants.OAUTH_TOKEN_GENERATION_SERVICE_VERSION);
			ITokenGenerationService<AccessTokenDetailsDTO> tokenVersionService = TokenGenerationServiceFactory
					.getTokenVersionService(serviceVersion, TokenType.LOCAL_TOKEN);
			AccessTokenDetailsDTO accessTokenDTO = tokenVersionService.getTokenDataFromDecryptedToken(decryptedToken);
			if (currentTime.after(accessTokenDTO.getExpiryTime())) {
				throw new IMSServiceException(IMSServiceExceptionCodes.ACCESS_TOKEN_EXPIRED.errCode(),
						IMSServiceExceptionCodes.ACCESS_TOKEN_EXPIRED.errMsg());
			}
			invalidTokenExcCode = IMSAuthenticationExceptionCodes.INVALID_ACCESS_TOKEN;
			globalTokenId = accessTokenDTO.getGlobalTokenId();
			break;
		case AUTH_CODE:
			serviceVersion = Configuration
					.getGlobalProperty(ConfigurationConstants.OAUTH_CODE_GENERATION_SERVICE_VERSION);
			ITokenGenerationService<AuthCodeDetailsDTO> transferTokenVersionService = TokenGenerationServiceFactory
					.getTokenVersionService(serviceVersion, TokenType.TRANSFER_TOKEN);
			AuthCodeDetailsDTO authCodeDetailsDTO = transferTokenVersionService
					.getTokenDataFromDecryptedToken(decryptedToken);
			if (currentTime.after(authCodeDetailsDTO.getExpiryTime())) {
				throw new IMSServiceException(IMSServiceExceptionCodes.AUTH_CODE_EXPIRED.errCode(),
						IMSServiceExceptionCodes.AUTH_CODE_EXPIRED.errMsg());
			}
			invalidTokenExcCode = IMSAuthenticationExceptionCodes.INVALID_AUTH_CODE;
			globalTokenId = authCodeDetailsDTO.getGlobalTokenId();
			break;
		default:
			log.error("Invalid token type for Getting Oauth token details which is : {}", tokenType);
			throw new InternalServerException(IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}

		if (log.isInfoEnabled())
			log.info("global token id for token {} is : {}", token, globalTokenId);

		if (null != globalTokenId) {
			GlobalTokenDetailsEntity globalTokenDetailsEntity = globalTokenDao.getGlobalTokenById(globalTokenId);

			if (log.isInfoEnabled())
				log.info("fetched global token details against global token id {} is : {}  ", globalTokenId,
						globalTokenDetailsEntity);
			if (globalTokenDetailsEntity instanceof RefreshTokenDetailsEntity)
				refreshEntity = (RefreshTokenDetailsEntity) globalTokenDetailsEntity;
			else
				throw new IMSServiceException(invalidTokenExcCode.errCode(), invalidTokenExcCode.errMsg());

			if (null != refreshEntity) {
				oauthTokenInfo.setMerchantId(refreshEntity.getMerchantId());
				oauthTokenInfo.setUserId(refreshEntity.getUserId());
			}
		}
		return oauthTokenInfo;
	}
}