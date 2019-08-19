package com.snapdeal.payments.view.sqs;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.snapdeal.payments.tsm.enums.PartyType;

@Component
public class NotificationReaderSerializationFactory<T> {
   private final static ObjectMapper mapper = new ObjectMapper();
   

   private final static SimpleModule testModule ;
   static {
	   testModule = new SimpleModule("MyEnumDeserialzer", new Version(1, 0, 0, null))
               .addDeserializer(PartyType.class,new PartyTypeDeserializer());
   }

   public T parseJsonToEntity(String jsonContext, Class<T> clazz) throws NotificationReaderException {
      T contextEntity = null;
      if (jsonContext == null)
         return contextEntity;

      try {
    //  mapper.configure(DeserializationFeature.F, state)
			mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,true);
			mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
			mapper.registerModule(testModule);
    	//	mapper.setSerializationInclusion(Include.NON_EMPTY);
    		//mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
    		//mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
    		//mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    	  contextEntity = mapper.readValue(jsonContext, clazz);
      } catch (Exception e) {
         throw new NotificationReaderException(e);
      }
      return contextEntity;
   }

   public String parseEntityToJson(T obj) throws NotificationReaderException {
      String json = null;

      try {
         json = mapper.writeValueAsString(obj);
      } catch (JsonProcessingException e) {
         throw new NotificationReaderException(e);
      }
      return json;
   }
}
