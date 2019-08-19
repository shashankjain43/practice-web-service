package com.snapdeal.ims.utils;

import com.snapdeal.ims.common.CheckSumCalculator;
import com.snapdeal.ims.common.RequestMapCreator;
import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.common.constant.ValidResponseEnum;
import com.snapdeal.ims.dto.ClientConfig;
import com.snapdeal.ims.enums.EnvironmentEnum;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.IMSExceptionResponse;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.AbstractRequest;
import com.snapdeal.ims.response.AbstractResponse;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.mina.http.api.HttpMethod;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
			throws ServiceException,HttpTransportException {
		final Map<String, String> parameters = RequestMapCreator
				.getMap(request);

		R response = null;
		int statusCode = CommonConstants.DEFAULT_ERROR_STATUS_CODE;
		try {

			long startTime = System.currentTimeMillis();	
	      if (request.getClientConfig() == null) {
	         request.setClientConfig(getDefaultClientConfig());
	      }
	      Map<String, String> header = createHeader(request,method);

			HttpResponse result = executeHttpMethod(completeUrl, parameters,
					header, method, request.getClientConfig());
			if(log.isDebugEnabled()) {
			   log.debug("Time taken by Request Id " + request.getClientConfig().getAppRequestId()+" for executing is :: " +  (System.currentTimeMillis() - startTime) + " ms");
			}
			if (result != null && result.getStatusLine() != null) {
				statusCode = result.getStatusLine().getStatusCode();
				String json = EntityUtils.toString(result.getEntity());
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				for (ValidResponseEnum status : ValidResponseEnum.values()) {
					if (statusCode == status.getValue()) {
						try {
							response = mapper.readValue(json, type);
						} catch (IOException e) {
							throw new HttpTransportException(e.getMessage(),
									IMSDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
						}
						return response;
					}
				}
				handleInvalidResult(result, json, mapper);
			}
		}
		catch (HttpTransportException e) {
			throw e;
		}
		catch (ServiceException e) {
			throw e;
		}
		catch (Exception e) {
		   log.error("Error in executing service", e);
			throw new HttpTransportException(e.getMessage(), IMSDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		return response;
}

	private HttpResponse executeHttpMethod(String completeUrl,
			Map<String, String> parameters, Map<String, String> header,

			HttpMethod method, ClientConfig requestConfig) throws ServiceException {
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
			throws HttpTransportException,ServiceException {
	   long timeStamp = System.currentTimeMillis();
	   ClientConfig clientConfig = request.getClientConfig();
	   
		Map<String, String> header = new HashMap<String, String>();
		header.put(IMSRequestHeaders.CONTENT_TYPE.toString(),
		           RestURIConstants.APPLICATION_JSON);
		header.put(IMSRequestHeaders.ACCEPT.toString(),
		           RestURIConstants.APPLICATION_JSON);
		header.put(IMSRequestHeaders.CLIENT_ID.toString(),
				getClientIdFromRequestConfig(clientConfig));
		header.put(IMSRequestHeaders.CLIENT_SDK_VERSION.toString(),
		           getVersion());
		header.put(IMSRequestHeaders.TIMESTAMP.toString(),
		           String.valueOf(timeStamp));
		header.put(IMSRequestHeaders.USER_AGENT.toString(),
		           request.getUserAgent());
		header.put(IMSRequestHeaders.USER_MACHINE_IDENTIFIER.toString(),
		           request.getUserMachineIdentifier());
		header.put(IMSRequestHeaders.APP_REQUEST_ID.toString(), clientConfig.getAppRequestId());
		
		if(method == HttpMethod.GET || method == HttpMethod.DELETE) {
			header.put(
					IMSRequestHeaders.HASH.toString(),
					CheckSumCalculator
							.generateSHA256CheckSum(getClientIdFromRequestConfig(clientConfig)
									+ getClientKeyFromRequestConfig(clientConfig) + timeStamp));
		} else {
			header.put(IMSRequestHeaders.HASH.toString(), CheckSumCalculator
					.generateSHA256CheckSum(request.getHashGenerationString()
		+ getClientKeyFromRequestConfig(clientConfig) + timeStamp));
		}
		return header;
	}

	private String getClientIpAddress() throws HttpTransportException {
		InetAddress ip;
		String hostIpAddress = null;

		try {
			ip = InetAddress.getLocalHost();
			hostIpAddress = ip.getHostAddress();
		} catch (UnknownHostException e) {
			throw new HttpTransportException(e.getMessage(), IMSDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		return hostIpAddress;
	}

	private void handleInvalidResult(HttpResponse result, String json,
			ObjectMapper mapper) throws ServiceException {
	   
		IMSExceptionResponse se = null;
      try {
         String startTime = null;
         String endTime = null;
         Header[] headers = result.getAllHeaders();
         for (Header header : headers) {
            if (header.getName().equalsIgnoreCase(ClientConstants.REQUEST_RECV_TIMESTAMP))
               startTime = header.getValue();
            if (header.getName().equalsIgnoreCase(ClientConstants.RESPONSE_SENT_TIMESTAMP)) {
               endTime = header.getValue();
            }
         }
         se = mapper.readValue(json, IMSExceptionResponse.class);
         throw new ServiceException(se.getMessage(), se.getErrorCode(), startTime, endTime);
      }
		catch (ServiceException e) {
			throw e;
		}
		catch (Exception e) {
			throw new HttpTransportException(e.getMessage(), IMSDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		
	}

	public EnvironmentEnum getEnvironment() {
		return environment;
	}

	public boolean getIsSecure() {
		return isSecure;
	}
	
	private String getClientIdFromRequestConfig(ClientConfig config) {

		if (config == null || config.getClientId() == null) {

			return ClientDetails.getInstance().getClientId();
		}
		return config.getClientId();
	}

	private String getClientKeyFromRequestConfig(ClientConfig config)
			throws ServiceException {

		if (config == null || config.getClientKey() == null) {

			return ClientDetails.getInstance().getClientKey();
		}
		return config.getClientKey();
	}
	
	private ClientConfig getDefaultClientConfig() throws ServiceException {

		ClientConfig config = new ClientConfig();
		config.setApiTimeOut(ClientDetails.getInstance().getApiTimeOut());
		config.setClientId(ClientDetails.getInstance().getClientId());
		config.setClientKey(ClientDetails.getInstance().getClientKey());
		return config;
	}
	
	private String getAppRequestId(ClientConfig clientConfig){
		   if(clientConfig == null || StringUtils.isEmpty(clientConfig.getAppRequestId())){
			   return UUID.randomUUID().toString();
		   }else{
			   return clientConfig.getAppRequestId() ;
		   }
	}
}