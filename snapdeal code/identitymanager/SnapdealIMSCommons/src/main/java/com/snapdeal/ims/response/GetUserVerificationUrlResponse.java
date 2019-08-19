package com.snapdeal.ims.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetUserVerificationUrlResponse extends AbstractResponse {

   private static final long serialVersionUID = -3847727818459408472L;
   private String url;

}
