package com.snapdeal.admin.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snapdeal.admin.dao.entity.IMSAPIDetails;

@Getter
@Setter
public class GetAllIMSApisResponse {
   @JsonProperty("Records")
   private List<IMSAPIDetails> apisList;
   @JsonProperty("Result")
   private String result;
   @JsonProperty("TotalRecordCount")
   private Integer totalResult;
}
