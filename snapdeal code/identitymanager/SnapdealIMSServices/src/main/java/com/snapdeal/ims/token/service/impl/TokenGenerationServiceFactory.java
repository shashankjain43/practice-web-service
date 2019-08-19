package com.snapdeal.ims.token.service.impl;

import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.token.dto.TokenMetadata;
import com.snapdeal.ims.token.service.ITokenGenerationService;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory class for creating token version service.
 */
@Slf4j
public class TokenGenerationServiceFactory {

	/**
	 * Store TokenGeneration service in hash map for reuse.
	 */
	private static Map<String, ITokenGenerationService<TokenMetadata>> cache = new HashMap<String, ITokenGenerationService<TokenMetadata>>();

	/**
	 * Private constructor.
	 */
	private TokenGenerationServiceFactory() {
	}

	/**
	 * Return {@link ITokenGenerationService} of particular version, this will
	 * mainly be used for backward compatibility.
	 * 
	 * @param version
	 * @param tokentype
	 * @return {@link ITokenGenerationService}
	 */
	@SuppressWarnings("rawtypes")
	public static ITokenGenerationService getTokenVersionService(String version, TokenType tokentype) {

		ITokenGenerationService tokenGenService = null;
		String versionService = null;
		switch (tokentype) {
		case GLOBAL_TOKEN:
			versionService = Configuration.getGlobalProperty(ConfigurationConstants.GLOBAL_TOKEN_GENERATION_SERVICE,
					version);
			break;

		case LOCAL_TOKEN:
			versionService = Configuration.getGlobalProperty(ConfigurationConstants.TOKEN_GENERATION_SERVICE, version);
			break;

		case TRANSFER_TOKEN:
			versionService = Configuration.getGlobalProperty(ConfigurationConstants.TRANSFER_TOKEN_GENERATION_SERVICE,
					version);
			break;

		}

		String errorMessage = "Token Generation Service not supported for version:" + version + " and type : "
				+ tokentype;
		if (null != versionService && !versionService.isEmpty()) {
			try {
				tokenGenService = cache.get(versionService);
				if (null == tokenGenService) {
					tokenGenService = (ITokenGenerationService) Class.forName(versionService).newInstance();
					cache.put(versionService, tokenGenService);
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				log.warn(errorMessage, e);
				throw new InternalServerException(
						IMSInternalServerExceptionCodes.TOKEN_GENERATION_SERVICE_NOT_SUPPORTED.errCode(),
						IMSInternalServerExceptionCodes.TOKEN_GENERATION_SERVICE_NOT_SUPPORTED.errMsg());
			}
		} else {
			log.warn(errorMessage);
			throw new InternalServerException(
					IMSInternalServerExceptionCodes.TOKEN_GENERATION_SERVICE_NOT_SUPPORTED.errCode(),
					IMSInternalServerExceptionCodes.TOKEN_GENERATION_SERVICE_NOT_SUPPORTED.errMsg());
		}
		return tokenGenService;
	}

	public static enum TokenType {
		GLOBAL_TOKEN("G"), LOCAL_TOKEN("L"), TRANSFER_TOKEN("T"), ACCESS_TOKEN("AT"), AUTH_CODE("AC"), REFRESH_TOKEN(
				"RT");
		@Getter
		private String value;

		private TokenType(String value) {
			this.value = value;
		}

		public static TokenType forName(String value) {
			TokenType tokenType = null;
			if (StringUtils.isNotBlank(value)) {
				for (TokenType type : values()) {
					if (type.value.equals(value)) {
						tokenType = type;
						break;
					}
				}
			}
			return tokenType;
		}
	}
}