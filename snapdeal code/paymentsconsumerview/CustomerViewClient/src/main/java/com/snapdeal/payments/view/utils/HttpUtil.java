package com.snapdeal.payments.view.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.avalon.framework.service.ServiceException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.mina.http.api.HttpMethod;
import org.codehaus.jackson.map.DeserializationConfig;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapdeal.payments.authorize.core.AuthorizationClient;
import com.snapdeal.payments.authorize.core.model.AuthorizationParams;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.constant.RestURIConstants;
import com.snapdeal.payments.view.commons.enums.PaymentsViewRequestHeaders;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.client.HttpTransportException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewDefaultExceptionCodes;
import com.snapdeal.payments.view.commons.request.AbstractRequest;
import com.snapdeal.payments.view.commons.request.ClientConfig;
import com.snapdeal.payments.view.commons.response.AbstractResponse;
import com.snapdeal.payments.view.commons.response.ServiceResponse;
import com.snapdeal.payments.view.commons.utils.CheckSumUtil;
import com.snapdeal.payments.view.enums.EnvironmentEnum;
import com.snapdeal.payments.view.enums.ValidResponseEnum;

@Slf4j
public class HttpUtil {
	
	private AuthorizationClient authClient = new AuthorizationClient();

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
		environment = ClientConstants.ENVIRONMENT;
	}

	public String getCompleteUrl(String relativeUrl) {
		return ClientDetails.getInstance().getUrl() + relativeUrl;
	}

	public <T extends AbstractRequest, R> R 
		processHttpRequestNew(String completeUrl, 
				TypeReference<ServiceResponse<R>> typeReference, 
							T request,
							HttpMethod method)throws PaymentsViewServiceException {
		 
		final Map<String,String> parameters = RequestMapCreator
				.getMap(request);

		R response = null;
		int statusCode = PaymentsViewConstants.DEFAULT_ERROR_STATUS_CODE;
		try {

			long startTime = System.currentTimeMillis();	
	      if (request.getClientConfig() == null) {
	         request.setClientConfig(getDefaultClientConfig());
	      }
	      Map<String, String> header = createHeader(request,method);
	      if(log.isDebugEnabled()) {
			   log.debug("UserAgent :  " + request.getUserAgent()
			   		+ ", UserMachineIdenifier " +  request.getUserMachineIdentifier());
			}
			HttpResponse result = executeHttpMethod(completeUrl, parameters,
					header, method, request.getClientConfig());
			if(log.isDebugEnabled()) {
			   log.debug("Time taken by Request Id " + request.getClientConfig().getAppRequestId()+" for executing is :: " +  (System.currentTimeMillis() - startTime) + " ms");
			}
			if (result != null && result.getStatusLine() != null) {
				statusCode = result.getStatusLine().getStatusCode();
				String json = EntityUtils.toString(result.getEntity());
				ObjectMapper mapper = new ObjectMapper();

				for (ValidResponseEnum status : ValidResponseEnum.values()) {
					if (statusCode == status.getValue()) {
						try {
						   mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							response = mapper.readValue(json, typeReference);
						} catch (IOException e) {
							throw new HttpTransportException(e.getMessage(),
									PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
						}
						return response;
					}
				}
				handleInvalidResultNew(result, json, mapper);
			}
		}
		catch (HttpTransportException e) {
			throw e;
		}
		catch (PaymentsViewServiceException e) {
			throw e;
		}
		catch (Exception e) {
		   log.error("Error in executing service", e);
			throw new HttpTransportException(e.getMessage(), PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		return response;
}
	
	public <T extends AbstractRequest, R extends AbstractResponse> R processHttpRequest(String completeUrl, 
						Class<R> type, 
						T request,
						HttpMethod method)throws PaymentsViewServiceException {
	 
	final Map<String,String> parameters = RequestMapCreator
			.getMap(request);

	R response = null;
	int statusCode = PaymentsViewConstants.DEFAULT_ERROR_STATUS_CODE;
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
			org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();

			for (ValidResponseEnum status : ValidResponseEnum.values()) {
				if (statusCode == status.getValue()) {
					try {
						mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						response = mapper.readValue(json, type);
					} catch (IOException e) {
						throw new HttpTransportException(e.getMessage(),
								PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
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
	catch (PaymentsViewServiceException e) {
		throw e;
	}
	catch (Exception e) {
	   log.error("Error in executing service", e);
		throw new HttpTransportException(e.getMessage(), PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
	}
	return response;
}

	private HttpResponse executeHttpMethod(String completeUrl,
										   Map<String, String> parameters, 
										   Map<String, String> header,
										   HttpMethod method, 
										   ClientConfig requestConfig) throws ServiceException {
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
			throws Exception {
	   long timeStamp = System.currentTimeMillis();
	   ClientConfig clientConfig = request.getClientConfig();
	  
		Map<String, String> header = new HashMap<String, String>();
		
		header.put(PaymentsViewRequestHeaders.CONTENT_TYPE.toString(),
		           RestURIConstants.APPLICATION_JSON);
		header.put(PaymentsViewRequestHeaders.ACCEPT.toString(),
		           RestURIConstants.APPLICATION_JSON);
		header.put(PaymentsViewRequestHeaders.CLIENT_NAME.toString(),
				getClientNameFromRequestConfig(clientConfig));
		header.put(PaymentsViewRequestHeaders.CLIENT_SDK_VERSION.toString(),
		           getVersion());
		header.put(PaymentsViewRequestHeaders.TIMESTAMP.toString(),
		           String.valueOf(timeStamp));
		header.put(PaymentsViewRequestHeaders.USER_AGENT.toString(),
		           request.getUserAgent());
		header.put(PaymentsViewRequestHeaders.USER_MACHINE_IDENTIFIER.toString(),
		           request.getUserMachineIdentifier());
		header.put(PaymentsViewRequestHeaders.APP_REQUEST_ID.toString(), 
				 getAppRequestId(request.getClientConfig()));
		header.put(PaymentsViewRequestHeaders.TOKEN.toString(),request.getToken()) ;
		 request.setClientConfig(null);
		header.put(
					PaymentsViewRequestHeaders.HASH.toString(),
					CheckSumUtil.generateChecksum(
							request, 
							getClientKeyFromRequestConfig(clientConfig)));
		request.setClientConfig(clientConfig);
		//add auth params
		AuthorizationParams authorizationParams = new AuthorizationParams(
				ClientDetails.getInstance().getClientId(), ClientDetails.getInstance().getClientKey());
		return authClient.UpdateRequest(header, request, authorizationParams);
	}


	private void handleInvalidResultNew(HttpResponse result, String json,
			ObjectMapper mapper) throws PaymentsViewServiceException {

		PaymentsViewServiceException se = null;
		try {
			se = mapper.readValue(json, PaymentsViewServiceException.class);
			throw new PaymentsViewServiceException(se.getErrCode(),se.getMessage());
		}
		catch (PaymentsViewServiceException e) {
			throw e;
		}
		catch (Exception e) {
			throw new HttpTransportException(e.getMessage(), PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		
	}
	
	private void handleInvalidResult(HttpResponse result, String json,
			org.codehaus.jackson.map.ObjectMapper mapper) throws PaymentsViewServiceException {

		PaymentsViewServiceException se = null;
		try {
			se = mapper.readValue(json, PaymentsViewServiceException.class);
			throw new PaymentsViewServiceException(se.getMessage(), se.getErrCode());
		}
		catch (PaymentsViewServiceException e) {
			throw e;
		}
		catch (Exception e) {
			throw new HttpTransportException(e.getMessage(), PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		
	}

	public EnvironmentEnum getEnvironment() {
		return environment;
	}

	public boolean getIsSecure() {
		return isSecure;
	}
	
	private String getClientNameFromRequestConfig(ClientConfig config) {

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