package com.snapdeal.ims.token.service.impl;

import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.errorcodes.IMSAuthenticationExceptionCodes;
import com.snapdeal.ims.exception.AuthenticationException;
import com.snapdeal.ims.token.dto.TransferTokenDetailsDTO;
import com.snapdeal.ims.token.service.ITokenGenerationService;
import com.snapdeal.ims.token.service.impl.TokenGenerationServiceFactory.TokenType;

import org.apache.commons.lang.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferTokenGenerationServiceV02Impl extends AbstractTokenGenerationService implements
         ITokenGenerationService<TransferTokenDetailsDTO> {

   @Override
   public TransferTokenDetailsDTO getTokenDataFromDecryptedToken(String plainTokenString) {
      TransferTokenDetailsDTO data = new TransferTokenDetailsDTO();
      plainTokenString = super.unMarshall(data, plainTokenString);
      String[] details = StringUtils.split(plainTokenString, ServiceCommonConstants.TOKEN_DELIM);
      if (null != details && details.length == 4) {
         data.setAliasName(details[0]);
         data.setGlobalTokenId(details[1]);
         data.setExpiry(Long.valueOf(details[2]));
         data.setTokenType(TokenType.forName(details[3]));
      } else {
         throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_TOKEN.errCode(),
                  IMSAuthenticationExceptionCodes.INVALID_TOKEN.errMsg());
      }
      if (data.getTokenType() != TokenType.TRANSFER_TOKEN) {
         log.error("Token type is not transfer token.");
         throw new AuthenticationException(IMSAuthenticationExceptionCodes.INVALID_TOKEN.errCode(),
                  IMSAuthenticationExceptionCodes.INVALID_TOKEN.errMsg());
      }
      return data;
   }

   @Override
   public String generatePlainToken(TransferTokenDetailsDTO tokenDetails) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(super.marshall(tokenDetails)).append(ServiceCommonConstants.TOKEN_DELIM);
      stringBuilder.append(tokenDetails.getAliasName()).append(ServiceCommonConstants.TOKEN_DELIM);
      stringBuilder.append(tokenDetails.getGlobalTokenId()).append(
               ServiceCommonConstants.TOKEN_DELIM);
      stringBuilder.append(tokenDetails.getExpiry()).append(ServiceCommonConstants.TOKEN_DELIM);
      stringBuilder.append(TokenType.TRANSFER_TOKEN.getValue());
      return stringBuilder.toString();
   }

}
