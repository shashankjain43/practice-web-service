package com.snapdeal.admin.request;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class CreateIMSApiRequest {
   
   @NotEmpty
   private String apiMethod;
   @NotEmpty
   private String apiURI;

   private String alias;
   
}
