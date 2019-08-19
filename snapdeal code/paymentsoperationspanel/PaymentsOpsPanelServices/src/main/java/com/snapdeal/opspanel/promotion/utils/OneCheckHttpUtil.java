package com.snapdeal.opspanel.promotion.utils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.snapdeal.opspanel.promotion.Response.GenericOneCheckResponse;
import com.snapdeal.opspanel.promotion.Response.ReverseLoadMoneyResponse;
import com.snapdeal.opspanel.promotion.exception.OneCheckServiceException;
import com.snapdeal.opspanel.promotion.request.ReverseLoadMoneyRequest;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.utils.CheckSumCalculator;

@Component
@Slf4j
public class OneCheckHttpUtil {

	private RestTemplate restTemplate = new RestTemplate();
	
	@Value("${checksum.clientid}")
	String ocClientId;

	@Value("${checksum.clientkey}")
	String ocClientKey;
	
	@Value("${oneCheck.url}")
	String ocUrl;


	public <T, R> ResponseEntity<T> processPostRequest(String requestUri, R request, Class<T> response,
			Map<String, String> headrs) throws IOException {
		HttpHeaders headers = getProcessedHeaders(headrs);
		headers.setContentType(MediaType.APPLICATION_JSON);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Inclusion.NON_NULL);
		String requestJson = mapper.writeValueAsString(request);
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		return restTemplate.exchange(requestUri, HttpMethod.POST, entity, response);
	}

	public <T> ResponseEntity<T> processGetRequest(String requestUri, Class<T> response, 
			Map<String, String> headrs)
			throws JsonProcessingException {
		HttpHeaders headers = getProcessedHeaders(headrs);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return restTemplate.exchange(requestUri, HttpMethod.GET, null, response);
	}

	public String serializeParams(Map<String, String> parameters) throws UnsupportedEncodingException {
		StringBuilder bufferUrl = new StringBuilder();
		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			bufferUrl.append(entry.getKey());
			bufferUrl.append("=");
			bufferUrl.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			bufferUrl.append("&");
		}
		return bufferUrl.toString();
	}

	private HttpHeaders getProcessedHeaders(Map<String, String> headrs) {
		HttpHeaders headers = new HttpHeaders();
		for (Map.Entry<String, String> entry : headrs.entrySet()) {
			if (null != entry)
				headers.set(entry.getKey(), entry.getValue());
		}
		return headers;
	}
	
	public ReverseLoadMoneyResponse httpCallForReverseLoadMoneyOneCheck(
			ReverseLoadMoneyRequest request) throws OneCheckServiceException {

		String requestUri=ocUrl+ "/account/v1/payment/reversetxn";
		Map<String,String> headersMap=new HashMap<String,String>();
		headersMap.put("content-type", "application/json");
		headersMap.put("accept", "application/json");
		headersMap.put("AppRequestId", UUID.randomUUID().toString());
		headersMap.put("clientid", ocClientId);
		ObjectMapper mapper = new ObjectMapper();
		try {
			try {
				mapper.setSerializationInclusion(Inclusion.NON_NULL);
				headersMap.put("checksum",CheckSumCalculator.
						generateSHA256CheckSum(
								ocClientId + ocClientKey + mapper.writeValueAsString(request)));
			} catch (IOException e) {
				log.info("json serialize Exception: " + e);
			}
		} catch (InfoPanelException e1) {
			log.info("checksum calc Exception: " + e1);
		}
		ResponseEntity<GenericOneCheckResponse> response=null;
		try{
			response=processPostRequest(requestUri,request,GenericOneCheckResponse.class,headersMap);
		}catch(JsonProcessingException jpe){
			log.info("parsing Exception while communicating with one check core " + jpe);
			throw new OneCheckServiceException("OC-0001", jpe.getMessage());
		}catch (IOException e) {
			log.info("IO Exception while communicating with one check core " + e);
			throw new OneCheckServiceException("OC-0002", e.getMessage());
		}
		if(response.getBody().getError()!=null){
			throw new OneCheckServiceException(response.getBody().getError().getErrorCode(),
					response.getBody().getError().getErrorMessage());
		}
		return convertMaptoResponse(response.getBody().getData(),ReverseLoadMoneyResponse.class);
	}

	private <T> T convertMaptoResponse(Object data,
			Class<T> ocResponse) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(data, ocResponse);
	}


}
