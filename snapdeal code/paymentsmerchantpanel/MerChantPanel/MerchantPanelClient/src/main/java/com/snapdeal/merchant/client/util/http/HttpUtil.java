package com.snapdeal.merchant.client.util.http;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.mina.http.api.HttpMethod;
import org.codehaus.jackson.map.ObjectMapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.snapdeal.merchant.client.exception.HttpTransportException;
import com.snapdeal.merchant.client.util.ClientDefaultExceptionCodes;
import com.snapdeal.merchant.client.util.ClientRequestHeaders;
import com.snapdeal.merchant.client.util.ValidResponseEnum;
import com.snapdeal.merchant.client.util.RequestMapCreator;
import com.snapdeal.merchant.client.util.RestURIConstants;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.AbstractMerchantRequest;
import com.snapdeal.merchant.request.AbstractRequest;
import com.snapdeal.merchant.response.AbstractResponse;

public class HttpUtil {

	HttpSender httpSender = HttpSender.getInstance();

	private static HttpUtil instance = new HttpUtil();

	public static HttpUtil getInstance() {
		return instance;
	}

	private String version;
	private boolean isSecure;
	private EnvironmentEnum environment;

	private String getVersion() {
		return version;
	}

	private HttpUtil() {
		Properties properties = new Properties();
		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream("mpanel/client.properties");
		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {

			}
		}
		version = properties.getProperty("CLIENT_SDK_VERSION");
		isSecure = Boolean.parseBoolean(properties
				.getProperty(ClientConstants.ISSECURE));
		environment = EnvironmentEnum.valueOf((properties
				.getProperty(ClientConstants.ENVIRONMENT)));

	}

	public String getCompleteUrl(String relativeUrl) {
		return ClientDetails.getInstance().getUrl() + relativeUrl;
	}

	@SuppressWarnings("unchecked")
	public <T extends AbstractRequest, R extends AbstractResponse> R processHttpRequest(
			String completeUrl, Type responseType, Class<R> type, T request, HttpMethod method)
			throws MerchantException {
		final Map<String, String> parameters = RequestMapCreator
				.getMap(request);

		GenericMerchantResponse response = null;
		R data = null;
		try {

			Map<String, String> header = createHeader(request, method);

			HttpResponse result = executeHttpMethod(completeUrl, parameters,
					header, method);

			if (result != null && result.getStatusLine() != null) {
				//statusCode = result.getStatusLine().getStatusCode();
				String responseJson = EntityUtils.toString(result.getEntity());
				//Gson gson = new Gson();

				GsonBuilder builder = new GsonBuilder(); 

				// Register an adapter to manage the date types as long values 
				builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() { 
				   public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
				      return new Date(json.getAsJsonPrimitive().getAsLong()); 
				   } 
				});

				Gson gson = builder.create();
				
				response = getGenericResponse(result, responseJson, gson,responseType);
				data = (R) response.getData();
			}
		} catch (MerchantException e) {
			throw e;
		} catch (Exception e) {
			throw new MerchantException(e.getMessage(),
					ClientDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		return data;
	}

	private HttpResponse executeHttpMethod(String completeUrl,
			Map<String, String> parameters, Map<String, String> header,
			HttpMethod method) throws HttpTransportException, MerchantException {

		HttpResponse result = null;
		switch (method) {
		case GET:
			result = httpSender.executeGet(completeUrl, null, header);
			break;
		case PUT:
			result = httpSender.executePut(completeUrl, parameters, header);
			break;
		case POST:
			result = httpSender.executePost(completeUrl, parameters, header);
			break;
		case DELETE:
			result = httpSender.executeDelete(completeUrl, parameters, header);
			break;
		default:
			throw new UnsupportedOperationException(
					"Server doesn't support http method: " + method);
		}
		return result;
	}

	private Map<String, String> createHeader(AbstractRequest request,
			HttpMethod method) throws Exception {
		long timeStamp = System.currentTimeMillis();
		Map<String, String> header = new HashMap<String, String>();
		header.put(ClientRequestHeaders.CONTENT_TYPE.toString(),
				RestURIConstants.APPLICATION_JSON);
		header.put(ClientRequestHeaders.ACCEPT.toString(),
				RestURIConstants.APPLICATION_JSON);
		
		if(request instanceof AbstractMerchantRequest){
			AbstractMerchantRequest merchantRequest = (AbstractMerchantRequest) request;
			header.put(ClientRequestHeaders.token.toString(),
					merchantRequest.getToken());
			header.put(ClientRequestHeaders.merchantId.toString(),
					merchantRequest.getMerchantId());
		}
		
		return header;
	}

	private GenericMerchantResponse<?> getGenericResponse(HttpResponse result, String json,
			Gson gson, Type responseType) throws MerchantException {

		boolean invalid = true;
		GenericMerchantResponse<?> response = null;
		try {
			// check for any other status code other than 200/201
			for (ValidResponseEnum status : ValidResponseEnum.values()) {
				if (result.getStatusLine().getStatusCode() == status.getValue()) {
					invalid = false;
					break;
				}
			}
			if (invalid) {
				throw new MerchantException(
						ClientDefaultExceptionCodes.SERVICE_EXCEPTION.errCode(),
						ClientDefaultExceptionCodes.SERVICE_EXCEPTION.errMsg());
			}
			response = gson.fromJson(json, responseType);
			if(response == null) {
				throw new MerchantException(
						ClientDefaultExceptionCodes.SERVICE_EXCEPTION.errCode(),
						ClientDefaultExceptionCodes.SERVICE_EXCEPTION.errMsg());
			}
			if (response.getError() != null) {
				throw new MerchantException(response.getError().getErrorCode(),
						response.getError().getErrorMessage());
			}
		} catch (MerchantException e) {
			throw e;
		} catch (Exception e) {
			throw new MerchantException(ClientDefaultExceptionCodes.INTERNAL_CLIENT.errCode(),e.getMessage());
		}
		return response;
	}

	public EnvironmentEnum getEnvironment() {
		return environment;
	}

	public boolean getIsSecure() {
		return isSecure;
	}
}