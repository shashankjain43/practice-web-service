package com.snapdeal.payments.view.sqs;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.snapdeal.payments.tsm.enums.PartyType;

public class PartyTypeDeserializer extends JsonDeserializer<PartyType>{

	@Override
	public PartyType deserialize(JsonParser arg0, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		return null;
	}



	
}
