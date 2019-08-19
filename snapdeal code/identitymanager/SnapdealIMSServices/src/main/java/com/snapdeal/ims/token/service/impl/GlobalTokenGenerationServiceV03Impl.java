package com.snapdeal.ims.token.service.impl;

import org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.token.dto.GlobalTokenDetailsDTO;
import com.snapdeal.ims.token.dto.RefreshTokenDetailsDTO;
import com.snapdeal.ims.token.service.ITokenGenerationService;

public class GlobalTokenGenerationServiceV03Impl extends
		AbstractTokenGenerationService implements
		ITokenGenerationService<RefreshTokenDetailsDTO> {
	/**
	 * @see com.snapdeal.identity.token.service.IGlobalTokenGenerationService#generatePlainToken(GlobalTokenDetailsDTO)
	 */
	@Override
	public String generatePlainToken(RefreshTokenDetailsDTO refreshTokenDetails) {
		StringBuilder plainToken = new StringBuilder();
		plainToken.append(super.marshall(refreshTokenDetails));
		plainToken.append(ServiceCommonConstants.TOKEN_DELIM);
		plainToken.append(refreshTokenDetails.getGlobalTokenId());
		return plainToken.toString();
	}

	/**
	 * @see com.snapdeal.ims.token.service.ITokenGenerationService#getTokenDataFromDecryptedToken(String)
	 */
	@Override
	public RefreshTokenDetailsDTO getTokenDataFromDecryptedToken(
			String plainTokenString) {
		RefreshTokenDetailsDTO globalTokenDetailsDTO = new RefreshTokenDetailsDTO();
		plainTokenString = super.unMarshall(globalTokenDetailsDTO,
				plainTokenString);
		String[] details = StringUtils.split(plainTokenString,
				ServiceCommonConstants.TOKEN_DELIM);
		if (null != details && details.length == 1) {
			globalTokenDetailsDTO.setGlobalTokenId(details[0]);
		} else {
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_REFRESH_TOKEN.errCode(), 
					  IMSAuthenticationExceptionCodes.INVALID_REFRESH_TOKEN.errMsg());
		}
		return globalTokenDetailsDTO;
	}
}