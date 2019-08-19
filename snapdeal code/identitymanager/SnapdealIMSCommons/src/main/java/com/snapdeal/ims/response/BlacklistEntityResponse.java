package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class BlacklistEntityResponse extends AbstractResponse {

   private static final long serialVersionUID = -8085264741151795462L;
   
   private boolean success=false;

}
