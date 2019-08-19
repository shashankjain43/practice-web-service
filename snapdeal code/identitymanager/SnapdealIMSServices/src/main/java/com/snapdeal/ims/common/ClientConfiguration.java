package com.snapdeal.ims.common;

import lombok.extern.slf4j.Slf4j;

import com.snapdeal.ims.authorize.exception.AuthorizationException;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.cache.ICache;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.dbmapper.entity.info.ClientPlatform;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.errorcodes.IMSAuthorizationExceptionCodes;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.InternalServerException;

@Slf4j
public class ClientConfiguration {
   
   public static Client getClientById(String clientId) {
      
      if (clientId == null)
         return null;
      
      final ICache<String, Client> clientCache = 
         CacheManager.getInstance().getCache(ClientCache.class);
      
      if (null == clientCache || null == clientCache.get(clientId)) {
         log.error("Configuration not present for client id:" + clientId);
         throw new InternalServerException(IMSInternalServerExceptionCodes.CLIENT_CONFIGURATION_NOT_PRESENT.errCode(),
                                           IMSInternalServerExceptionCodes.CLIENT_CONFIGURATION_NOT_PRESENT.errMsg());
      }
   
      return clientCache.get(clientId);
   }
   
   public static Merchant getMerchantById(String clientId) {
      
      if (clientId == null) {
         throw new AuthorizationException(IMSAuthorizationExceptionCodes.CLIENT_ID_NOT_PROVIDED.errCode(),
                                          IMSAuthorizationExceptionCodes.CLIENT_ID_NOT_PROVIDED.errMsg());
      }
      
      final Client client = getClientById(clientId);
      if (client == null || client.getMerchant() == null) {
         throw new InternalServerException(IMSInternalServerExceptionCodes.CLIENT_CONFIGURATION_PARAMETER_MISSING.errCode(),
                                           IMSInternalServerExceptionCodes.CLIENT_CONFIGURATION_PARAMETER_MISSING.errMsg());
      } 
      
      return client.getMerchant();
   }

   public static ClientPlatform getClientPlatform(String clientId) {
	   if (clientId == null) {
	         throw new AuthorizationException(IMSAuthorizationExceptionCodes.CLIENT_ID_NOT_PROVIDED.errCode(),
	                                          IMSAuthorizationExceptionCodes.CLIENT_ID_NOT_PROVIDED.errMsg());
	      }
	      
	      final Client client = getClientById(clientId);
	      if (client == null || client.getMerchant() == null) {
	         throw new InternalServerException(IMSInternalServerExceptionCodes.CLIENT_CONFIGURATION_PARAMETER_MISSING.errCode(),
	                                           IMSInternalServerExceptionCodes.CLIENT_CONFIGURATION_PARAMETER_MISSING.errMsg());
	      } 
	      
	      return client.getClientPlatform();
   }
}