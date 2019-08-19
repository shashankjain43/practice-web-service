package com.snapdeal.ims.token.service;

import com.snapdeal.ims.dto.OAuthTokenInformationDTO;
import com.snapdeal.ims.request.GetTransferTokenRequest;
import com.snapdeal.ims.response.GetTransferTokenResponse;

public interface ITransferTokenService {

   public String getGlobalTokenByTransferToken(String token);
   
   public GetTransferTokenResponse getTransferToken(GetTransferTokenRequest request, String clientId);

   public String generateAuthCodeDetails(String globalTokenId, String merchantId);
   
   public OAuthTokenInformationDTO getOAuthTokenInfo(String token);
}
