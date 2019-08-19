package com.snapdeal.merchant.enums;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class RefundStatusSerializer extends JsonSerializer<RefundStatus> {

	@Override
	public void serialize(RefundStatus status, JsonGenerator generator, SerializerProvider provider)
			throws IOException, JsonProcessingException {
			generator.writeString(status.toString());
	}

}
