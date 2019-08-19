package com.snapdeal.ims.authorization.impl;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.authorize.IAuthorizeRequest;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.WhiteListAPICache;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.common.CheckSumCalculator;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.errorcodes.IMSAuthorizationExceptionCodes;
import com.snapdeal.ims.exception.AuthorizationException;
import com.snapdeal.ims.exception.IMSGenericException;
import com.snapdeal.ims.request.AbstractRequest;
import com.snapdeal.ims.response.AbstractResponse;
import com.snapdeal.payments.interceptors.RequestHeaders;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;

/**
 * This method validates the client based on clientId and Ipaddress and request
 * on the basis of hash which are being sent from ClientSide upon using @Authorize
 * annotation
 * 
 * @author kishan
 *
 */

@Component
@Slf4j
public class ImsRequestAuthorizationImpl implements IAuthorizeRequest {

   @Autowired
   private AuthorizationContext context;
   
   private String CLIENT_SECRET_ID = "CLIENT_SECRET";

   @Override
   @Marked
   @Timed
   @RequestAware
   public boolean authorizeHeadersAndApi(HttpServletRequest request, String requestedMethod) {
      populateHeaders(request);
      Client client = ClientConfiguration.getClientById(
               context.get(IMSRequestHeaders.CLIENT_ID.toString()));
      if (client != null) {
         context.set(CLIENT_SECRET_ID, client.getClientKey());
      } else {
         throw new AuthorizationException(
                  IMSAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT.errCode(),
                  IMSAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT.errMsg());
      }
      
      // if API is restricted then send false;
      if(!isAPIWhiteListed(request, client, requestedMethod)){
         throw new AuthorizationException(
                  IMSAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT.errCode(),
                  IMSAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT.errMsg());
      }

      return isRequestExpired();
      

      // TODO Kishan to discuss with team and do internal and external api
      // whitelisting and ip whitelisting only for client
      /*
       * ConfigCache cache = CacheManager.getInstance().getCache(
       * ConfigCache.class); Map<String, String> clientConfig = null; if
       * (cache != null) { clientConfig =
       * cache.get(context.get(IMSRequestHeaders.CLIENT_ID .toString())); //
       * check whether client id and ip address are authorized or not String
       * validIpAddressOfClient = clientConfig
       * .get(IMSRequestHeaders.CLIENT_IP_ADDRESS.toString()); if
       * (validIpAddressOfClient == null) { throw new AuthorizationException(
       * IMSAuthorizationExceptionCodes.IP_ADDRESS_NOT_PROVIDED .errCode(),
       * IMSAuthorizationExceptionCodes.IP_ADDRESS_NOT_PROVIDED .errMsg()); }
       * if (!context.get(IMSRequestHeaders.CLIENT_IP_ADDRESS.toString())
       * .equalsIgnoreCase(validIpAddressOfClient)) { throw new
       * AuthorizationException(
       * IMSAuthorizationExceptionCodes.UNAUTHORIZED_CLIENT_IP_ADDRESS
       * .errCode(), IMSAuthorizationExceptionCodes.1 .errMsg()); } boolean
       * permittedApi = Boolean.parseBoolean(clientConfig
       * .get(requestedMethod)); if (permittedApi) {
       * log.info("----Access granted to client with id " +
       * context.get(IMSRequestHeaders.CLIENT_ID.toString()) +
       * " and ip address: " + context.get(IMSRequestHeaders.CLIENT_IP_ADDRESS
       * .toString()) + "----"); return true; }
       * 
       * } return false;
       */
   }
   
   private boolean isAPIWhiteListed(HttpServletRequest request, Client client, String requestedMethod){

      boolean isWhitelistingEnabled = Boolean.valueOf(Configuration
               .getGlobalProperty(ConfigurationConstants.WHITELIST_APIS_ENABLED));
      
      // if API whitelisting disabled then return true for all APIS
      if(!isWhitelistingEnabled){
         return true;
      }
      
      WhiteListAPICache whiteListAPICache = CacheManager
               .getInstance().getCache(WhiteListAPICache.class);
      
      String clientId = client.getClientId();
      String requestURIPattern = (String) request.getAttribute(ServiceCommonConstants.HTTP_REQUEST_URI_PATTERN_KEY);
      // remove double slash from URI pattern 
      requestURIPattern = requestURIPattern.replaceAll("//", "/");

      String requestHTTPMethod = request.getMethod();
      Boolean value = whiteListAPICache.get(clientId, requestURIPattern, requestHTTPMethod);
      
      if(value != null){
         return value;
      }
      
      // Fetch default value from configuration if not whitelisted for a particular client
      // First fetch for merchant then for global
      
      String merchant = getMerchant(clientId);
		Boolean defaultValue = null;
		String isWhileApiAllowedDefaulValue = Configuration.getClientProperty(
				merchant,
				ConfigurationConstants.WHITELIST_APIS_IS_ALLOWED_DEFAULT_VALUE);

		if (isWhileApiAllowedDefaulValue == null) {
			isWhileApiAllowedDefaulValue = Configuration
					.getGlobalProperty(ConfigurationConstants.WHITELIST_APIS_IS_ALLOWED_DEFAULT_VALUE);
		}

		defaultValue = Boolean.valueOf(isWhileApiAllowedDefaulValue);

		return defaultValue;

   }
   
   private String getMerchant(String clientId) {
      return ClientConfiguration.getMerchantById(clientId).getMerchantName();
   }

