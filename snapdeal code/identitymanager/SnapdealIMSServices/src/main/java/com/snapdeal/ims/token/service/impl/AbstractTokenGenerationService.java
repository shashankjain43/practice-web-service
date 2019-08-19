package com.snapdeal.ims.token.service.impl;

import org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.token.dto.TokenMetadata;

/**
 * This class is an utility class with implementation to prepend common detail
 * in a token like version of token generation.
 */
public abstract class AbstractTokenGenerationService {

	/**
	 * This method is used to keep few of the common marshal logic in
	 * TokenGenerationService.
	 * 
	 * @param data
	 * @return
	 */
	protected <T extends TokenMetadata> String marshall(T data) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(data.getTokenGenerationServiceVersion());
		return stringBuilder.toString();
	}

	/**
	 * All the data that are common which are marshalled are decoded using this
	 * method to set in the dto.
	 * 
	 * @param dto
	 * @param plainTokenText
	 * @return
	 */
	protected <T extends TokenMetadata> String unMarshall(T dto,
			String plainTokenText) {
		int firstIndex = StringUtils.indexOf(plainTokenText,
				ServiceCommonConstants.TOKEN_DELIM);
		if (firstIndex != 3) {
			throw new AuthenticationException(
					IMSAuthenticationExceptionCodes.INVALID_TOKEN.errCode(),
					IMSAuthenticationExceptionCodes.INVALID_TOKEN.errMsg());

		}
		dto.setTokenGenerationServiceVersion(StringUtils.substringBefore(
				plainTokenText, ServiceCommonConstants.TOKEN_DELIM));
		return plainTokenText.substring(firstIndex);
	}
}