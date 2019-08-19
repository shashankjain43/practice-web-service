package com.snapdeal.merchant.request;

import java.io.IOException;
import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.snapdeal.merchant.dto.ClientConfig;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonPropertyOrder(alphabetic = true)
public abstract class AbstractRequest implements Serializable {

   private static final long serialVersionUID = 1L;

   @JsonIgnore
   protected ClientConfig clientConfig;

   @JsonIgnore
   public String getHashGenerationString() {
      return getHashGenerationString(this);
   }

   public String getHashGenerationString(Object request) {
      ObjectMapper mapper = new ObjectMapper();
      String jsonEquivalentStr;
      try {
         mapper.setSerializationInclusion(Inclusion.NON_NULL);
         jsonEquivalentStr = mapper.writeValueAsString(request);
      } catch (IOException e) {
         throw new RuntimeException(e.getMessage());
      }
      return jsonEquivalentStr;
   }
}