package com.snapdeal.merchant.client.util;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.snapdeal.merchant.request.AbstractRequest;

public class RequestMapCreator {

	@SuppressWarnings("unchecked")
	public static Map<String, String> getMap(AbstractRequest request) {

		Map<String, String> map = new HashMap<String, String>();
		ObjectMapper mapper = new ObjectMapper();
		map = mapper.convertValue(request, map.getClass());
		return map;
	}
}
