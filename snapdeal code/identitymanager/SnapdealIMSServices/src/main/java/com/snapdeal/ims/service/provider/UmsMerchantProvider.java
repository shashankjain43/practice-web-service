package com.snapdeal.ims.service.provider;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UmsMerchantProvider {

   @Autowired
   private AuthorizationContext context;

   public Merchant getMerchant() {
      final String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
      final Merchant merchant = ClientConfiguration.getMerchantById(clientId);
      return merchant;
   }

   public String getClientId() {
      final String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
      return clientId;
   }
}
