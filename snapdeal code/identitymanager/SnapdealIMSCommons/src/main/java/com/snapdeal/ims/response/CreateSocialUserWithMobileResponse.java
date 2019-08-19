package com.snapdeal.ims.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class CreateSocialUserWithMobileResponse extends AbstractResponse{
   
   private static final long serialVersionUID = -4846533483888775941L;

   private String otpId;
   
   private String accountStatus;
}
