package com.snapdeal.payments.view.commons.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewDefaultExceptionCodes;

public class JsonUtils {

	static ObjectMapper mapper = new ObjectMapper();

	public static String serialize(Object obj)
			throws PaymentsViewServiceException {
		String str = null;
		if (null == obj) {
			return str;
		}
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		try {
			str = mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new PaymentsViewServiceException(e.getMessage(),
					PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		return str;

	}

	public static <T> T deSerialize(String src, Class<T> clazz)
			throws PaymentsViewServiceException {
		T obj =null;
		try {
				obj = (T) mapper.readValue(src, clazz);
		} catch (Exception e) {
			throw new PaymentsViewServiceException(
					PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode(),
					e.getMessage());
		}
		return obj;

	}

	public static HashMap<String, Object> getObjectToMap(Object request)
			throws PaymentsViewServiceException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String json;
		try {
			json = mapper.writeValueAsString(request);
			// convert JSON string to Map
			map = mapper.readValue(json,
					new TypeReference<Map<String, Object>>() {
					});
		} catch (JsonProcessingException e) {
			throw new PaymentsViewServiceException(e.getMessage(),
					PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		} catch (IOException e) {
			throw new PaymentsViewServiceException(e.getMessage(),
					PaymentsViewDefaultExceptionCodes.INTERNAL_CLIENT.errCode());
		}
		return map;

	}

}
