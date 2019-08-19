package com.snapdeal.payments.view.utils;

import java.util.HashMap;
import java.util.Map;

import com.snapdeal.payments.view.commons.request.AbstractRequest;

public class RequestMapCreator {

	@SuppressWarnings("unchecked")
	public static Map getMap(AbstractRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
		map = mapper.convertValue(request, map.getClass());
		Map<String, Object> mapNew = new HashMap<String, Object>();
		for (String key : map.keySet()) {
			if (map.get(key) != null)
				mapNew.put(key, map.get(key));
		}
		return mapNew;
	}
}
