package com.snapdeal.merchant.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.mina.http.api.HttpMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.snapdeal.merchant.common.constant.CommonConstants;
import com.snapdeal.merchant.common.constant.RequestMapCreator;
import com.snapdeal.merchant.common.constant.RestURIConstants;
import com.snapdeal.merchant.dto.ClientConfig;
import com.snapdeal.merchant.enums.EnvironmentEnum;
import com.snapdeal.merchant.enums.MPanelRequestHeader;
import com.snapdeal.merchant.enums.ValidResponseEnum;
import com.snapdeal.merchant.errorcodes.RequestExceptionCodes;
import com.snapdeal.merchant.exception.HttpTransportException;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.exceptionResponse.MPanelExceptionResponse;
import com.snapdeal.merchant.request.AbstractRequest;
import com.snapdeal.merchant.response.AbstractResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUtil {

   HttpSender httpSender = HttpSender.getInstance();

   private static HttpUtil instance = new HttpUtil();

   public static HttpUtil getInstance() {
      return instance;
   }

   private String version;
   private boolean isSecure;
   private EnvironmentEnum environment;

   public String getVersion() {
      return version;
   }

   private HttpUtil() {
      version = ClientConstants.CLIENT_SDK_VERSION;
      isSecure = ClientConstants.IS_SECURE_ENABLED;
      environment = ClientConstants.ENVIRONMENT;
   }

   public String getCompleteUrl(String relativeUrl) {
      return ClientDetails.getInstance().getUrl() + relativeUrl;
   }

   public <T extends AbstractRequest, R extends AbstractResponse> R processHttpRequest(
            String completeUrl, Class<R> type, T request, HttpMethod method)
                     throws MerchantException, HttpTransportException {
      final Map<String, String> parameters = RequestMapCreator.getMap(request);

      R response = null;
      int statusCode = CommonConstants.DEFAULT_ERROR_STATUS_CODE;
      try {

         long startTime = System.currentTimeMillis();
         if (request.getClientConfig() == null) {
            request.setClientConfig(getDefaultClientConfig());
         }
         Map<String, String> header = createHeader(request, method);

         HttpResponse result = executeHttpMethod(completeUrl, parameters, header, method,
                  request.getClientConfig());
         if (log.isDebugEnabled()) {
            log.debug("Time taken for executing is :: " + (System.currentTimeMillis() - startTime)
                     + " ms");
         }
         if (result != null && result.getStatusLine() != null) {
            statusCode = result.getStatusLine().getStatusCode();
            String json = EntityUtils.toString(result.getEntity());
            ObjectMapper mapper = new ObjectMapper();

            for (ValidResponseEnum status : ValidResponseEnum.values()) {
               if (statusCode == status.getValue()) {
                  try {
                     mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                              false);
                     response = mapper.readValue(json, type);
                  } catch (IOException e) {
                     throw new HttpTransportException(e.getMessage(),
                              RequestExceptionCodes.INTERNAL_CLIENT.getErrCode());
                  }
                  return response;
               }
            }
            handleInvalidResult(result, json, mapper);
         }
      } catch (HttpTransportException e) {
         throw e;
      } catch (MerchantException e) {
         throw e;
      } catch (Exception e) {
         log.error("Error in executing service", e);
         throw new HttpTransportException(e.getMessage(),
                  RequestExceptionCodes.INTERNAL_CLIENT.getErrCode());
      }
      return response;
   }

   private HttpResponse executeHttpMethod(String completeUrl, Map<String, String> parameters,
            Map<String, String> header,

            HttpMethod method, ClientConfig requestConfig) throws MerchantException {
      HttpResponse result = null;
      switch (method) {
         case GET:
            result = httpSender.executeGet(completeUrl, parameters, header, requestConfig);
            break;
         case PUT:
            result = httpSender.executePut(completeUrl, parameters, header, requestConfig);
            break;
         case POST:
            result = httpSender.executePost(completeUrl, parameters, header, requestConfig);
            break;
         case DELETE:
            result = httpSender.executeDelete(completeUrl, parameters, header, requestConfig);
            break;
         default:
            throw new UnsupportedOperationException(
                     "Server doesn't support http method: " + method);
      }
      return result;
   }

   private Map<String, String> createHeader(AbstractRequest request, HttpMethod method)
            throws HttpTransportException, MerchantException {
      long timeStamp = System.currentTimeMillis();
      ClientConfig clientConfig = request.getClientConfig();

      Map<String, String> header = new HashMap<String, String>();
      header.put(MPanelRequestHeader.CONTENT_TYPE.toString(), RestURIConstants.APPLICATION_JSON);
      header.put(MPanelRequestHeader.ACCEPT.toString(), RestURIConstants.APPLICATION_JSON);
      header.put(MPanelRequestHeader.CLIENT_SDK_VERSION.toString(), getVersion());
      header.put(MPanelRequestHeader.TIMESTAMP.toString(), String.valueOf(timeStamp));

      // TODO ask what to do in if else condition

      if (method == HttpMethod.GET || method == HttpMethod.DELETE) {
         /*
          * header.put(MPanelRequestHeader.HASH.toString(),
          * CheckSumCalculator
          * .generateSHA256CheckSum(getClientIdFromRequestConfig(clientConfig)
          * + getClientKeyFromRequestConfig(clientConfig) + timeStamp));
          */
      } else {
         /*
          * header.put(MPanelRequestHeader.HASH.toString(),
          * CheckSumCalculator.generateSHA256CheckSum(request.
          * getHashGenerationString()
          * + getClientKeyFromRequestConfig(clientConfig) + timeStamp));
          */ }
      return header;
   }

   private String getClientIpAddress() throws HttpTransportException {
      InetAddress ip;
      String hostIpAddress = null;

      try {
         ip = InetAddress.getLocalHost();
         hostIpAddress = ip.getHostAddress();
      } catch (UnknownHostException e) {
         throw new HttpTransportException(e.getMessage(),
                  RequestExceptionCodes.INTERNAL_CLIENT.getErrCode());
      }
      return hostIpAddress;
   }

   private void handleInvalidResult(HttpResponse result, String json, ObjectMapper mapper)
            throws MerchantException {

      MPanelExceptionResponse se = null;
      try {
         se = mapper.readValue(json, MPanelExceptionResponse.class);
         throw new MerchantException(se.getMessage(), se.getErrorCode());
      } catch (MerchantException e) {
         throw e;
      } catch (Exception e) {
         throw new HttpTransportException(e.getMessage(),
                  RequestExceptionCodes.INTERNAL_CLIENT.getErrCode());
      }

   }

   public EnvironmentEnum getEnvironment() {
      return environment;
   }

   public boolean getIsSecure() {
      return isSecure;
   }

   private ClientConfig getDefaultClientConfig() throws MerchantException {

      ClientConfig config = new ClientConfig();
      config.setApiTimeOut(ClientDetails.getInstance().getApiTimeOut());
      return config;
   }

}
