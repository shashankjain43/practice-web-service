package com.snapdeal.ims.token.service.impl;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.token.dto.TokenDetailsDTO;
import com.snapdeal.ims.token.service.ITokenGenerationService;

import org.apache.commons.lang.StringUtils;

/**
 * @see com.snapdeal.ims.token.service.ITokenGenerationService
 * 
 * @version V02
 */
public class TokenGenerationServiceV02Impl extends
		AbstractTokenGenerationService implements
		ITokenGenerationService<TokenDetailsDTO> {
	/**
	 * Utility method to get Token data from token string.
	 * 
	 * @param plainTokenString
	 * @return
	 */
	@Override
	public TokenDetailsDTO getTokenDataFromDecryptedToken(
			String plainTokenString) {
		TokenDetailsDTO data = new TokenDetailsDTO();
		plainTokenString = super.unMarshall(data, plainTokenString);
		String[] details = StringUtils.split(plainTokenString,
				ServiceCommonConstants.TOKEN_DELIM);
		if (null != details && details.length == 3) {
			data.setClientId(details[0]);
			data.setGlobalTokenId(details[1]);
		} else {
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_TOKEN.errCode(), 
											  IMSAuthenticationExceptionCodes.INVALID_TOKEN.errMsg());
		}
		return data;

	}

	@Override
	public String generatePlainToken(TokenDetailsDTO tokenDetails) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.marshall(tokenDetails)).append(
				ServiceCommonConstants.TOKEN_DELIM);
		stringBuilder.append(tokenDetails.getClientId()).append(
				ServiceCommonConstants.TOKEN_DELIM);
		stringBuilder.append(tokenDetails.getGlobalTokenId()).append(
				ServiceCommonConstants.TOKEN_DELIM);
		stringBuilder.append(System.nanoTime());
		return stringBuilder.toString();
	}
}