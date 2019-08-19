package com.snapdeal.admin.response;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class updateIMSApiResponse {

   @JsonProperty("Result")
   private String result;

}
