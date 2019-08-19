package com.snapdeal.ims.token.service.impl;

import org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.token.dto.GlobalTokenDetailsDTO;
import com.snapdeal.ims.token.service.ITokenGenerationService;

public class GlobalTokenGenerationServiceV01Impl extends
		AbstractTokenGenerationService implements
		ITokenGenerationService<GlobalTokenDetailsDTO> {
	/**
	 * @see com.snapdeal.identity.token.service.IGlobalTokenGenerationService#generatePlainToken(GlobalTokenDetailsDTO)
	 */
	@Override
	public String generatePlainToken(GlobalTokenDetailsDTO globalTokenDetails) {
		StringBuilder plainToken = new StringBuilder();
		plainToken.append(super.marshall(globalTokenDetails));
		plainToken.append(ServiceCommonConstants.TOKEN_DELIM);
		plainToken.append(globalTokenDetails.getGlobalTokenId());
		return plainToken.toString();
	}

	/**
	 * @see com.snapdeal.ims.token.service.ITokenGenerationService#getTokenDataFromDecryptedToken(String)
	 */
	@Override
	public GlobalTokenDetailsDTO getTokenDataFromDecryptedToken(
			String plainTokenString) {

		GlobalTokenDetailsDTO globalTokenDetailsDTO = new GlobalTokenDetailsDTO();
		plainTokenString = super.unMarshall(globalTokenDetailsDTO,
				plainTokenString);
		String[] details = StringUtils.split(plainTokenString,
				ServiceCommonConstants.TOKEN_DELIM);
		if (null != details && details.length == 1) {
			globalTokenDetailsDTO.setGlobalTokenId(details[0]);
		} else {
			throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN.errCode(), 
					  IMSAuthenticationExceptionCodes.INVALID_GLOBAL_TOKEN.errMsg());
		}
		return globalTokenDetailsDTO;
	}
}