package com.snapdeal.admin.request;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.validator.constraints.NotEmpty;

@Getter
@Setter
public class GetWhiteListAPIsStatusRequest {

   @NotEmpty
   private String clientId;
}
