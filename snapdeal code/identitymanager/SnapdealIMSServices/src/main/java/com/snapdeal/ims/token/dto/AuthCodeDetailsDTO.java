package com.snapdeal.ims.token.dto;

import com.snapdeal.ims.token.service.impl.TokenGenerationServiceFactory.TokenType;

import java.util.Date;

import lombok.Data;

@Data
public class AuthCodeDetailsDTO extends TokenMetadata{

   private String globalTokenId;
   private String merchantId;
   private Long expiry;
   private TokenType tokenType;

   // This is not used for creating token, this is a separate data which marks
   // the validity of a token. This is used in renewal flow and sign-out.
   private Date expiryTime;

   public void setExpiryTime(Date expiryTime) {
      this.expiryTime = expiryTime;
      this.expiry = this.expiryTime.getTime();
   }

   public void setExpiry(Long expiry) {
      this.expiry = expiry;
      this.expiryTime = new Date(this.expiry);
   }

}
