package com.snapdeal.opspanel.restclient;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.amazonaws.HttpMethod;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseClient {

	private String baseUrl;
	private RestClient restClient;
	Map<String,String> commonHeaders;

	public BaseClient( ClientConfig clientConfig ) { 
		this.baseUrl = clientConfig.getBaseUrl();
		this.commonHeaders = clientConfig.getCommonHeaders();
		restClient = new RestClient( clientConfig );
	}

	public <RequestType, ResponseType> ResponseType executeApi( String[] api, Map<String,String> headers, RequestType request, Class<ResponseType> responseType ) throws Exception {
		Map<String,Object> response = null;

		if( commonHeaders != null ) {
			if( headers != null ) {
				headers.putAll( commonHeaders );
			} else {
				headers = commonHeaders;
			}
		}

		if( request instanceof IMultipartRequest ) {
			IMultipartRequest multipartRequest = ( IMultipartRequest ) request;
			response = restClient.executeMultipartPost( baseUrl + api[ 0 ], multipartRequest.getHttpEntity(), headers );
		} else {
			Map<String,Object> parameters = null;
			if( request != null ) {
				parameters = new ObjectMapper().convertValue( request, Map.class );
			}
			response = restClient.executeRestCall( baseUrl + api[ 0 ], parameters, headers, HttpMethod.valueOf( api[ 1 ] ) );
		}
		String json = new Gson().toJson( response, new TypeToken<Map<String,Object>>(){}.getType() );
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(json, responseType );
		} catch( Exception e ) {
			log.info( "Exception while converting response: " + ExceptionUtils.getFullStackTrace( e ) );
			throw new OpsPanelException( "RC-0006", e.getMessage() );
		}
	}
}
