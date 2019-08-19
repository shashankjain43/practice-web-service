package com.snapdeal.ims.token.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.dto.OAuthTokenInformationDTO;
import com.snapdeal.ims.dto.TransferTokenDTO;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.response.GetTransferTokenResponse;
import com.snapdeal.ims.token.dto.AuthCodeDetailsDTO;
import com.snapdeal.ims.token.dto.TransferTokenDetailsDTO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.ims.token.service.ITokenGenerationService;
import com.snapdeal.ims.token.service.ITokenService;
import com.snapdeal.ims.token.service.ITransferTokenService;
import com.snapdeal.ims.token.service.impl.TokenGenerationServiceFactory.TokenType;
import com.snapdeal.ims.utils.CipherServiceUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransferTokenServiceImpl extends AbstractTokenService implements ITransferTokenService {

	@Autowired
	private ITokenService tokenService;

	@Autowired
	IActivityDataService activityDataService;

	@Override
	public String getGlobalTokenByTransferToken(String token) {
		String plainTokenString = decryptToken(token, TokenType.TRANSFER_TOKEN);
		ITokenGenerationService<TransferTokenDetailsDTO> transferTokenVersionService = getTokenGenerationService(
				plainTokenString, TokenType.TRANSFER_TOKEN);
		TransferTokenDetailsDTO tokenData = transferTokenVersionService
				.getTokenDataFromDecryptedToken(plainTokenString);

		// Check the expiration validity of transfer token
		Date currentTime = new Date();
		if (currentTime.after(tokenData.getExpiryTime())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.TRANSFER_TOKEN_EXPIRED.errCode(),
					IMSServiceExceptionCodes.TRANSFER_TOKEN_EXPIRED.errMsg());
		}

		GlobalTokenDetailsEntity gTokenDetails = globalTokenDao.getGlobalTokenById(tokenData.getGlobalTokenId());
		if (null == gTokenDetails) {
			log.error("Global token expired for transfer token : " + token);
			throw new IMSServiceException(IMSServiceExceptionCodes.TRANSFER_TOKEN_EXPIRED.errCode(),
					IMSServiceExceptionCodes.TRANSFER_TOKEN_EXPIRED.errMsg());
		}
		activityDataService.setActivityDataByUserId(gTokenDetails.getUserId());
		return gTokenDetails.getGlobalToken();
	}

	@Override
	public GetTransferTokenResponse getTransferToken(GetTransferTokenRequest request, String clientId) {
		GlobalTokenDetailsEntity globalTokenDetails = tokenService.getGlobalTokenDetailsForToken(request.getToken());
		activityDataService.setActivityDataByUserId(globalTokenDetails.getUserId());
		if (!isValidTransferToken(request.getTargetImsConsumer())) {
			throw new IMSServiceException(IMSServiceExceptionCodes.INVALID_IMS_TARGET_CONSUMER.errCode(),
					IMSServiceExceptionCodes.INVALID_IMS_TARGET_CONSUMER.errMsg());
		}
		GetTransferTokenResponse response = new GetTransferTokenResponse();
		TransferTokenDTO transferTokenDetails = generateTransferTokenDetails(globalTokenDetails.getGlobalTokenId(),
				clientId, request.getTargetImsConsumer());

		response.setTransferTokenDto(transferTokenDetails);
		return response;
	}

	private boolean isValidTransferToken(String imsConsumer) {

		// Check if imsConsumer is supported.
		final ClientCache clientCache = CacheManager.getInstance().getCache(ClientCache.class);
		if (clientCache.getAlliasNames().contains(imsConsumer)) {
			return true;
		}
		return false;
	}

	private TransferTokenDTO generateTransferTokenDetails(String globalTokenId, String clientId, String aliasName) {
		// Get TokenService of particular version.
		String serviceVersion = Configuration
				.getGlobalProperty(ConfigurationConstants.DEFAULT_TRANSFER_TOKEN_GENERATION_SERVICE_VERSION);

		// get expiryInterval in seconds from configuration and convert that to
		// milliseconds
		Long expiryInterval = Long
				.valueOf(Configuration.getGlobalProperty(ConfigurationConstants.TRANSFER_TOKEN_EXPIRY_TIME)) * 1000;
		Long transferTokenExpiry = System.currentTimeMillis() + expiryInterval;

		@SuppressWarnings("unchecked")
		ITokenGenerationService<TransferTokenDetailsDTO> transferTokenVersionService = TokenGenerationServiceFactory
				.getTokenVersionService(serviceVersion, TokenType.TRANSFER_TOKEN);
		// Generate plain token from login token request and encrypt using
		// cipherService.
		TransferTokenDetailsDTO tokenDetails = mapRequestToDTO(globalTokenId, clientId, aliasName, serviceVersion,
				transferTokenExpiry);
		String plainToken = transferTokenVersionService.generatePlainToken(tokenDetails);
		String token;
		try {
			token = CipherServiceUtil.encrypt(plainToken);
		} catch (CipherException e) {
			throw new InternalServerException(e);
		}

		// Set TransferTokenDTO from token and tokenDetails
		TransferTokenDTO transferTokenDetails = new TransferTokenDTO();

		transferTokenDetails.setTransferToken(token);
		transferTokenDetails.setTransferTokenExpiry(tokenDetails.getExpiryTime());

		return transferTokenDetails;
	}

	/**
	 * Utility method to create {@link TransferTokenDetailsDTO} from request.
	 * 
	 */
	private TransferTokenDetailsDTO mapRequestToDTO(String globalTokenId, String clientId, String aliasName,
			String tokenGenerationServiceVersion, Long expiry) {
		TransferTokenDetailsDTO tokenDetails = new TransferTokenDetailsDTO();

		tokenDetails.setTokenGenerationServiceVersion(tokenGenerationServiceVersion);
		tokenDetails.setGlobalTokenId(globalTokenId);
		tokenDetails.setAliasName(aliasName);
		tokenDetails.setExpiry(expiry);
		return tokenDetails;
	}

	@Override
	public String generateAuthCodeDetails(String globalTokenId, String merchantId) {
		// Get TokenService of particular version.
		String serviceVersion = Configuration
				.getGlobalProperty(ConfigurationConstants.OAUTH_CODE_GENERATION_SERVICE_VERSION);

		// get expiryInterval in seconds from configuration and convert that to
		// milliseconds
		Long expiryInterval = Long
				.valueOf(Configuration.getGlobalProperty(ConfigurationConstants.OAUTH_CODE_EXPIRY_TIME)) * 1000;
		Long transferTokenExpiry = System.currentTimeMillis() + expiryInterval;

		@SuppressWarnings("unchecked")
		ITokenGenerationService<AuthCodeDetailsDTO> transferTokenVersionService = TokenGenerationServiceFactory
				.getTokenVersionService(serviceVersion, TokenType.TRANSFER_TOKEN);
		// Generate plain token from login token request and encrypt using
		// cipherService.
		AuthCodeDetailsDTO tokenDetails = mapRequestToDTO(globalTokenId, merchantId, serviceVersion,
				transferTokenExpiry);
		String plainToken = transferTokenVersionService.generatePlainToken(tokenDetails);
		String token;
		try {
			token = CipherServiceUtil.encrypt(plainToken);
		} catch (CipherException e) {
			throw new InternalServerException(e);
		}

		return token;
	}

	/**
	 * Utility method to create {@link AuthCodeDetailsDTO} from request.
	 * 
	 */
	private AuthCodeDetailsDTO mapRequestToDTO(String globalTokenId, String merchantId,
			String tokenGenerationServiceVersion, Long expiry) {
		AuthCodeDetailsDTO tokenDetails = new AuthCodeDetailsDTO();

		tokenDetails.setTokenGenerationServiceVersion(tokenGenerationServiceVersion);
		tokenDetails.setGlobalTokenId(globalTokenId);
		tokenDetails.setMerchantId(merchantId);
		tokenDetails.setExpiry(expiry);
		return tokenDetails;
	}

	@Override
	public OAuthTokenInformationDTO getOAuthTokenInfo(String token) {
		return getOAuthTokenDetails(token, TokenType.AUTH_CODE);
	}

}
