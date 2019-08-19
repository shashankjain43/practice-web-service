package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snapdeal.admin.dao.entity.IMSAPIDetails;

@Getter
@Setter
public class createIMSApiResponse {

   @JsonProperty("Record")
   private IMSAPIDetails imsApiDetails;

   @JsonProperty("Result")
   private String result;

}