   private boolean isRequestExpired() {
      
      long currentTime = System.currentTimeMillis();
      long timeDiff = 
              currentTime - Long.parseLong(context.get(IMSRequestHeaders.TIMESTAMP.toString()));
      
      // get expiry time in seconds from database
      if (timeDiff / 1000 < Long.parseLong(Configuration.getGlobalProperty(ConfigurationConstants.REQUEST_TTL))) {
         return true;
      } else {
         throw new AuthorizationException(IMSAuthorizationExceptionCodes.REQUEST_TIMEOUT.errCode(),
                                          IMSAuthorizationExceptionCodes.REQUEST_TIMEOUT.errMsg());
      }      
   }

   @Override
   public boolean isValidCheckSum(Object obj) throws AuthorizationException {
      
      boolean isValidRequest;

      String clientHash = context.get(IMSRequestHeaders.HASH.toString());
      if (!(obj instanceof AbstractRequest)) {
         isValidRequest = checkSumMatched(clientHash, null);
      } else {
         AbstractRequest request = (AbstractRequest) obj;
         isValidRequest = checkSumMatched(clientHash, request);
      }
      
      return isValidRequest;
   }

   private boolean checkSumMatched(String checkSum,
                                   AbstractRequest request)
      throws AuthorizationException {

      String clientSecretKey = context.get(CLIENT_SECRET_ID);
      if (clientSecretKey == null) {
         throw new AuthorizationException(IMSAuthorizationExceptionCodes.CLIENT_SECRET_NOT_PROVIDED.errCode(),
                                          IMSAuthorizationExceptionCodes.CLIENT_SECRET_NOT_PROVIDED.errMsg());
      }
      String calculatedCheckSum;
      if(context.get(IMSRequestHeaders.HTTPMETHOD.toString()).equals(HttpMethod.GET.toString())
         || context.get(IMSRequestHeaders.HTTPMETHOD.toString()).equals(HttpMethod.DELETE.toString())){
         calculatedCheckSum = 
            CheckSumCalculator.generateSHA256CheckSum(context.get(IMSRequestHeaders.CLIENT_ID.toString())
                                                      + clientSecretKey
                                                      + context.get(IMSRequestHeaders.TIMESTAMP.toString()));
         if (calculatedCheckSum.equals(checkSum)) {
            return true;
         }
     } else {
          try {
            calculatedCheckSum = 
               CheckSumCalculator.generateSHA256CheckSum(request.getHashGenerationString()
                                                         + clientSecretKey
                                                         + context.get(IMSRequestHeaders.TIMESTAMP.toString()));
               if (calculatedCheckSum.equals(checkSum)) {
                  return true;
               }
           } catch (Exception e) {
               throw new AuthorizationException(IMSAuthorizationExceptionCodes.INVALID_REQUEST.errCode(),
                                                IMSAuthorizationExceptionCodes.INVALID_REQUEST.errMsg());
           }         
      }
      return false;
   }

   private void populateHeaders(HttpServletRequest request) {
      String ipAddress = request.getHeader("X-FORWARDED-FOR");
      if (ipAddress == null) {
         ipAddress = request.getRemoteAddr();
      }
      if (ipAddress == null) {
         throw new AuthorizationException(
                  IMSAuthorizationExceptionCodes.CLIENT_IP_ADDRESS_NOT_PROVIDED.errCode(),
                  IMSAuthorizationExceptionCodes.CLIENT_IP_ADDRESS_NOT_PROVIDED.errMsg());
      }
      context.set(IMSRequestHeaders.CLIENT_IP_ADDRESS.toString(), ipAddress);
      String clientId = request.getHeader((IMSRequestHeaders.CLIENT_ID.toString()));
      if (clientId == null) {
         throw new AuthorizationException(
                  IMSAuthorizationExceptionCodes.CLIENT_ID_NOT_PROVIDED.errCode(),
                  IMSAuthorizationExceptionCodes.CLIENT_ID_NOT_PROVIDED.errMsg());
      }
      context.set(IMSRequestHeaders.CLIENT_ID.toString(), clientId);
      context.set(IMSRequestHeaders.USER_AGENT.toString(),
                  request.getHeader(IMSRequestHeaders.USER_AGENT.toString()));
      context.set(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString(),
                  request.getHeader(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()));
      context.set(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString(),
                  request.getHeader(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString()));
      context.set(IMSRequestHeaders.HASH.toString(),
                  request.getHeader(IMSRequestHeaders.HASH.toString()));
      context.set(IMSRequestHeaders.TIMESTAMP.toString(),
                  request.getHeader(IMSRequestHeaders.TIMESTAMP.toString()));
      context.set(IMSRequestHeaders.HTTPMETHOD.toString(),
                  request.getMethod().toString());
		context.set(IMSRequestHeaders.SERVER_IP_ADDRESS.toString(),
				request.getLocalAddr());
		context.set(IMSRequestHeaders.APP_REQUEST_ID.toString(), request.getHeader(RequestHeaders.APP_REQUEST_ID
						.getName()));
		context.set(IMSRequestHeaders.START_TIME.toString(),String.valueOf(System.currentTimeMillis()));
	}

   @Override
   public boolean resetContext(Object response) {
      if(context.get(IMSRequestHeaders.START_TIME.toString()) !=null) {
    	  if(response instanceof AbstractResponse){
    		  AbstractResponse abstractResponse = AbstractResponse.class.cast(response);
    	         abstractResponse.setStartTime(context.get(IMSRequestHeaders.START_TIME.toString()));
    	         abstractResponse.setEndTime(String.valueOf(System.currentTimeMillis()));
    	  }        
      }
      return true;
   }
}
