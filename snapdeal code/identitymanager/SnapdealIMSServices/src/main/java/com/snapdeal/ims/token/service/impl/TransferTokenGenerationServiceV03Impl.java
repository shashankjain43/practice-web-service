package com.snapdeal.ims.token.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.token.dto.AuthCodeDetailsDTO;
import com.snapdeal.ims.token.service.ITokenGenerationService;
import com.snapdeal.ims.token.service.impl.TokenGenerationServiceFactory.TokenType;

@Slf4j
public class TransferTokenGenerationServiceV03Impl extends AbstractTokenGenerationService implements
         ITokenGenerationService<AuthCodeDetailsDTO> {

   @Override
   public AuthCodeDetailsDTO getTokenDataFromDecryptedToken(String plainTokenString) {
	  AuthCodeDetailsDTO data = new AuthCodeDetailsDTO();
      plainTokenString = super.unMarshall(data, plainTokenString);
      String[] details = StringUtils.split(plainTokenString, ServiceCommonConstants.TOKEN_DELIM);
      if (null != details && details.length == 4) {
         data.setMerchantId(details[0]);
         data.setGlobalTokenId(details[1]);
         data.setExpiry(Long.valueOf(details[2]));
         data.setTokenType(TokenType.forName(details[3]));
      } else {
         throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_AUTH_CODE.errCode(),
                  IMSAuthenticationExceptionCodes.INVALID_AUTH_CODE.errMsg());
      }
      if (data.getTokenType() != TokenType.TRANSFER_TOKEN) {
         log.error("Token type is not transfer token.");
         throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_AUTH_CODE.errCode(),
                  IMSAuthenticationExceptionCodes.INVALID_AUTH_CODE.errMsg());
      }
      return data;
   }

   @Override
   public String generatePlainToken(AuthCodeDetailsDTO tokenDetails) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(super.marshall(tokenDetails)).append(ServiceCommonConstants.TOKEN_DELIM);
      stringBuilder.append(tokenDetails.getMerchantId()).append(ServiceCommonConstants.TOKEN_DELIM);
      stringBuilder.append(tokenDetails.getGlobalTokenId()).append(
               ServiceCommonConstants.TOKEN_DELIM);
      stringBuilder.append(tokenDetails.getExpiry()).append(ServiceCommonConstants.TOKEN_DELIM);
      stringBuilder.append(TokenType.TRANSFER_TOKEN.getValue());
      return stringBuilder.toString();
   }

}
