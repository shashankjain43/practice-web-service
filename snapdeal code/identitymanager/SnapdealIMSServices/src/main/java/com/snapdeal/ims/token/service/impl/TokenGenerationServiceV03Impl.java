package com.snapdeal.ims.token.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.token.dto.AccessTokenDetailsDTO;
import com.snapdeal.ims.token.service.ITokenGenerationService;

/**
 * @see com.snapdeal.ims.token.service.ITokenGenerationService
 * 
 * @version V03
 */
public class TokenGenerationServiceV03Impl extends
		AbstractTokenGenerationService implements
		ITokenGenerationService<AccessTokenDetailsDTO> {
	/**
	 * Utility method to get Token data from token string.
	 * 
	 * @param plainTokenString
	 * @return
	 */
	@Override
	public AccessTokenDetailsDTO getTokenDataFromDecryptedToken(
			String plainTokenString) {
		AccessTokenDetailsDTO data = new AccessTokenDetailsDTO();
		plainTokenString = super.unMarshall(data, plainTokenString);
		String[] details = StringUtils.split(plainTokenString,
				ServiceCommonConstants.TOKEN_DELIM);
		if (null != details && details.length == 3) {
			data.setMerhchantId(details[0]);
			data.setGlobalTokenId(details[1]);
			data.setExpiry(Long.valueOf(details[2]));
		} else {
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_ACCESS_TOKEN.errCode(), 
											  IMSAuthenticationExceptionCodes.INVALID_ACCESS_TOKEN.errMsg());
		}
		return data;

	}

	@Override
	public String generatePlainToken(AccessTokenDetailsDTO tokenDetails) {

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(super.marshall(tokenDetails)).append(
				ServiceCommonConstants.TOKEN_DELIM);
		stringBuilder.append(tokenDetails.getMerhchantId()).append(
				ServiceCommonConstants.TOKEN_DELIM);
		stringBuilder.append(tokenDetails.getGlobalTokenId()).append(
				ServiceCommonConstants.TOKEN_DELIM);
		stringBuilder.append(tokenDetails.getExpiry());
		return stringBuilder.toString();
	}
}